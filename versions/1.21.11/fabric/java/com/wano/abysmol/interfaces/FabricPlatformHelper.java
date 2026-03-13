package com.wano.abysmol.interfaces;
import com.wano.abysmol.Constants;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public String getPlatformVersion(){
        return FabricLoader.getInstance()
                .getModContainer("fabricloader")
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public InputStream loadModResource(String pathInResources){
        Identifier id = Identifier.parse(pathInResources);

        String fullPath = String.format("assets/%s/%s",
                id.getNamespace(),
                id.getPath()
                );

        return FabricLoader.getInstance().getModContainer(id.getNamespace())
                .flatMap(container -> container.findPath(fullPath))
                .map(path -> {
                    try {
                        return Files.newInputStream(path);
                    } catch (IOException e) {
                        Constants.LOG.error("Failed to open stream for: {}", fullPath);
                        return null;
                    }
                }).orElse(null);
    }
}
