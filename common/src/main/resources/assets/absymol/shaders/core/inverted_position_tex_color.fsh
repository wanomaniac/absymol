#version 330

// Can't moj_import in things used during startup, when resource packs don't exist.
// This is a copy of dynamicimports.glsl
layout(std140) uniform DynamicTransforms {
    mat4 ModelViewMat;
    vec4 ColorModulator;
    vec3 ModelOffset;
    mat4 TextureMat;
};

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    // Invert vertex color
    vec3 vertexColorInvRGB = vertexColor.rgb;
    vec4 vertexColorInvRGBA = vec4(vertexColorInvRGB, vertexColor.a);
    // Invert texture color
    vec4 textureSampleRGBA = texture(Sampler0, texCoord0);
    vec3 textureInvertedRGB = vec3(1.0) - textureSampleRGBA.rgb;

    vec4 color = vec4(textureInvertedRGB, textureSampleRGBA.a) * vertexColorInvRGBA;
    if (color.a == 0.0) {
        discard;
    }
    fragColor = color * ColorModulator;
}
