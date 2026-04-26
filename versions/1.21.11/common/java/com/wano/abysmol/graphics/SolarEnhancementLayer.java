package com.wano.abysmol.graphics;

import com.mojang.blaze3d.vertex.PoseStack;
//import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import com.wano.abysmol.Constants;
import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SolarEnhancementLayer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends EnergySwirlLayer<S, M> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"textures/layers/photosynthesis_layer.png");
    private final M model;
    public static float speed = 1;
    public SolarEnhancementLayer(RenderLayerParent<S, M> parent, M model) {
        super(parent);
        this.model = model;
    }

    @Override
    protected boolean isPowered(S state) {
        // Cast to your accessor to check the actual "Charged" logic
//        int solarAmplifier = ((SolarEnhancementsRenderStateAccessor) state).abysmol$solarEnhancementsLevel();
//        speed = solarAmplifier > 0 ? Math.max(1, solarAmplifier - 1) : 0;
        return ((SolarEnhancementsRenderStateAccessor) state).abysmol$solarEnhancements();
    }

    @Override
    protected float xOffset(float ageInTicks) {
        // Minecraft's default charged creeper speed is ageInTicks * 0.01F
        return 0;
    }


    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, S state, float partialTick, float ageInTicks) {
        if (this.isPowered(state)) {
            // 1. Fetch the amplifier/level directly from the state
            int solarAmplifier = ((SolarEnhancementsRenderStateAccessor) state).abysmol$solarEnhancementsLevel();

            // 2. Calculate local speed (avoiding static variables)
            float localSpeed = solarAmplifier > 0 ? Math.max(1, solarAmplifier - 1) : 0;

            // 3. Calculate the UV offsets manually
            float uOffset = (state.ageInTicks * 0.01F * localSpeed) % 1.0F;
            float vOffset = (state.ageInTicks * 0.01F) % 1.0F;

            M model = this.model();
            poseStack.pushPose();

            // Handle Baby scaling if necessary
            if (state.isBaby) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0.0, 1.5, 0.0); // Adjust based on specific entity needs
            }

            collector.order(1).submitModel(
                    model,
                    state,
                    poseStack,
                    RenderTypes.energySwirl(this.getTextureLocation(), uOffset, vOffset),
                    light,
                    OverlayTexture.NO_OVERLAY,
                    -8355712,
                    null,
                    state.outlineColor,
                    null
            );

            poseStack.popPose();
        }
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
