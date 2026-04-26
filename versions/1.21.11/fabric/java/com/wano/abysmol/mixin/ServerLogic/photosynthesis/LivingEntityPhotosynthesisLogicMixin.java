package com.wano.abysmol.mixin.ServerLogic.photosynthesis;
import com.wano.abysmol.logic.PhotosynthesisTools;
import com.wano.abysmol.mixinAccessors.PhotosynthesisAccessor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityPhotosynthesisLogicMixin implements PhotosynthesisAccessor {
    @Unique
    private static final EntityDataAccessor<@NotNull Boolean> abysmol$SOLAR =
            SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private static final EntityDataAccessor<@NotNull Integer> abysmol$SOLAR$LEVEL =
            SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

    @Override
    public void abysmol$setSolarEnhancements(boolean value) {
        LivingEntity self = (LivingEntity)(Object)this;
        self.getEntityData().set(abysmol$SOLAR, value);
    }

    @Override
    public int abysmol$solarEnhancementsLevel() {
        LivingEntity self = (LivingEntity)(Object)this;
        return self.getEntityData().get(abysmol$SOLAR$LEVEL);
    }

    @Override
    public void abysmol$setSolarEnhancementsLevel(int level) {
        LivingEntity self = (LivingEntity)(Object)this;
        self.getEntityData().set(abysmol$SOLAR$LEVEL, level);
    }

    @Override
    public boolean abysmol$solarEnhancements() {
        LivingEntity self = (LivingEntity)(Object)this;
        return self.getEntityData().get(abysmol$SOLAR);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSolarData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(abysmol$SOLAR, false);
        builder.define(abysmol$SOLAR$LEVEL, 0);
    }

    @Unique
    boolean abysmol$solarEnhancementsActive = false;

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void chaos$modifySunFireDamage(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        if(self.level().isClientSide()) return;
        PhotosynthesisAccessor accessor = (PhotosynthesisAccessor) self;
        int totalSolarLevel = PhotosynthesisTools.getPhotosynthesisLevel4Server(self);
        boolean isUnderSun = PhotosynthesisTools.isEntityPhotosynthesized(self);
        boolean canBeSolar = isUnderSun && totalSolarLevel > 0;
        boolean overloaded = self.isInLava() || self.isOnFire();

        accessor.abysmol$setSolarEnhancements(canBeSolar);
        accessor.abysmol$setSolarEnhancementsLevel(totalSolarLevel);

        if (canBeSolar && !overloaded && !abysmol$solarEnhancementsActive) {
            abysmol$solarEnhancementsActive = true;
            // Level 1-3: Speed 0 (1) | Level 4-8: Speed 1 (2) | Level 9+: Speed 2 (3)
            if(totalSolarLevel <= 2){
                self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 1, false, false, true));
            } else {
                int masterAmp = totalSolarLevel / 2;
                self.addEffect(new MobEffectInstance(MobEffects.SPEED, -1, masterAmp, false, false, true));
                // Regeneration is OP, should not be given at at very low levels.
                if (totalSolarLevel >= 3) {
                    // Logarithmic scaling grows much slower than linear so it's the better choice
                    // Level 3: Amp 0 | Level 7: Amp 1 | Level 15: Amp 2
                    int secondaryAmp = (int) (Math.log(totalSolarLevel) / Math.log(3));
                    self.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, secondaryAmp, false, false, true));
                    if (totalSolarLevel >= 5) {
                        int regenAmp = Math.max(0, secondaryAmp - 1); // Regen is always 1 tier behind Health
                        self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, regenAmp, false, false, true));
                    }
                }
            }
        }
        else if (canBeSolar && overloaded) {
            // Overload state: Swap buffs for debuffs
            if (abysmol$solarEnhancementsActive) {
                self.removeAllEffects();
//                abysmol$solarEnhancementsActive = false;
            }
            self.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, -1, totalSolarLevel * 2, false, false, false));
        }
        else if (!canBeSolar && abysmol$solarEnhancementsActive) {
            // Deactivation state
            self.removeAllEffects();
            abysmol$solarEnhancementsActive = false;
        }
    }
}