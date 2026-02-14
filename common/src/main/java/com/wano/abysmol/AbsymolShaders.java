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
    private static RenderPipeline.Snippet PROPER_GUI_TEXTURED_SNIPPET;
    public static RenderPipeline PROPER_VIGNETTE;
    public static RenderPipeline PROPER_TEXT;
    public static RenderPipeline PROPER_GUI_TEXT;
    public static RenderPipeline PROPER_TEXT_BACKGROUND;
    public static RenderPipeline PROPER_TEXT_INTENSITY;
    public static RenderPipeline PROPER_GUI_TEXT_INTENSITY;
    public static RenderPipeline PROPER_TEXT_POLYGON_OFFSET;
    public static RenderPipeline PROPER_TEXT_SEE_THROUGH;
    public static RenderPipeline PROPER_TEXT_BACKGROUND_SEE_THROUGH;
    public static RenderPipeline PROPER_TEXT_INTENSITY_SEE_THROUGH;

    public static void init() {

        PROPER_GUI_TEXTURED_SNIPPET = RenderPipeline.builder(
                        new RenderPipeline.Snippet[]{(RenderPipelinesAccessor.getMatricesProjectionSnippet())}
                )
                .withVertexShader("core/position_tex_color")
                .withFragmentShader(Identifier.fromNamespaceAndPath("absymol","core/inverted_position_tex_color"))
                .withSampler("Sampler0")
                .withBlend(BlendFunction.TRANSLUCENT)
                .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
                .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                .buildSnippet();

                PROPER_VIGNETTE = RenderPipelinesAccessor.invokeRegister(
                RenderPipeline.builder(
                                new RenderPipeline.Snippet[]{PROPER_GUI_TEXTURED_SNIPPET}
                        )
                        .withLocation("pipeline/vignette")
                        .withBlend(new BlendFunction(SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_COLOR))
                        .build()
        );

        PROPER_TEXT = RenderPipelinesAccessor.invokeRegister(
                RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelinesAccessor.getTextSnippet(), RenderPipelinesAccessor.getFogSnippet()})
                        .withLocation("pipeline/text")
                        .withVertexShader("core/rendertype_text")
                        .withFragmentShader(Identifier.fromNamespaceAndPath("absymol","core/inverted_rendertype_text")).withSampler("Sampler0").withSampler("Sampler2").build());

        PROPER_GUI_TEXT = RenderPipelinesAccessor.invokeRegister(
                RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelinesAccessor.getGuiTextSnippet(), RenderPipelinesAccessor.getFogSnippet()}).withLocation("pipeline/gui_text")
                        .withVertexShader("core/rendertype_text")
                        .withFragmentShader(Identifier.fromNamespaceAndPath("absymol","core/inverted_rendertype_text")).withSampler("Sampler0").withSampler("Sampler2").withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build());
    }
}