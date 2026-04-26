package com.wano.abysmol;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbysmolFabricRoot implements ModInitializer {
	public static final String MOD_ID = "inevitable";
    public static final EvitabilityState GlobalModState = new EvitabilityState();
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        AbysmolInitalizations.initalizeAbysmolAtMain();
	}
}