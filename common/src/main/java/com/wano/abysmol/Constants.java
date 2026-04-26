package com.wano.abysmol;

import com.wano.abysmol.interfaces.IPlatformHelper;
import com.wano.abysmol.services.ServiceKey;
import com.wano.abysmol.services.ServicesManager;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final IPlatformHelper PLATFORM = ServicesManager.get(ServiceKey.of(IPlatformHelper.class));
	public static final String MOD_ID = "abysmol";
	public static final Logger LOG = LoggerFactory.getLogger(PLATFORM.getModName());
	public static final Identifier NETWORK_HANDSHAKE = Identifier.fromNamespaceAndPath("abysmol", "check");
	public static final Integer NETWORK_PROTOCOL_VERSION = 1;
}