#version 330

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    // Invert vertex color
    vec3 vertexColorInvRGB = vec3(1.0) - vertexColor.rgb;
    vec4 vertexColorInvRGBA = vec4(vertexColorInvRGB, vertexColor.a);
    // Invert texture color
    vec4 textureSampleRGBA = texture(Sampler0, texCoord0);
    vec3 textureInvertedRGB = vec3(1.0) - textureSampleRGBA.rgb;

    vec4 color = vec4(textureInvertedRGB, textureSampleRGBA.a).rrrr * vertexColorInvRGBA;
    if (color.a < 0.1) {
        discard;
    }
    fragColor = color * ColorModulator;
}
