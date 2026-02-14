#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec4 vertexColor;

out vec4 fragColor;

void main() {
    // Invert vertex color
    vec3 vertexColorInvRGB = vec3(1.0) - vertexColor.rgb;
    vec4 vertexColorInvRGBA = vec4(vertexColorInvRGB, vertexColor.a);

    vec4 color = vertexColorInvRGBA;
    if (color.a < 0.1) {
        discard;
    }
    fragColor = color * ColorModulator;
}
