package com.wano.abysmol.mixin.Client.Graphics;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderPipelines.class)
public interface RenderPipelinesAccessor {
    @Accessor("MATRICES_PROJECTION_SNIPPET")
    static RenderPipeline.Snippet getMatricesProjectionSnippet() {
        throw new UnsupportedOperationException();
    }

    @Accessor("TEXT_SNIPPET")
    static RenderPipeline.Snippet getTextSnippet() {
        throw new UnsupportedOperationException();
    }

    @Accessor("FOG_SNIPPET")
    static RenderPipeline.Snippet getFogSnippet() {
        throw new UnsupportedOperationException();
    }

    @Accessor("GUI_TEXT_SNIPPET")
    static RenderPipeline.Snippet getGuiTextSnippet() {
        throw new UnsupportedOperationException();
    }


    @Accessor("PIPELINES_BY_LOCATION")
    static Map<Identifier, RenderPipeline> getPipelinesByLocation() {
        throw new UnsupportedOperationException();
    }

    @Invoker("register")
    static RenderPipeline invokeRegister(RenderPipeline pipeline) {
        throw new UnsupportedOperationException();
    }
}
