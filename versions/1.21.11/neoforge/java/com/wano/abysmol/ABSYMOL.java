package com.wano.abysmol;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ABSYMOL {
    public ABSYMOL(IEventBus modBus) {
        AbysmolAttachments.ATTACHMENTS.register(modBus);
    }
}

