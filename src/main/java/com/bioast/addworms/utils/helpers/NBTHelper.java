package com.bioast.addworms.utils.helpers;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.entities.worm.ETiers;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class NBTHelper {

    //******************FOOD******************

    /**
     * @param foodIn the food that need to be writen
     * @param tagIn  tag to write the food to
     * @return only return the food tag
     */
    public static CompoundNBT writeFoodToNBT(@Nonnull Food foodIn, CompoundNBT tagIn, @Nullable Item foodItem) {
        CompoundNBT foodTag = new CompoundNBT();
        if (foodItem != null) {
            if (!(foodItem.getFood() == foodIn))
                foodIn = foodItem.getFood();
            foodTag.putInt(Tags.TAG_FOOD_ID,
                    Item.getIdFromItem(foodItem));
        }
        assert foodIn != null;
        foodTag.putInt(Tags.TAG_FOOD_HUNGER,
                foodIn.getHealing());
        foodTag.putFloat(Tags.TAG_FOOD_SAT,
                foodIn.getSaturation());
        foodTag.putBoolean(Tags.TAG_FOOD_CAN_EAT_WHEN_FULL,
                foodIn.canEatWhenFull());
        foodTag.putBoolean(Tags.TAG_FOOD_IS_FAST_EATING,
                foodIn.isFastEating());
        foodTag.putBoolean(Tags.TAG_FOOD_IS_MEAT,
                foodIn.isMeat());
        tagIn.put(Tags.TAG_FOOD_HEADER, foodTag);
        return foodTag;
    }

    public static Food readFoodFromNBT(CompoundNBT nbt) {
        try {
            if (nbt.contains(Tags.TAG_FOOD_HEADER)) {
                return new Food.Builder()
                        .hunger(((CompoundNBT) nbt.get(Tags.TAG_FOOD_HEADER)).getInt(Tags.TAG_FOOD_HUNGER))
                        .saturation(((CompoundNBT) nbt.get(Tags.TAG_FOOD_HEADER)).getFloat(Tags.TAG_FOOD_SAT))
                        .build();
            }
        } catch (Exception e) {
            AddWorms.LOGGER.log(Level.ERROR, String.format("ItemStack at & is not a custome food NBT : ERROR &", nbt,
                    e));
            return null;
        }
        return null;
    }
    //********************Tiers**************************

    public static void addWormTierToStack(ItemStack itemStackIn, ETiers tiers) {
        itemStackIn.getOrCreateTag().putInt(NBTHelper.Tags.TAG_WORM_LVL, tiers.level);
        itemStackIn.getOrCreateTag().putInt(NBTHelper.Tags.TAG_WORM_RANGE, tiers.range);
        itemStackIn.getOrCreateTag().putFloat(NBTHelper.Tags.TAG_WORM_DAMAGE, tiers.damage);
        itemStackIn.getOrCreateTag().putFloat(NBTHelper.Tags.TAG_WORM_SPEED, tiers.speed);
    }

    public static ETiers readWormTierFromStack(ItemStack itemStackIn) {
        int lvl = itemStackIn.getOrCreateTag()
                .getInt(Tags.TAG_WORM_LVL);
        return ETiers.getWithLevel(lvl);
    }

    //*************************************************
    public static void addDataToItemStack(ItemStack itemStack, String key, Boolean value) {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean(key, value);
        itemStack.setTag(tag);
    }

    public static class Tags {
        //Foods
        public static final String TAG_FOOD_HEADER = "Food";
        public static final String TAG_FOOD_ID = "FoodID";
        public static final String TAG_FOOD_HUNGER = "Hunger";
        public static final String TAG_FOOD_SAT = "Saturation";
        public static final String TAG_FOOD_CAN_EAT_WHEN_FULL = "CanEatWhenFull";
        public static final String TAG_FOOD_IS_FAST_EATING = "IsFastEating";
        public static final String TAG_FOOD_IS_MEAT = "IsMeat";
        //WormTiers
        public static final String TAG_WORM_LVL = "Worm_lvl";
        public static final String TAG_WORM_SPEED = "Worm_Speed";
        public static final String TAG_WORM_DAMAGE = "Worm_Damage";
        public static final String TAG_WORM_RANGE = "Worm_Range";
    }
}
