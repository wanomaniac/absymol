package com.wano.abysmol.interfaces;
import com.wano.abysmol.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
    public String getModVersion(){
        return FabricLoader.getInstance()
                .getModContainer(Constants.MOD_ID)
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }

    @Override
    public String getModName(){
        return FabricLoader.getInstance()
                .getModContainer(Constants.MOD_ID)
                .map(mod -> mod.getMetadata().getName())
                .orElse("unknown");
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public boolean IsServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
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
