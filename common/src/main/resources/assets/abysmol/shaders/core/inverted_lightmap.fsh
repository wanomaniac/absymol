////#version 330
////
////layout(std140) uniform LightmapInfo {
////    float AmbientLightFactor;
////    float SkyFactor;
////    float BlockFactor;
////    float NightVisionFactor;
////    float DarknessScale;
////    float DarkenWorldFactor;
////    float BrightnessFactor;
////    vec3 SkyLightColor;
////    vec3 AmbientColor;
////} lightmapInfo;
////
////in vec2 texCoord;
////
////out vec4 fragColor;
////
////float get_brightness(float level) {
////    return level / (4.0 - 3.0 * level);
////}
////
////vec3 notGamma(vec3 color) {
////    float maxComponent = max(max(color.x, color.y), color.z);
////    float maxInverted = 1.0f - maxComponent;
////    float maxScaled = 1.0f - maxInverted * maxInverted * maxInverted * maxInverted;
////    return color * (maxScaled / maxComponent);
////}
////
////void main() {
////    float block_raw = texCoord.x;
////    float sky_raw = texCoord.y;
////
////    // 1. Get standard brightness (0.0 = Dark, 1.0 = Bright)
////    float block_light = get_brightness(block_raw);
////    float sky_light = get_brightness(sky_raw);
////
////    // 2. Combine them into a single "Light Level"
////    // We use max so that a torch in a dark cave provides 'light'
////    float total_light = max(block_light * lightmapInfo.BlockFactor, sky_light * lightmapInfo.SkyFactor);
////
////    // 3. THE INVERSION: Make shadows white
////    // Instead of: color = total_light (0 is black)
////    // We use: color = 1.0 - total_light (0 is now 1.0/white)
////    float shadow_logic = 1.0 - total_light;
////
////    // 4. Color Construction
////    // We base the color on our 'shadow_logic'.
////    // Areas with 0 light now have a value of 1.0 (White).
////    vec3 color = vec4(shadow_logic).rgb;
////
////    // 5. Apply Tints
////    // We want the 'Dark' areas (torches) to have a bit of color contrast
////    vec3 torch_darkness = vec3(0.2, 0.15, 0.1); // The color of the 'dark' torch hole
////    color = mix(color, torch_darkness, block_raw * 0.6);
////
////    // 6. Ambient & Sky Factors
////    // We mix in the AmbientColor from the uniform to prevent "pure" #FFFFFF white
////    color = mix(color, lightmapInfo.AmbientColor, lightmapInfo.AmbientLightFactor * 0.2);
////
////    // 7. Night Vision & Darkness
////    // Night vision usually makes things bright; here it should 'flatten' the white
////    if (lightmapInfo.NightVisionFactor > 0.0) {
////        color = mix(color, vec4(0.5).rgb, lightmapInfo.NightVisionFactor);
////    }
////
////    // 8. Final Polish
////    color = clamp(color, 0.0, 1.0);
////
////    // Applying a slight gamma lift to keep the whites "clean"
////    color = pow(color, vec3(0.8));
////
////
////
////    fragColor = vec4(color, 1.0);
////}
//
//#version 330
//
//layout(std140) uniform LightmapInfo {
//    float AmbientLightFactor;
//    float SkyFactor;
//    float BlockFactor;
//    float NightVisionFactor;
//    float DarknessScale;
//    float DarkenWorldFactor;
//    float BrightnessFactor;
//    vec3 SkyLightColor;
//    vec3 AmbientColor;
//} lightmapInfo;
//
//in vec2 texCoord;
//
//out vec4 fragColor;
//
//float get_brightness(float level) {
//    return level / (4.0 - 3.0 * level);
//}
//
//vec3 notGamma(vec3 color) {
//    float maxComponent = max(max(color.x, color.y), color.z);
//    float maxInverted = 1.0f - maxComponent;
//    float maxScaled = 1.0f - maxInverted * maxInverted * maxInverted * maxInverted;
//    return color * (maxScaled / maxComponent);
//}
//
//void main() {
//    float block_brightness = get_brightness(floor(texCoord.x * 16) / 15) * lightmapInfo.BlockFactor;
//    float sky_brightness = get_brightness(floor(texCoord.y * 16) / 15) * lightmapInfo.SkyFactor;
//
//    // cubic nonsense, dips to yellowish in the middle, white when fully saturated
//    vec3 color = vec3(
//    block_brightness,
//    block_brightness * ((block_brightness * 0.6 + 0.4) * 0.6 + 0.4),
//    block_brightness * (block_brightness * block_brightness * 0.6 + 0.4)
//    );
//
//    color = mix(color, lightmapInfo.AmbientColor, lightmapInfo.AmbientLightFactor);
//
//    color += lightmapInfo.SkyLightColor * sky_brightness;
//    color = mix(color, vec3(0.75), 0.04);
//
//    if (lightmapInfo.AmbientLightFactor == 0.0f) {
//        vec3 darkened_color = color * vec3(0.7, 0.6, 0.6);
//        color = mix(color, darkened_color, lightmapInfo.DarkenWorldFactor);
//    }
//
//    if (lightmapInfo.NightVisionFactor > 0.0) {
//        // scale up uniformly until 1.0 is hit by one of the colors
//        float max_component = max(color.r, max(color.g, color.b));
//        if (max_component < 1.0) {
//            vec3 bright_color = color / max_component;
//            color = mix(color, bright_color, lightmapInfo.NightVisionFactor);
//        }
//    }
//
//    if (lightmapInfo.AmbientLightFactor == 0.0f) {
//        color = color - vec3(lightmapInfo.DarknessScale);
//    }
//
//    color = clamp(color, 0.0, 1.0);
//
//    vec3 notGamma = notGamma(color);
//    color = mix(color, notGamma, lightmapInfo.BrightnessFactor);
//    color = mix(color, vec3(0.75), 0.04);
//
//    color = 1.0 - color;
//    fragColor = vec4(color, 1.0);
//}


