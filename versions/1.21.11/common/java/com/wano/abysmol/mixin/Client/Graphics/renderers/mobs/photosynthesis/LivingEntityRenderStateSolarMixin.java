package com.wano.abysmol.mixin.Client.Graphics.renderers.mobs.photosynthesis;

import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateSolarMixin implements SolarEnhancementsRenderStateAccessor {
    @Unique
    public boolean abysmol$solarEnhancements = false;

    @Unique
    public int abysmol$solarEnhancements$level = 0;

    @Override
    public boolean abysmol$solarEnhancements() {
        return abysmol$solarEnhancements;
    }

    @Override
    public void abysmol$setSolarEnhancements(boolean se){
        abysmol$solarEnhancements = se;
    }

    @Override
    public int abysmol$solarEnhancementsLevel() {
        return abysmol$solarEnhancements$level;
    }

    @Override
    public void abysmol$setSolarEnhancementsLevel(int level) {
        abysmol$solarEnhancements$level = level;
    }


}
