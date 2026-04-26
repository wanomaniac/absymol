package com.wano.abysmol.mixin.Resources;

import com.wano.abysmol.Constants;
import com.wano.abysmol.resources.AbysmolDataReloader;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ReloadableServerResources.class)
public class DeveloperReloadMixin {
    @Inject(method = "listeners", at = @At("RETURN"), cancellable = true)
    private void abysmol$addCustomListeners(CallbackInfoReturnable<List<PreparableReloadListener>> cir) {
        List<PreparableReloadListener> listeners = new ArrayList<>(cir.getReturnValue());
        if(Constants.PLATFORM.isDevelopmentEnvironment()) listeners.add(new AbysmolDataReloader()); //
        cir.setReturnValue(List.copyOf(listeners));
    }
}