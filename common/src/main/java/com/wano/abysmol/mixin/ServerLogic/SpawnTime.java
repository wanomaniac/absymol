package com.wano.abysmol.mixin.ServerLogic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class SpawnTime {
    @Inject(method = "createLevels", at = @At("TAIL"))
    private void chaos$setDefaultMidnight(CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;

        for (ServerLevel level : server.getAllLevels()) {
            // Set day time to midnight
            level.setDayTime(18000L);
        }
    }
}
