package com.wano.abysmol;

public class AbnormalUtils {
    // inverts a integer (0-255) color value that inverts the color provided in the parameter
    public static int invertColor(int color){
        // invert color
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        r = 255 - (r & 0xFF);
        g = 255 - (g & 0xFF);
        b = 255 - (b & 0xFF);

        return  (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int invertColor(int a, int r, int g, int b){
        return (a & 0xFF) << 24
                | (0xFF - r) << 16
                | (0xFF - g) << 8
                | (0xFF - b);
    }
}
