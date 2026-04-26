package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - Texts at bottom like version and credits are unreadable with white, changed to black.
@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @ModifyArg(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"),
            index = 4
    )
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }
}
