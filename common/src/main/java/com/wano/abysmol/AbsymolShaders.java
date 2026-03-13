package com.wano.abysmol;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.wano.abysmol.mixin.Graphics.RenderPipelinesAccessor;
import net.minecraft.resources.Identifier;

public class AbsymolShaders {
    public static RenderPipeline.Snippet GUI_INVERTED_SNIPPET;
    public static RenderPipeline VIGNETTE;

    public static void init(){
        GUI_INVERTED_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelinesAccessor.getMatricesProjectionSnippet()}).withVertexShader("core/position_tex_color")
                .withFragmentShader(Identifier.fromNamespaceAndPath("absymol", "core/inverted_position_tex_color")).withSampler("Sampler0").withBlend(BlendFunction.TRANSLUCENT).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).buildSnippet();
        VIGNETTE = RenderPipelinesAccessor.invokeRegister((RenderPipeline.builder(new RenderPipeline.Snippet[]{GUI_INVERTED_SNIPPET}).withLocation("pipeline/vignette").withBlend(new BlendFunction(SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_COLOR)).build()));
    }
}
