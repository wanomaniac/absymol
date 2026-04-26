package com.wano.abysmol.mixin.Client.Graphics.gui;

import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {
    @ModifyArg(
            method = "renderItemToAtlas",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/TrackingItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"
            ),
            index = 2 // The third argument (0-indexed) is the packedLight integer
    )
    private int forceLowestLight(int original) {
        // 0 is the absolute minimum brightness
        return 15728880;
    }
}
