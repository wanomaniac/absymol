package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Patch - To make all tool tips black by default instead of unreadable white.
@Mixin(ClientTextTooltip.class)
public class ClientTextTooltipMixin {
    @ModifyArg(
            method = "renderText",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V"
            ),
            index = 4
    )
    private static int replaceUnreadableColor(int x){
        return AbysmolARGB.black(255);
    }
}
