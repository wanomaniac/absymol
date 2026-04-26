package com.wano.abysmol.mixin.Client.Graphics;

import com.wano.abysmol.AbnormalUtils;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ARGB.class)
public class ARGBMixin {
    @Inject(
            method = "color(IIII)I",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void chaos$injectColor(
            final int alpha, final int red, final int green, final int blue,
            CallbackInfoReturnable<Integer> cir
    ) {
        cir.cancel();
        cir.setReturnValue(AbnormalUtils.invertColor(alpha, red, green, blue));
    }

    @Inject(
            method = "alpha(I)I",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void alpha(final int color, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        cir.setReturnValue((color >>> 24));
    }

    @Inject(method = "red(I)I", at = @At("HEAD"), cancellable = true)
    private static void red(final int color, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        int r = (color >>> 16) & 0xFF;
        cir.setReturnValue(0xFF - r);
    }

    @Inject(method = "green(I)I", at = @At("HEAD"), cancellable = true)
    private static void green(final int color, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        int g = (color >>> 8) & 0xFF;
        cir.setReturnValue(0xFF - g);
    }

    @Inject(method = "blue(I)I", at = @At("HEAD"), cancellable = true)
    private static void blue(final int color, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        int b = color & 0xFF;
        cir.setReturnValue(0xFF - b);
    }

    @Inject(
            method = "color(II)I",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void invertColor(
            int alpha, int color,
            CallbackInfoReturnable<Integer> cir
    ) {
        cir.cancel();
        int r = (color >>> 16) & 0xFF;
        int g = (color >>> 8) & 0xFF;
        int b = color & 0xFF;

        int inverted =
                ((255 - r) << 16)
                        | ((255 - g) << 8)
                        | (255 - b);

        cir.setReturnValue((alpha & 0xFF) << 24 | inverted);
    }


}
