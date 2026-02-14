package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements.Renderer;

import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateSolarMixin implements SolarEnhancementsRenderStateAccessor {
    @Unique
    public boolean absymol$solarEnhancements;

    @Override
    public boolean abysmol$solarEnhancements() {
        return absymol$solarEnhancements;
    }

    @Override
    public void abysmol$setSolarEnhancements(boolean se){
        absymol$solarEnhancements = se;
    }
}