#version 330

layout(std140) uniform LightmapInfo {
    float AmbientLightFactor;
    float SkyFactor;
    float BlockFactor;
    float NightVisionFactor;
    float DarknessScale;
    float DarkenWorldFactor;
    float BrightnessFactor;
    vec3 SkyLightColor;
    vec3 AmbientColor;
} lightmapInfo;

in vec2 texCoord;

out vec4 fragColor;

float get_brightness(float level) {
    return level / (4.0 - 3.0 * level);
}

vec3 notGamma(vec3 color) {
    float maxComponent = max(max(color.x, color.y), color.z);
    float maxInverted = 1.0f - maxComponent;
    float maxScaled = 1.0f - maxInverted * maxInverted * maxInverted * maxInverted;
    return color * (maxScaled / maxComponent);
}

void main() {
    float block_brightness = get_brightness(floor(texCoord.x * 16) / 15) * lightmapInfo.BlockFactor;
    float sky_brightness = get_brightness(floor(texCoord.y * 16) / 15) * lightmapInfo.SkyFactor;

    // cubic nonsense, dips to yellowish in the middle, white when fully saturated
    sky_brightness = max(sky_brightness, 0.05);
    vec3 color = vec3(
    block_brightness,
    block_brightness * ((block_brightness * 0.6 + 0.4) * 0.6 + 0.4),
    block_brightness * (block_brightness * block_brightness * 0.6 + 0.4)
    );

    color = mix(color, lightmapInfo.AmbientColor, lightmapInfo.AmbientLightFactor);

    color += lightmapInfo.SkyLightColor * sky_brightness;
    color = mix(color, vec3(0.75), 0.06);

    if (lightmapInfo.AmbientLightFactor == 0.0f) {
        vec3 darkened_color = color * vec3(0.7, 0.6, 0.6);
        color = mix(color, darkened_color, lightmapInfo.DarkenWorldFactor);
    }

    if (lightmapInfo.NightVisionFactor > 0.0) {
        // scale up uniformly until 1.0 is hit by one of the colors
        float max_component = max(color.r, max(color.g, color.b));
        if (max_component < 1.0) {
            vec3 bright_color = color / max_component;
            color = mix(color, bright_color, lightmapInfo.NightVisionFactor);
        }
    }

    if (lightmapInfo.AmbientLightFactor == 0.0f) {
        color = color - vec3(lightmapInfo.DarknessScale);
    }

    color = clamp(color, 0.0, 1.0);

    vec3 notGamma = notGamma(color);
    color = mix(color, notGamma, lightmapInfo.BrightnessFactor);
    color = mix(color, vec3(0.75), 0.04);

    fragColor = vec4(color, 1.0);
}
