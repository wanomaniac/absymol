package com.wano.abysmol.mixin.Client.Graphics;

import com.wano.abysmol.AbnormalUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/client/gui/Font$PreparedTextBuilder")
public class PreparedTextBuilderMixin {
    @Final
    @Shadow
    private boolean drawShadow;
//
//    @Inject(
//            method = "getTextColor",
//            at = @At("RETURN"),
//            cancellable = true)
//    private void getTextColor(final CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(AbnormalUtils.invertColor(cir.getReturnValue()));
//    }

    @Inject(
            method = "getShadowColor(Lnet/minecraft/network/chat/Style;I)I",
            at = @At("HEAD"),
            cancellable = true)
    private void getShadowColor(final Style style, final int textColor, final CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        Integer shadow = style.getShadowColor();
        if (shadow != null) {
            shadow = AbnormalUtils.invertColor(shadow);
            float textAlpha = ARGB.alphaFloat(textColor);
            float shadowAlpha = ARGB.alphaFloat(shadow);
            cir.setReturnValue(textAlpha != 1.0F ? ARGB.color(ARGB.as8BitChannel(textAlpha * shadowAlpha), shadow) : shadow);
        } else {
            cir.setReturnValue(drawShadow ? 0x40FFFFFF : 0);
        }
    }
}
