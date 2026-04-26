package com.wano.abysmol.mixin.Client.Graphics.gui;

import com.wano.abysmol.graphics.PhotosynthesisLevelType;
import com.wano.abysmol.logic.PhotosynthesisTools;
import com.wano.abysmol.mixinAccessors.PhotosynthesisAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Responsible for fixing VIGNETTE,
@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Unique
    RandomSource random = RandomSource.create();

    @Unique
    int framesBlink = 0;
    boolean blinked = false;

    @Unique
    private void abysmol$renderPhotosynthesisLevel(GuiGraphics guiGraphics, PhotosynthesisLevelType heartType, int x, int y, boolean halfHeart, boolean blinking) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, heartType.getSprite(halfHeart, blinking), x, y, 9, 9);
    }

    @Unique
    private void abysmol$renderPhotosynthesisLevels(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentLevel, boolean isActive){
        PhotosynthesisLevelType photosynthesisType = PhotosynthesisLevelType.getForPlayer(player);
        int totalIcons = Mth.ceil((double)maxHealth / 2.0);

        for (int l = 0; l < totalIcons; ++l) {
            int row = l / 10;
            int column = l % 10;
            int posX = x + column * 8;
            int posY = y - row * height;

            // When it's pretty high!
            if (currentLevel >= 8 && isActive) {
                posY += random.nextInt(2);
            }

            if(isActive) {
                if (framesBlink > 150) {
                    framesBlink = 0;
                    blinked = !blinked;
                }
                framesBlink++;
            } else {
                blinked = false;
            }

            if (l == offsetHeartIndex) {
                posY += 2;
            }
            // Renders container!
            this.abysmol$renderPhotosynthesisLevel(guiGraphics, PhotosynthesisLevelType.CONTAINER, posX, posY, false, false);
            int iconValue = l * 2;
            if (iconValue < currentLevel) {
                boolean isHalf = (iconValue + 1 == currentLevel);
                this.abysmol$renderPhotosynthesisLevel(guiGraphics, photosynthesisType, posX, posY, isHalf, blinked);
            }
        }
    }

    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderAirBubbles(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;III)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderPhotosynthesisWithHealth(GuiGraphics guiGraphics, CallbackInfo ci, Player player, // Captured local
                                                int i,         // health
                                                boolean flag,  // blink
                                                long j,        // millis
                                                int k,         // displayHealth
                                                int l,         // left x (armor side)
                                                int i1,        // right x (food side)
                                                int j1,        // baseline y
                                                float f,       // max health
                                                int k1,        // absorption
                                                int l1,        // rows
                                                int i2,        // height
                                                int j2,        // current y tracker
                                                int k2         // regen offset
    ){
        int solarLevel = ((PhotosynthesisAccessor)player).abysmol$solarEnhancementsLevel();
        boolean solarActive = ((PhotosynthesisAccessor)player).abysmol$solarEnhancements();

        if (solarLevel <= 0) return;
        int yAxis = j1 - (l1 - 1) * i2 - i2;
        if (player.getArmorValue() > 0) yAxis -= 10;


        float maxSolar = PhotosynthesisTools.getMaxSolarPotential(player);
        this.abysmol$renderPhotosynthesisLevels(guiGraphics, player, l, yAxis, i2, k2, maxSolar, solarLevel, solarActive);
    }


    @Redirect(
            method = "renderSelectedItemName",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawStringWithBackdrop(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIII)V"
            )
    )
    private void redirectSelectedItemName(
            GuiGraphics guiGraphics,
            Font font,
            Component component,
            int x,
            int y,
            int width,
            int color
    ) {
        guiGraphics.fill(x - 2, y - 2, x + width + 2, y + 9 + 2, 0xFF000000);
        guiGraphics.drawString(font, component, x, y, color, true);
    }

//    @Redirect(
//            method = "renderVignette",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V")
//    )
//    private void cancelVignette(GuiGraphics instance, RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
//        instance.blit(
//                AbysmolShaders.VIGNETTE,
//                atlas,
//                x, y,
//                u, v,
//                width, height,
//                textureWidth, textureHeight,
//                color
//        );
//    }
}

