package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.ConnectScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Patch - Background is white, if the text is white, its barely readable, it needs to be changed to black.
@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {
    @ModifyArg(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"),
            index = 4
    )
    private static int replaceUnreadableColors(int originalColor){
        return AbysmolARGB.black(255);
    }
}
