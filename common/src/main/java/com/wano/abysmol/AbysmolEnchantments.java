package com.wano.abysmol;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class AbysmolEnchantments {
    public static final ResourceKey<@NotNull Enchantment> PHOTOSYNTHESIS = registerKey(
            "photosynthesis"
    );

    public static ResourceKey<@NotNull Enchantment> registerKey(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Constants.MOD_ID, id));
    }

    public static void init() {
    }
}
