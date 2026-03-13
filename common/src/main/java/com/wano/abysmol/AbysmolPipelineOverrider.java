package com.wano.abysmol;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AbysmolPipelineOverrider {
    public static final Map<String, String> VERTEX_SHADER_OVERRIDE = new HashMap<>();
    public static final Map<String, String> FRAGMENT_SHADER_OVERRIDE = new HashMap<>();

    public static void init() {
        String platformName = Constants.PLATFORM.getPlatformName().toLowerCase();
        InputStream streamCore = AbysmolPipelineOverrider.class.getResourceAsStream("/assets/absymol/shaders/injector.json");
        InputStream streamModLoader = AbysmolPipelineOverrider.class.getResourceAsStream("/assets/absymol/shaders/injector." + platformName + ".json");

        // Cannot run with the core shader replacements.
        if (streamCore == null) {
            throw new IllegalStateException("Abysmol Core shader mappings are missing! Should be at /assets/absymol/shaders/injector.json but it ain't there!!! Reinstall mod!!!");
        }

        if (streamModLoader == null) {
            Constants.LOG.warn("Platform-specific shader mappings for {} not found. This is a dev warning but it's probably a good idea to put it in there for special stuff.", platformName);
        }

        try {
            abysmol$applyJsonToMap(streamCore);
            if (streamModLoader != null) {
                abysmol$applyJsonToMap(streamModLoader);
            }
        } catch (Exception e) {
            Constants.LOG.error("Failed to parse shader injector JSONs!", e);
        }
    }

    private static void abysmol$applyJsonToMap(InputStream stream) {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("vertex")) {
                JsonObject vertexObject = json.getAsJsonObject("vertex");
                if(!vertexObject.isJsonNull()) {
                    vertexObject.entrySet().forEach(entry ->
                            VERTEX_SHADER_OVERRIDE.put(entry.getKey(), entry.getValue().getAsString())
                    );
                }
            }
            if (json.has("fragment")) {
                JsonObject fragmentObject = json.getAsJsonObject("fragment");
                if(!fragmentObject.isJsonNull()) {
                    fragmentObject.entrySet().forEach(entry ->
                            FRAGMENT_SHADER_OVERRIDE.put(entry.getKey(), entry.getValue().getAsString())
                    );
                }
            }

        } catch (IOException e) {
            Constants.LOG.error("Error reading shader stream", e);
        }
    }

}
