package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.inventory.BookSignScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - to fix another book unreading issue, titles unreadable cuz bg is black
@Mixin(BookSignScreen.class)
public class BookSignScreenMixin {
    @ModifyArg(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setTextColor(I)V")
    )
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.white(255);
    }

    @ModifyArg(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"),
            index = 4
    )
    private static int replaceUnreadableColorRender(int originalColor){
        return AbysmolARGB.white(255);
    }

    @ModifyArg(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawWordWrap(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/FormattedText;IIIIZ)V"),
            index = 5
    )
    private static int replaceUnreadableColorRenderWordWrap(int originalColor){
        return AbysmolARGB.white(255);
    }
}
