package com.wano.abysmol;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class AbysmolFabricPrelaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        AbysmolInitalizations.initalizeAbysmolAtPreLaunch();
    }
}
