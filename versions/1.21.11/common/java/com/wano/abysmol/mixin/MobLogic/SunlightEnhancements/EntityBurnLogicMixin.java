package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements;

import com.wano.abysmol.logic.PhotosynthesisTools;
import com.wano.abysmol.mixinAccessors.SolarEnhancementsAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityBurnLogicMixin {

    @Redirect(
            method = "baseTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z")
    )
    private boolean onFireTickDamage(Entity instance, ServerLevel level, DamageSource source, float amount) {
        return abysmol$fireDamage();
    }

    @Unique
    private boolean abysmol$fireDamage() {
        Entity entity = (Entity) (Object) this;
        if(entity == null) return false;
        Level var3 = entity.level();
        if (var3 instanceof ServerLevel serverlevel && entity instanceof LivingEntity lEntity) {
            return entity.hurtServer(serverlevel, entity.damageSources().onFire(), PhotosynthesisTools.isEntityPhotosynthesized(lEntity) ? 2.0F * ((float) PhotosynthesisTools.getPhotosynthesisLevel4Server(lEntity) / 2) : 1.0F);
        }
        return false;
    }

    @Redirect(
            method = "lavaHurt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z")
    )
    private boolean onLavaDamage(Entity instance, ServerLevel level, DamageSource source, float amount) {
        return abysmol$fireDamage();
    }
}
