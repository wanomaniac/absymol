package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - to fix unreadable container titles like inventory & <title of specific container>
@Mixin(AbstractContainerScreen.class)
public class AbstractContainerMixin {
    @ModifyArg(
            method = "renderLabels",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"),
            index = 4
    )
    private static int replaceUnreadableColors(int originalColor){
        return AbysmolARGB.white(255);
    }
}
