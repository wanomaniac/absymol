package com.wano.abysmol.mixin.Graphics;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.jtracy.MemoryPool;
import net.minecraft.util.PngInfo;
import org.jspecify.annotations.Nullable;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(NativeImage.class)
public class NativeImageMixin {
    @Accessor("MEMORY_POOL")
    static MemoryPool getMemoryPool() {
        throw new AssertionError();
    }

    // this just inverts everything, trippy.
    @Inject(
            method = "read(Lcom/mojang/blaze3d/platform/NativeImage$Format;Ljava/nio/ByteBuffer;)Lcom/mojang/blaze3d/platform/NativeImage;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void onRead(
            NativeImage.@Nullable Format format,
            final ByteBuffer bytes,
            CallbackInfoReturnable<NativeImage> cir
    ) throws IOException {
        cir.cancel();
        if (format != null && !format.supportedByStb()) {
            throw new UnsupportedOperationException("Don't know how to read format " + String.valueOf(format));
        } else if (MemoryUtil.memAddress(bytes) == 0L) {
            throw new IllegalArgumentException("Invalid buffer");
        } else {
            PngInfo.validateHeader(bytes);

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                IntBuffer comp = stack.mallocInt(1);
                ByteBuffer pixels = STBImage.stbi_load_from_memory(bytes, w, h, comp, format == null ? 0 : format.components());
                if (pixels == null) {
                    throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
                } else {
                    int components = format == null ? comp.get(0) : format.components();
                    for (int i = 0; i < pixels.limit(); i += components) {
                            pixels.put(i,     (byte)(255 - (pixels.get(i) & 0xFF)));     // R
                            pixels.put(i + 1, (byte)(255 - (pixels.get(i + 1) & 0xFF))); // G
                            pixels.put(i + 2, (byte)(255 - (pixels.get(i + 2) & 0xFF))); // B
                    }

                    long address = MemoryUtil.memAddress(pixels);
                    getMemoryPool().malloc(address, pixels.limit());
                    cir.setReturnValue(new NativeImage(format == null ? NIFormatAccessor.getStbFormat(comp.get(0)) : format, w.get(0), h.get(0), true, address));
                }
            }
        }
    }
}
