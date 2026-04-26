package com.wano.abysmol.mixin.ServerLogic;

import com.wano.abysmol.Constants;
import com.wano.abysmol.utils.AbysmolHandshake;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.CustomQueryPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(ServerLoginPacketListenerImpl.class)
public abstract class ServerLoginMixin {
    @Shadow @Final
    Connection connection;
    @Inject(method = "handleHello", at = @At("TAIL"))
    private void abysmol$sendHandshake(ServerboundHelloPacket packet, CallbackInfo ci) {
       AbysmolHandshake.SendAsServer(connection);
    }

    @Inject(method = "handleCustomQueryPacket", at = @At("HEAD"), cancellable = true)
    private void abysmol$verifyHandshake(ServerboundCustomQueryAnswerPacket packet, CallbackInfo ci) {
        ServerLoginPacketListenerImpl self = (ServerLoginPacketListenerImpl)(Object)this;
        if (packet.transactionId() == 1234) {
            CustomQueryAnswerPayload payload = packet.payload();

            if (payload == null) {
                self.disconnect(Component.translatable("abysmol.network.invalid_client", Constants.PLATFORM.getModName()));
            } else {
                try {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    payload.write(buf);
                    buf.readerIndex(0);

                    if (buf.readableBytes() > 0) {
                        int clientProtocol = buf.readInt();
                        String clientVersion = buf.readUtf();

                        if (clientProtocol != Constants.NETWORK_PROTOCOL_VERSION || !Objects.equals(clientVersion, Constants.PLATFORM.getModVersion())) {
                            self.disconnect(Component.translatable("abysmol.network.version_mismatch",
                                    Constants.PLATFORM.getModName(),
                                    Constants.PLATFORM.getModVersion(),
                                    clientVersion
                            ));
                        } else {
                            Constants.LOG.info("{} {}", clientProtocol, clientVersion);
                        }
                    }

                }
                catch (Exception e) {
                    self.disconnect(Component.translatable("abysmol.network.handshake_exception", Constants.PLATFORM.getModName()));
                }
            }
            ci.cancel(); // Prevent vanilla from getting confused by our custom ID
        }
    }
}
