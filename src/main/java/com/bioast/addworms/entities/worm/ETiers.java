package com.bioast.addworms.entities.worm;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.item.ItemStack;

public enum ETiers {
    Wild(1, 1, 1.0f, 2.5f),
    Basic(2, 2, 1.5f, 5f),
    Enhanced(3, 2, 2.0f, 7.5f),
    Balanced(4, 3, 2.5f, 12f),
    Autistic(5, 3, 3.0f, 25f),
    Heroic(6, 3, 4.0f, 50f),
    Majestic(7, 3, 4.5f, 80f),
    Celestial(8, 4, 5f, 80f),
    Global(9, 5, 6f, 120f),
    Clay(10, 6, 7f, 500f),
    Rat(99, 10, 10f, 999f);

    public int level;
    public int range;
    public float speed;
    public float damage;

    ETiers(int level, int range, float speed, float damage) {
        this.level = level;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }

    public static ETiers getWithLevel(int lvlIn) {
        if (lvlIn > ETiers.values().length) {
            switch (lvlIn) {
                case 99:
                    return ETiers.valueOf("Rat");
                default:
                    return null;
            }
        }
        return ETiers.values()[lvlIn - 1];
    }

    String getTranslationKey() {
        return "worms.tiers." + AddWorms.MODID.toLowerCase() + this.toString().toLowerCase();
    }

    void setTier(ItemStack itemStackIn) {
        NBTHelper.addWormTierToStack(itemStackIn, this);
    }

    public static class TranslationKeyOfStats {
        static final String prefix = "worms.tiers.stats." + AddWorms.MODID.toLowerCase() + ".";

        public static final String KEY_LEVEL = prefix + "level";
        public static final String KEY_RANGE = prefix + "range";
        public static final String KEY_DMG = prefix + "damage";
        public static final String KEY_SPD = prefix + "speed";
    }
}
