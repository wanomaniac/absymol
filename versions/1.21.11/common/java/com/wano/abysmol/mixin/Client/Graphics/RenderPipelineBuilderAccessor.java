package com.wano.abysmol.mixin.Client.Graphics;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(RenderPipeline.Builder.class)
public interface RenderPipelineBuilderAccessor {
    @Accessor("location")
    Optional<Identifier> getLocation();

    @Accessor("vertexShader")
    Optional<Identifier> getVertexShader();

    @Accessor("fragmentShader")
    Optional<Identifier> getFragmentShader();
}