package com.wano.abysmol.mixin.Client;

import com.wano.abysmol.Constants;
import com.wano.abysmol.utils.AbysmolHandshake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundLoginFinishedPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ClientHandshakePacketListenerImpl.class)
public class ClientLoginMixin {
    @Shadow @Final private Connection connection;
    @Shadow @Final private Consumer<Component> updateStatus;
    @Unique private boolean abysmol$validated = false;

    @Inject(method = "handleCustomQuery", at = @At("HEAD"), cancellable = true)
    private void abysmol$onCustomQuery(ClientboundCustomQueryPacket packet, CallbackInfo ci) {
        if (packet.transactionId() == 1234) {
            updateStatus.accept(Component.translatable("connect.negotiating"));
            AbysmolHandshake.SendAsClient(connection);
            this.abysmol$validated = true;
            ci.cancel();
        }
    }

    @Inject(method = "handleLoginFinished", at = @At("HEAD"), cancellable = true)
    private void abysmol$verifyServerIsAbysmol(ClientboundLoginFinishedPacket packet, CallbackInfo ci) {
        if(Minecraft.getInstance().isSingleplayer()) return;

        if (!this.abysmol$validated) {
            this.connection.disconnect(Component.translatable("abysmol.network.invalid_server", Constants.PLATFORM.getModName()));
            ci.cancel();
        }
    }
}
