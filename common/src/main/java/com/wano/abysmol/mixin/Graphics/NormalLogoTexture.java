package com.wano.abysmol.mixin.Graphics;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.jtracy.MemoryPool;
import com.mojang.jtracy.TracyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.PngInfo;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(targets = "net/minecraft/client/gui/screens/LoadingOverlay$LogoTexture")
public class NormalLogoTexture {
    private static final MemoryPool MEMORY_POOL = TracyClient.createMemoryPool("NativeImage");

    @Inject(
            method = "loadContents(Lnet/minecraft/server/packs/resources/ResourceManager;)Lnet/minecraft/client/renderer/texture/TextureContents;",
        at = @At("HEAD"),
        cancellable = true
    )
    public void loadContents(final ResourceManager resourceManager, CallbackInfoReturnable<TextureContents> cir) throws IOException {
        ResourceProvider vanillaProvider = Minecraft.getInstance().getVanillaPackResources().asProvider();

        try (InputStream resource = vanillaProvider.open(LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION)) {
            ByteBuffer file = null;
            NativeImage image;
            NativeImage.Format format = NativeImage.Format.RGBA;
            try {
                file = TextureUtil.readResource(resource);
                if (!format.supportedByStb()) {
                    throw new UnsupportedOperationException("Don't know how to read format " + String.valueOf(format));
                } else if (MemoryUtil.memAddress(file) == 0L) {
                    throw new IllegalArgumentException("Invalid buffer");
                } else {
                    PngInfo.validateHeader(file);

                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        IntBuffer w = stack.mallocInt(1);
                        IntBuffer h = stack.mallocInt(1);
                        IntBuffer comp = stack.mallocInt(1);
                        ByteBuffer pixels = STBImage.stbi_load_from_memory(file, w, h, comp, format.components());
                        if (pixels == null) {
                            throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
                        } else {
                            long address = MemoryUtil.memAddress(pixels);
                            MEMORY_POOL.malloc(address, pixels.limit());
                            image = new NativeImage(format, w.get(0), h.get(0), true, address);
                        }
                    }
                }
            } finally {
                MemoryUtil.memFree(file);
                IOUtils.closeQuietly(resource);
            }

            cir.setReturnValue(new TextureContents(image, new TextureMetadataSection(true, true, MipmapStrategy.MEAN, 0.0F)));
        }
    }
}
