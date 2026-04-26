package com.wano.abysmol;

public class AbysmolInitalizations {
    public static void initalizeAbysmolAtPreLaunch(){
        AbysmolShaders.init();
    }

    public static void initalizeAbysmolAtMain(){
        AbysmolEnchantments.init();
        AbysmolItems.init();
        AbysmolBlocks.init();
    }

    public static void initalizeAbysmolAtPostLaunch(){

    }
}
