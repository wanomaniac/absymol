package com.wano.abysmol.interfaces;

import java.io.InputStream;

public interface IPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * @return Gets the version of the platform loader.
     */
    String getPlatformVersion();

    /**
     * Gets the version of the mod.
     * @return Gets the version of the mod.
     */
    String getModVersion();

    /**
     * Gets the name of the mod
     * @return The name of the mod
     */
    String getModName();

    boolean isClient();
    boolean IsServer();

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    public InputStream loadModResource(String pathInResources);

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
}