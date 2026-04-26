package com.wano.abysmol.mixin.Client.Sound;

import com.mojang.blaze3d.audio.Channel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.lwjgl.openal.AL10;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(Channel.class)
public class ChannelMixin {
    @Shadow
    private int source;

    @Inject(
            method = "setPitch(F)V",
            at = @At("HEAD"),
            cancellable = true)
    public void setPitch(final float pitch, CallbackInfo ci) {
        ci.cancel();
        float finalPitch;

        ClientLevel level = Minecraft.getInstance().level;
        ThreadLocalRandom threadRandom = ThreadLocalRandom.current();
        if (level != null) {
            long time = level.getDayTime() % 24000L;

            boolean isNight = time > 13000 && time < 23000;
            float timeModifier = isNight ? 1.2f : 0.8f;
            float randomness = (threadRandom.nextFloat() * 0.2f) - 0.1f;
            finalPitch = pitch * timeModifier + randomness;
        } else {
            float fallbackRandom = (float) ((threadRandom.nextFloat() * 0.4) - 0.2);
            finalPitch = pitch + fallbackRandom;
        }

        AL10.alSourcef(this.source, 4099, Math.max(0.1f, finalPitch));
    }
}
