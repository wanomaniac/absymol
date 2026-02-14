package com.wano.abysmol.graphics;

import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SolarEnhancementLayer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends EnergySwirlLayer<S, M> {

    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
    private final M model;

    public SolarEnhancementLayer(RenderLayerParent<S, M> parent, M model) {
        super(parent);

        this.model = model;
    }

    @Override
    protected boolean isPowered(S state) {
        // Cast to your accessor to check the actual "Charged" logic
        return ((SolarEnhancementsRenderStateAccessor) state).abysmol$solarEnhancements();
    }

    @Override
    protected float xOffset(float ageInTicks) {
        // Minecraft's default charged creeper speed is ageInTicks * 0.01F
        return ageInTicks * 0.01F;
    }

    @Override
    protected @NotNull Identifier getTextureLocation() {
        return TEXTURE;
    }

    @Override
    protected M model() {
        return this.model;
    }
}
