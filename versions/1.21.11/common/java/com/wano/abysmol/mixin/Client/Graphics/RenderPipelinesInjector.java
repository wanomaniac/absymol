package com.wano.abysmol.mixin.Client.Graphics;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.wano.abysmol.AbysmolPipelineOverrider;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderPipelines.class)
public class RenderPipelinesInjector {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;build()Lcom/mojang/blaze3d/pipeline/RenderPipeline;"))
    private static RenderPipeline RedirectBuild(RenderPipeline.Builder instance){
        if(AbysmolPipelineOverrider.LOCATION_SHADER_OVERRIDE.isEmpty()){
            AbysmolPipelineOverrider.init();
        }
        RenderPipelineBuilderAccessor accessor = (RenderPipelineBuilderAccessor) (Object) instance;
        if(accessor.getLocation().isPresent()) {
            String location = accessor.getLocation().get().getPath();
            AbysmolPipelineOverrider.PipelineLocationData data = AbysmolPipelineOverrider.LOCATION_SHADER_OVERRIDE.get(location);
            if(data != null){
                if(!data.fragmentShader().isEmpty()) instance.withFragmentShader(Identifier.fromNamespaceAndPath("abysmol", data.fragmentShader()).toString());
                if(!data.vertexShader().isEmpty()) instance.withVertexShader(Identifier.fromNamespaceAndPath("abysmol", data.vertexShader()).toString());
            }
        }

        return instance.build();
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;withVertexShader(Ljava/lang/String;)Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;"))
    private static String replaceVertexShader(String original) {
        if(AbysmolPipelineOverrider.VERTEX_SHADER_OVERRIDE.isEmpty()){
            AbysmolPipelineOverrider.init();
        }
        String replacement = AbysmolPipelineOverrider.VERTEX_SHADER_OVERRIDE.get(original);
        return replacement != null ? Identifier.fromNamespaceAndPath("abysmol", replacement).toString() : original;
    }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;withFragmentShader(Ljava/lang/String;)Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;"))
    private static String replaceFragmentShader(String original) {
        if(AbysmolPipelineOverrider.FRAGMENT_SHADER_OVERRIDE.isEmpty()){
            AbysmolPipelineOverrider.init();
        }
        String replacement = AbysmolPipelineOverrider.FRAGMENT_SHADER_OVERRIDE.get(original);
        return replacement != null ? Identifier.fromNamespaceAndPath("abysmol", replacement).toString() : original;
    }
}
