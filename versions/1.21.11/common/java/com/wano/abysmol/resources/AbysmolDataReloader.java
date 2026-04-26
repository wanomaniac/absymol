package com.wano.abysmol.resources;

import com.wano.abysmol.AbysmolPipelineOverrider;
import com.wano.abysmol.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AbysmolDataReloader implements PreparableReloadListener {
    @Override
    public @NonNull CompletableFuture<Void> reload(@NonNull SharedState sharedState, @NonNull Executor backgroundExecutor, PreparationBarrier preparationBarrier, @NonNull Executor gameExecutor) {
        return CompletableFuture.runAsync(AbysmolPipelineOverrider::reinit, backgroundExecutor)
                .thenCompose(preparationBarrier::wait)
                .thenRunAsync(() -> {
                    if(Constants.PLATFORM.isClient()) {
                        Minecraft client = Minecraft.getInstance();
                        client.reloadResourcePacks();
                    }
                }, gameExecutor);
    }
}