package com.bioast.addworms.init;

import com.bioast.addworms.enchantments.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;

public class ModEnchantments {

    public static final RegistryObject<Enchantment> UPG = Registration.ENCHANTMENT.register("range_enchantment",
            () -> new EnchantmentBase(Enchantment.Rarity.COMMON, EnchantmentType.create("worm",
                    p -> p.isIn(ModTags.Items.WORM)), EquipmentSlotType.values()));
}
