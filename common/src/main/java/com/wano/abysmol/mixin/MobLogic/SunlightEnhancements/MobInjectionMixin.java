package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements;

import com.wano.abysmol.mixinAccessors.SolarEnhancementsAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// This class's purpose is override the logic behind how mob's are affected by sunlight burning.
// Instead of sunlight directly damaging the mob, it will increase it's own power, with it's own guided assistance with healing, damage etc.
// After all, night is safe
@Mixin(Mob.class)
public class MobInjectionMixin {
    private boolean directIsSunBurningMe() {
            Mob self = (Mob)(Object)this;
            if (!self.level().isClientSide() && (Boolean)self.level().environmentAttributes().getValue(EnvironmentAttributes.MONSTERS_BURN, self.position())) {
                float f = self.getLightLevelDependentMagicValue();

                // Eye-level block position check
                BlockPos blockpos = BlockPos.containing(self.getX(), self.getEyeY(), self.getZ());

                // Rain/Snow check
                boolean flag = self.isInWaterOrRain() || self.isInPowderSnow || self.wasInPowderSnow;

                // REMOVED: the random.nextFloat() * 30.0F check
                // We only check if light is high enough and they can see the sky
                if (f > 0.5F && !flag && self.level().canSeeSky(blockpos)) {
                    return true;
                }
            }
            return false;
    }

    @Inject(method = "burnUndead", at = @At("HEAD"), cancellable = true)
    private void chaos$updateSolar(CallbackInfo ci) {
        Mob self = (Mob)(Object)this;
        if(self.level().isClientSide()) return;

        ((SolarEnhancementsAccessor)self)
                .abysmol$setSolarEnhancements(directIsSunBurningMe());
        ci.cancel();
    }

}
