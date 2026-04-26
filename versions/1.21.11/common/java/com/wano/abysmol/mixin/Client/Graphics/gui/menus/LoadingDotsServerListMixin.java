package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.components.LoadingDotsWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - Multiplayer screen scanning LAN text is unreadable with white, changed to black.
@Mixin(LoadingDotsWidget.class)
public class LoadingDotsServerListMixin {
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 4)
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }
}

