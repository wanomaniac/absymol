#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

// Glyph's textures may be broken but i will leave the vertexColor out as it's inverted by CPU auto
void main() {
    // Invert texture color
    vec4 textureSampleRGBA = texture(Sampler0, texCoord0);
    vec3 textureInvertedRGB = vec3(1.0) - textureSampleRGBA.rgb;

    vec4 color = vec4(textureInvertedRGB, textureSampleRGBA.a) * vertexColor * ColorModulator;
    if (color.a < 0.1) {
        discard;
    }
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
