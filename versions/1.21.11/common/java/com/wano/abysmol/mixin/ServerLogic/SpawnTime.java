package com.wano.abysmol.mixin.ServerLogic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class SpawnTime {

    @Inject(method = "setInitialSpawn", at = @At("TAIL"))
    private static void setInitialSpawn(ServerLevel level, ServerLevelData levelData, boolean generateBonusChest, boolean debug, LevelLoadListener listener, CallbackInfo ci) {
        level.setDayTime(18000L);
    }
}