package com.wano.abysmol.mixin;

import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Identifier.class)
public class IdentifierMixin {
    @Inject(
            method = "withDefaultNamespace",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void allowForCustomNamespaces(String location, CallbackInfoReturnable<Identifier> cir){
        if(location.contains(":")){
            cir.setReturnValue(Identifier.parse(location));
            cir.cancel();
        }
    }
}
