package com.wano.abysmol.mixin.Client.Graphics.renderers.mobs.photosynthesis;

import com.wano.abysmol.graphics.SolarEnhancementLayer;
import com.wano.abysmol.logic.PhotosynthesisTools;
import com.wano.abysmol.mixinAccessors.SolarEnhancementsRenderStateAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class SolarEnhancementInitalizer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<S>> {
    @Shadow
    protected abstract boolean addLayer(RenderLayer<S, M> layer);

    @Inject(
            method = "extractRenderState*",
            at = @At("TAIL")
    )
    private void chaos$copySolarBurn(
            T entity,
            S renderState,
            float partialTick,
            CallbackInfo ci
    ) {
        if (entity instanceof LivingEntity self) {
            ((SolarEnhancementsRenderStateAccessor)renderState)
                    .abysmol$setSolarEnhancements(PhotosynthesisTools.isEntityPhotosynthesized4Client(self) && PhotosynthesisTools.getPhotosynthesisLevel4Client(self) > 0);
            ((SolarEnhancementsRenderStateAccessor)renderState)
                    .abysmol$setSolarEnhancementsLevel(PhotosynthesisTools.getPhotosynthesisLevel4Client(self));
        }
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void initalize(EntityRendererProvider.Context context, M model, float shadowRadius, CallbackInfo ci){
        RenderLayerParent<S, M> parent = (RenderLayerParent<S, M>) (Object) this;
        this.addLayer(new SolarEnhancementLayer<T, S, M>(parent, model));
    }

}
