package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - Changed default rendering text color to black instead of white.
@Mixin(targets = "net.minecraft.client.gui.GuiGraphics$RenderingTextCollector")
public class RenderingTextMixin {
    @ModifyArg(method = "accept(Lnet/minecraft/client/gui/TextAlignment;IILnet/minecraft/client/gui/ActiveTextCollector$Parameters;Lnet/minecraft/util/FormattedCharSequence;)V",
               at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiTextRenderState;<init>(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;Lorg/joml/Matrix3x2fc;IIIIZZLnet/minecraft/client/gui/navigation/ScreenRectangle;)V"),
              index = 5)
    private static int ReplaceColor(int og){
        return AbysmolARGB.black(255);
    }
}
