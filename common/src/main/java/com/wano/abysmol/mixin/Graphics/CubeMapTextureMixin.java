package com.wano.abysmol.mixin.Graphics;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.CubeMapTexture;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.io.IOException;

@Mixin(CubeMapTexture.class)
public class CubeMapTextureMixin {
    @Unique
    private static final String[] SUFFIXES = new String[]{"_1.png", "_3.png", "_5.png", "_4.png", "_0.png", "_2.png"};

    @Unique
    private Identifier resourceId;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(final Identifier id, CallbackInfo info) {
        resourceId = id;
    }

    @Inject(
            method = "loadContents",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onLoadContents(
            ResourceManager resourceManager,
            CallbackInfoReturnable<TextureContents> cir
    ) throws IOException {
        Identifier location = this.resourceId;

        TextureContents var15;
        try (TextureContents first = TextureContents.load(resourceManager, location.withSuffix(SUFFIXES[0]))) {
            int width = first.image().getWidth();
            int height = first.image().getHeight();
            NativeImage stackedImage = new NativeImage(width, height * 6, false);
            first.image().copyRect(stackedImage, 0, 0, 0, 0, width, height, false, true);

            for(int i = 1; i < 6; ++i) {
                try (TextureContents part = TextureContents.load(resourceManager, location.withSuffix(SUFFIXES[i]))) {
                    if (part.image().getWidth() != width || part.image().getHeight() != height) {
                        String var10002 = String.valueOf(location);
                        throw new IOException("Image dimensions of cubemap '" + var10002 + "' sides do not match: part 0 is " + width + "x" + height + ", but part " + i + " is " + part.image().getWidth() + "x" + part.image().getHeight());
                    }

                    part.image().copyRect(stackedImage, 0, 0, 0, i * height, width, height, false, true);
                }
            }

            var15 = new TextureContents(stackedImage, new TextureMetadataSection(true, false, MipmapStrategy.MEAN, 0.0F));
        }

        cir.setReturnValue(var15);
        cir.cancel();
    }

}