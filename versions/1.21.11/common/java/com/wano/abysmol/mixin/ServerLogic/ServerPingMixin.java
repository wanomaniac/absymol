package com.wano.abysmol.mixin.ServerLogic;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.resources.Identifier;
import net.minecraft.server.network.ServerStatusPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(ServerStatusPacketListenerImpl.class)
public class ServerPingMixin {
    @ModifyArg(
            method = "handleStatusRequest",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V")
    )
    public Packet<?> replaceStatusWithModTag(Packet<?> packet) {
        if (packet instanceof ClientboundStatusResponsePacket(ServerStatus originalStatus)) {
            ServerStatus.Version originalVersion = originalStatus.version().orElseGet(ServerStatus.Version::current);
            ServerStatus.Version taggedVersion = new ServerStatus.Version(
                    originalVersion.name() + " [ABYSMOL]",
                    originalVersion.protocol()
            );

            ServerStatus modifiedStatus = new ServerStatus(
                    originalStatus.description(),
                    originalStatus.players(),
                    Optional.of(taggedVersion),
                    originalStatus.favicon(),
                    originalStatus.enforcesSecureChat()
            );

            return new ClientboundStatusResponsePacket(modifiedStatus);
        }

        return packet; // Fallback if it's not the packet we expect
    }
}
