package com.wano.abysmol.mixin.Graphics;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wano.abysmol.AbysmolPipelineOverrider;
import com.wano.abysmol.Constants;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Mixin(RenderPipelines.class)
public class RenderPipelinesInjector {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;withVertexShader(Ljava/lang/String;)Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;"))
    private static String replaceVertexShader(String original) {
        String replacement = AbysmolPipelineOverrider.VERTEX_SHADER_OVERRIDE.get(original);
        return replacement != null ? Identifier.fromNamespaceAndPath("absymol", replacement).toString() : original;
    }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;withFragmentShader(Ljava/lang/String;)Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;"))
    private static String replaceFragmentShader(String original) {
        String replacement = AbysmolPipelineOverrider.FRAGMENT_SHADER_OVERRIDE.get(original);
        return replacement != null ? Identifier.fromNamespaceAndPath("absymol", replacement).toString() : original;
    }
}
