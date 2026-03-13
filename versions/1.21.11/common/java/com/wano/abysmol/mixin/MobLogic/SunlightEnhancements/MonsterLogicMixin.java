package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//
@Mixin(Monster.class)
public class MonsterLogicMixin {
    @Inject(
            method = "isDarkEnoughToSpawn",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        boolean isDaytime = level.getLevel().isBrightOutside();
        boolean canSeeSky = level.canSeeSky(pos);
        // todo: make mobs attracted to light placed on ground (torches, etc)
        if (isDaytime && canSeeSky) {
            cir.setReturnValue(true);
        } else cir.setReturnValue(false);
    }
}

