package com.wano.abysmol.utils;

import com.wano.abysmol.Constants;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class AbysmolHandshake {
    public static void SendAsServer(Connection connection){
        connection.send(new ClientboundCustomQueryPacket(1234, new CustomQueryPayload() {
            @Override
            public @NonNull Identifier id() {
                return Constants.NETWORK_HANDSHAKE;
            }

            @Override
            public void write(@NonNull FriendlyByteBuf friendlyByteBuf) {
                friendlyByteBuf.writeInt(Constants.NETWORK_PROTOCOL_VERSION); // Protocol Version
                friendlyByteBuf.writeUtf(Constants.PLATFORM.getModVersion()); // Mod Version
            }
        }));
    }

    public static void SendAsClient(Connection connection){
        connection.send(new ServerboundCustomQueryAnswerPacket(1234, friendlyByteBuf -> {
            friendlyByteBuf.writeInt(Constants.NETWORK_PROTOCOL_VERSION); // Protocol Version
            friendlyByteBuf.writeUtf(Constants.PLATFORM.getModVersion()); // Mod Version
        }));
    }
}
