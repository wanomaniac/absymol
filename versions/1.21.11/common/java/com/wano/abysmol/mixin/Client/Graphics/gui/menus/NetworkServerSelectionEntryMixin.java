package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - LAN server selection title is unreadable with white, changed to black.
@Mixin(ServerSelectionList.NetworkServerEntry.class)
public class NetworkServerSelectionEntryMixin {
    @ModifyArg(method = "renderContent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 4)
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }
}
