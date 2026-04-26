package com.wano.abysmol.mixin.Client.Graphics.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.wano.abysmol.AbnormalUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.TextureSetup;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Shadow
    abstract void submitColoredRectangle(final RenderPipeline renderPipeline, final TextureSetup textureSetup, final int x0, final int y0, final int x1, final int y1, final int color1, final @Nullable Integer color2);

    @Inject(
            method = "fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V",
            at = @At("HEAD"),
            cancellable = true)
    public void fill(final RenderPipeline pipeline, int x0, int y0, int x1, int y1, final int col, CallbackInfo ci) {
        ci.cancel();
        if (x0 < x1) {
            int tmp = x0;
            x0 = x1;
            x1 = tmp;
        }

        if (y0 < y1) {
            int tmp = y0;
            y0 = y1;
            y1 = tmp;
        }

        this.submitColoredRectangle(pipeline, TextureSetup.noTexture(), x0, y0, x1, y1, AbnormalUtils.invertColor(col), (Integer)null);
    }


}
