package com.wano.abysmol.logic;

import com.wano.abysmol.AbysmolEnchantments;
import com.wano.abysmol.mixinAccessors.PhotosynthesisAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class PhotosynthesisTools {
    public static int getMaxSolarPotential(LivingEntity self) {
        var enchantRegistry = self.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var photoEnchant = enchantRegistry.get(AbysmolEnchantments.PHOTOSYNTHESIS);

        if (photoEnchant.isPresent()) {
            int maxPerPiece = photoEnchant.get().value().getMaxLevel();
            // 4 Armor Slots + 1 Main Hand = 5 potential slots
            return maxPerPiece * 5;
        }
        return 20; // Default fallback
    }

    public static int getPhotosynthesisLevel4Server(LivingEntity self){
        if(self.level().isClientSide()) return 0;

        int baseLevel = 0;
        int totalEnchantLevel = 0;
        var enchantRegistry = self.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var photoEnchant = enchantRegistry.get(AbysmolEnchantments.PHOTOSYNTHESIS);
        if (photoEnchant.isPresent()) {
            var enchantmentHolder = photoEnchant.get();
            EquipmentSlot[] targetSlots = {
                    EquipmentSlot.FEET,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.HEAD,
                    EquipmentSlot.MAINHAND,
                    EquipmentSlot.BODY //
            };

            for (EquipmentSlot slot : targetSlots) {
                ItemStack stack = self.getItemBySlot(slot);
                if (!stack.isEmpty()) {
                    totalEnchantLevel += EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, stack);
                }
            }
        }


        int totalSolarLevel = baseLevel + totalEnchantLevel;
        if(self instanceof Mob && totalSolarLevel == 0) totalSolarLevel = 2;
        return totalSolarLevel;
    }

    public static int getPhotosynthesisLevel4ServerNoMobEnhancement(LivingEntity self){
        if(self.level().isClientSide()) return 0;

        int baseLevel = 0;
        int totalEnchantLevel = 0;
        var enchantRegistry = self.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var photoEnchant = enchantRegistry.get(AbysmolEnchantments.PHOTOSYNTHESIS);
        if (photoEnchant.isPresent()) {
            var enchantmentHolder = photoEnchant.get();
            EquipmentSlot[] targetSlots = {
                    EquipmentSlot.FEET,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.HEAD,
                    EquipmentSlot.MAINHAND,
                    EquipmentSlot.BODY //
            };

            for (EquipmentSlot slot : targetSlots) {
                ItemStack stack = self.getItemBySlot(slot);
                if (!stack.isEmpty()) {
                    totalEnchantLevel += EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, stack);
                }
            }
        }


        int totalSolarLevel = baseLevel + totalEnchantLevel;
        return totalSolarLevel;
    }

    public static int getPhotosynthesisLevel4Client(LivingEntity self){
        return ((PhotosynthesisAccessor)self).abysmol$solarEnhancementsLevel();
    }

    public static boolean isEntityPhotosynthesized4Client(LivingEntity self) {
        return ((PhotosynthesisAccessor)self).abysmol$solarEnhancements();
    }

    public static boolean isEntityPhotosynthesized(LivingEntity self) {
        if (self.isSpectator()) return false; // bug - spectators have photosynthesis abilities, not allowed.
        // 1. Check if it's day (Only if this is a SUN-based mechanic)
        if (self.level().isDarkOutside()) return false;

        // 2. Use the light-level check (f > 0.5F)
        float brightness = self.getLightLevelDependentMagicValue();
        if (brightness <= 0.5F) return false;


        boolean burnsInSun = (self instanceof Player) || getPhotosynthesisLevel4ServerNoMobEnhancement(self) > 0 ||
                (self.getType().is(EntityTypeTags.BURN_IN_DAYLIGHT)) && self.level().environmentAttributes().getValue(EnvironmentAttributes.MONSTERS_BURN, self.position());

        if (!burnsInSun) return false;

        // 4. Weather & Sky Access
        BlockPos eyePos = BlockPos.containing(self.getX(), self.getEyeY(), self.getZ());
        boolean badWeather = self.isInWaterOrRain() || self.isInPowderSnow;

        // canSeeSky is generally safe on client, but check isLoaded to be sure
        return !badWeather && self.level().canSeeSky(eyePos);
    }


}
