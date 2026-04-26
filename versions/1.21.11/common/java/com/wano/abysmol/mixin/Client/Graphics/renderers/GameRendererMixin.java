package com.wano.abysmol.mixin.Client.Graphics.renderers;

import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.jtracy.TracyClient;
import com.wano.abysmol.Constants;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "preloadUiShader", at = @At("HEAD"), cancellable = true)
    public void preloadUiShader(ResourceProvider resourceProvider, CallbackInfo ci) {
        ci.cancel();
        GpuDevice gpudevice = RenderSystem.getDevice();
        ShaderSource abysmolSource = (shaderId, type) -> {
            Identifier identifier = type.idConverter().idToFile(shaderId);
            if(identifier.getNamespace().equals("abysmol")) {
                try (InputStream is = Constants.PLATFORM.loadModResource(identifier.toString())) {
                    if (is == null) throw new IOException("Resource not found: " + identifier.getPath());

                    String shader = IOUtils.toString(is, StandardCharsets.UTF_8);
                    return shader;
                } catch (IOException e) {
                    Constants.LOG.error("Failed to load Abysmol shader: {}", identifier.getPath(), e);
                    return null;
                }
            } else {
                try (Reader reader = resourceProvider.getResourceOrThrow(identifier).openAsReader()) {
                    return IOUtils.toString(reader);
                } catch (IOException ioexception) {
                    Constants.LOG.error("Coudln't preload {} shader {}: {}", new Object[]{shaderId, identifier, ioexception});
                    return null;
                }
            }
        };

        gpudevice.precompilePipeline(RenderPipelines.GUI, abysmolSource);
        gpudevice.precompilePipeline(RenderPipelines.GUI_TEXTURED, abysmolSource);
        if (TracyClient.isAvailable()) {
            gpudevice.precompilePipeline(RenderPipelines.TRACY_BLIT, abysmolSource);
        }
    }
}
