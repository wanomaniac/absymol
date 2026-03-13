package com.wano.abysmol.mixin.MobLogic.SunlightEnhancements.Renderer;

import com.wano.abysmol.mixinAccessors.SolarEnhancementsAccessor;
import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateSolarMixin implements SolarEnhancementsRenderStateAccessor {
    @Unique
    public boolean absymol$solarEnhancements = false;

    @Unique
    public int absymol$solarEnhancements$level = 0;

    @Override
    public boolean abysmol$solarEnhancements() {
        return absymol$solarEnhancements;
    }

    @Override
    public void abysmol$setSolarEnhancements(boolean se){
        absymol$solarEnhancements = se;
    }

    @Override
    public int abysmol$solarEnhancementsLevel() {
        return absymol$solarEnhancements$level;
    }

    @Override
    public void abysmol$setSolarEnhancementsLevel(int level) {
        absymol$solarEnhancements$level = level;
    }


}
