package com.wano.abysmol.graphics;

import com.wano.abysmol.Constants;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public enum PhotosynthesisLevelType {
    CONTAINER(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hud/photosynthesis/container"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/container_blinking"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/container"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/container_blinking")),
    NORMAL(Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/full"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/full_blinking"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/half"), Identifier.fromNamespaceAndPath(Constants.MOD_ID,"hud/photosynthesis/half_blinking"));

    private final Identifier full;
    private final Identifier fullBlinking;
    private final Identifier half;
    private final Identifier halfBlinking;
    private PhotosynthesisLevelType(final Identifier full, final Identifier fullBlinking, final Identifier half, final Identifier halfBlinking) {
        this.full = full;
        this.fullBlinking = fullBlinking;
        this.half = half;
        this.halfBlinking = halfBlinking;
    }

    public Identifier getSprite(boolean halfHeart, boolean blinking) {
        if (halfHeart) {
            return blinking ? this.halfBlinking : this.half;
        } else {
            return blinking ? this.fullBlinking : this.full;
        }
    }

    public static PhotosynthesisLevelType getForPlayer(Player player) {
        return NORMAL;
    }
}