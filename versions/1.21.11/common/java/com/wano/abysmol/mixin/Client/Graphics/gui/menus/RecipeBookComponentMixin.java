package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// PATCH - Recipe book search box is completely unreadable with white since bg is also white, changed to black.
@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {
    @ModifyArg(
            method = "initVisuals",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setTextColor(I)V")
    )
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }
}
