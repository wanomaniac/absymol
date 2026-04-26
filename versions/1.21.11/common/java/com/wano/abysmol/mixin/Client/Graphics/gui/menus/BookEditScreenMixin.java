package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH to make book edits READABLE
@Mixin(BookEditScreen.class)
public class BookEditScreenMixin {
    @ModifyArg(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/MultiLineEditBox$Builder;setTextColor(I)Lnet/minecraft/client/gui/components/MultiLineEditBox$Builder;")
    )
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.white(255);
    }
}
