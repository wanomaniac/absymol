package com.wano.abysmol.mixin.Graphics;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.wano.abysmol.AbsymolShaders;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Currently responsible for making selected item text actually readable.
@Mixin(Gui.class)
public class GuiMixin {
    @Redirect(
            method = "renderSelectedItemName", // target the method where call exists
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawStringWithBackdrop(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIII)V"
            )
    )
    private void redirectSelectedItemName(
            GuiGraphics guiGraphics,
            Font font,
            Component component,
            int x,
            int y,
            int width,
            int color
    ) {
        guiGraphics.fill(x - 2, y - 2, x + width + 2, y + 9 + 2, 0xFF000000);
        guiGraphics.drawString(font, component, x, y, color, true);
    }

    @Redirect(
            method = "renderVignette",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V")
    )
    private void cancelVignette(GuiGraphics instance, RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        instance.blit(
                AbsymolShaders.PROPER_VIGNETTE,
                atlas,
                x, y,
                u, v,
                width, height,
                textureWidth, textureHeight,
                color
        );
    }
}

