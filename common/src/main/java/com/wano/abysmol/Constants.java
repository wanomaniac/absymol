package com.wano.abysmol;

import com.wano.abysmol.interfaces.IPlatformHelper;
import com.wano.abysmol.services.ServiceKey;
import com.wano.abysmol.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final String MOD_ID = "absymol";
	public static final String MOD_NAME = "ABYSMOL";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final IPlatformHelper PLATFORM = ServicesManager.get(ServiceKey.of(IPlatformHelper.class));
}