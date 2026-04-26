package com.wano.abysmol;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ABYSMOL {
    public ABYSMOL(IEventBus modBus) {
        AbysmolAttachments.ATTACHMENTS.register(modBus);
    }
}

