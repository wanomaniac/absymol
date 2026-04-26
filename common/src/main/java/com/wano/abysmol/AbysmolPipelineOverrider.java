package com.wano.abysmol;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class AbysmolPipelineOverrider {
    public record PipelineLocationData(String vertexShader, String fragmentShader) {}

    public static final Map<String, PipelineLocationData> LOCATION_SHADER_OVERRIDE = new HashMap<>();
    public static final Map<String, String> VERTEX_SHADER_OVERRIDE = new HashMap<>();
    public static final Map<String, String> FRAGMENT_SHADER_OVERRIDE = new HashMap<>();

    public static void reinit(){
        LOCATION_SHADER_OVERRIDE.clear();
        VERTEX_SHADER_OVERRIDE.clear();
        FRAGMENT_SHADER_OVERRIDE.clear();
        init();
    }

    public static void init() {
        if(Constants.PLATFORM.isClient()) {
            String platformName = Constants.PLATFORM.getPlatformName().toLowerCase();
            InputStream streamCore = AbysmolPipelineOverrider.class.getResourceAsStream("/assets/abysmol/shaders/injector.json");
            InputStream streamModLoader = AbysmolPipelineOverrider.class.getResourceAsStream("/assets/abysmol/shaders/injector." + platformName + ".json");

            // Cannot run with the core shader replacements.
            if (streamCore == null) {
                throw new IllegalStateException("Abysmol Core shader mappings are missing! Should be at /assets/abysmol/shaders/injector.json but it ain't there!!! Reinstall mod!!!");
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
                throw new RuntimeException("Shader mappings are invalid! Please reinstall this mod or report this as a bug");
            }
        }
    }

    private static void abysmol$applyJsonToMap(InputStream stream) {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject jsonGlobal = json.getAsJsonObject("global");
            if (jsonGlobal.has("vertex")) {
                JsonObject vertexObject = jsonGlobal.getAsJsonObject("vertex");
                if(!vertexObject.isJsonNull()) {
                    vertexObject.entrySet().forEach(entry ->
                            VERTEX_SHADER_OVERRIDE.put(entry.getKey(), entry.getValue().getAsString())
                    );
                }
            }
            if (jsonGlobal.has("fragment")) {
                JsonObject fragmentObject = jsonGlobal.getAsJsonObject("fragment");
                if(!fragmentObject.isJsonNull()) {
                    fragmentObject.entrySet().forEach(entry ->
                            FRAGMENT_SHADER_OVERRIDE.put(entry.getKey(), entry.getValue().getAsString())
                    );
                }
            }
            JsonObject jsonLocation = json.getAsJsonObject("locations");
            if (jsonLocation != null) {
                for (Map.Entry<String, JsonElement> entry : jsonLocation.entrySet()) {
                    String pipelinePath = entry.getKey();

                    // Ensure the value is actually an object before parsing
                    if (entry.getValue().isJsonObject()) {
                        JsonObject shaders = entry.getValue().getAsJsonObject();

                        // Extract the strings, providing an empty string fallback if the key is missing
                        String vsh = shaders.has("vertex") ? shaders.get("vertex").getAsString() : "";
                        String fsh = shaders.has("fragment") ? shaders.get("fragment").getAsString() : "";

                        // Store it in your map
                        LOCATION_SHADER_OVERRIDE.put(pipelinePath, new PipelineLocationData(vsh, fsh));
                    }
                }
            }


        } catch (IOException e) {
            Constants.LOG.error("Error reading shader stream", e);
            throw new RuntimeException("Shader mappings are invalid! Please reinstall this mod or report this as a bug");
        }
    }

}
