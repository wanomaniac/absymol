package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements;
import com.wano.abysmol.AbysmolAttachments;
import com.wano.abysmol.mixinAccessors.SolarEnhancementsAccessor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntitySolarEnhancementsMixin implements SolarEnhancementsAccessor {

    @Override
    public void abysmol$setSolarEnhancements(boolean value) {
        LivingEntity self = (LivingEntity)(Object)this;

        self.setData(AbysmolAttachments.SOLAR, value);
    }

    @Override
    public boolean abysmol$solarEnhancements() {
        LivingEntity self = (LivingEntity)(Object)this;
        return self.getData(AbysmolAttachments.SOLAR);
    }

    @Unique
    boolean abysmol$solarEnhancementsActive = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void chaos$modifySunFireDamage(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (self.level().isClientSide()) return;
        if (!(self instanceof Mob mob)) return;

        SolarEnhancementsAccessor accessor =
                (SolarEnhancementsAccessor) mob;

        boolean solar = accessor.abysmol$solarEnhancements();
        boolean overloaded = mob.isInLava() || mob.isOnFire();

        if (solar && !abysmol$solarEnhancementsActive && !overloaded) {
            mob.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 40, 1, false, false, false));
            mob.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 40, 1, false, false, false));
            abysmol$solarEnhancementsActive = true;
            return;
        }

        if (!solar && abysmol$solarEnhancementsActive) {
            mob.removeEffect(MobEffects.HEALTH_BOOST);
            mob.removeEffect(MobEffects.INSTANT_DAMAGE);
            mob.removeEffect(MobEffects.WEAKNESS);

            abysmol$solarEnhancementsActive = false;
            mob.playSound(SoundEvents.FIRE_EXTINGUISH);
            return;
        }

        // solar entities can NOT handle this much solar energy, so it can weaken them instead
        if (solar && abysmol$solarEnhancementsActive && overloaded) {
            mob.removeEffect(MobEffects.HEALTH_BOOST);
            mob.removeEffect(MobEffects.INSTANT_DAMAGE);

            mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1, false, false, false));
        }
    }
}