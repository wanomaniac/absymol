    package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements;

    import com.wano.abysmol.mixinAccessors.SolarEnhancementsAccessor;
    import net.minecraft.core.BlockPos;
    import net.minecraft.world.attribute.EnvironmentAttributes;
    import net.minecraft.world.entity.LivingEntity;
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
        @Inject(method = "burnUndead", at = @At("HEAD"), cancellable = true)
        private void chaos$updateSolar(CallbackInfo ci) {
            ci.cancel();
        }

    }
