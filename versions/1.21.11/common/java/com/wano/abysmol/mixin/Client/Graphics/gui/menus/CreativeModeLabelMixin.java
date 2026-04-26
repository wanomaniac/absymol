package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Creative mode titles like "Building Blocks" are unreadable due to the color similarities in bg, changed to white.
@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeLabelMixin {
    @ModifyArg(
            method = "renderLabels",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"
                    ),
            index = 4
    )
    private static int replaceUnreadableColor(int x){ // replace to full white
        return AbysmolARGB.white(255);
    }
}
