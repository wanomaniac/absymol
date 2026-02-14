package com.wano.abysmol.mixin.Graphics;

import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Recipes are straight up unreadable in inverted coloring so i had to improvise on using
// the advancement background.
@Mixin(RecipeToast.class)
public class RecipeToastMixin {
    @Unique
    private static final Identifier ADVANCEMENT_BACKGROUND = Identifier.withDefaultNamespace("toast/advancement");

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/toasts/RecipeToast;BACKGROUND_SPRITE:Lnet/minecraft/resources/Identifier;"
            )
    )
    private Identifier redirectBackgroundSprite() {
        return ADVANCEMENT_BACKGROUND;
    }
}
