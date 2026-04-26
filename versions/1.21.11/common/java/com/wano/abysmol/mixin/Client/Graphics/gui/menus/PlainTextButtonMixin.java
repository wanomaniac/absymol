package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.components.PlainTextButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - buttons are unreadable with white, changed to black.
@Mixin(PlainTextButton.class)
public class PlainTextButtonMixin {
    @ModifyArg(
            method = "renderContents",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"),
            index = 4
    )
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }
}
