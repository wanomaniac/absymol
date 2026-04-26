package com.wano.abysmol.mixin.Client.Graphics.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {
    @Inject(
            method = "render",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;drawProgressBar(Lnet/minecraft/client/gui/GuiGraphics;IIIIF)V"
    )
    )
    private void renderInjection(
            GuiGraphics graphics, int p_282704_, int p_283650_, float p_283394_,
            CallbackInfo cir
    ){
//        if (Minecraft.getInstance().getShaderManager().getShader(Identifier.parse("absymol:core/inverted_rendertype_text") , ShaderType.FRAGMENT) == null) {
//            return;
//        }
//
//        graphics.drawCenteredString(Minecraft.getInstance().font, "You are running "+ Constants.MOD_NAME+" on "+Constants.PLATFORM.getPlatformName(), graphics.guiWidth() / 2, 5, ARGB.color(255, 0, 85));
    }
}
