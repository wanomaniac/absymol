package com.wano.abysmol;

public class AbsymolDefinitions {
    public static void initalizeAbsymolAtPreLaunch(){
        AbsymolShaders.init();
        AbysmolPipelineOverrider.init();
    }

    public static void initalizeAbsymolAtMain(){
        AbsymolEnchantments.init();
        AbsymolItems.init();
        AbsymolBlocks.init();
    }

    public static void initalizeAbsymolAtPostLaunch(){

    }
}
