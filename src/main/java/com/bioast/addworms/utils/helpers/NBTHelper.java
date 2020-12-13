package com.bioast.addworms.utils.helpers;

import com.bioast.addworms.AddWorms;
import net.minecraft.item.Food;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.Level;

public class NBTHelper {

    /**
     * @param foodIn the food that need to be writen
     * @param tagIn tag to write the food to
     * @return only return the food tag
     */
    public static CompoundNBT writeFoodToNBT(Food foodIn,CompoundNBT tagIn){
        CompoundNBT foodTag = new CompoundNBT();

        foodTag.putInt(Tags.TAG_HUNGER,foodIn.getHealing());
        foodTag.putFloat(Tags.TAG_SAT,foodIn.getSaturation());
        foodTag.putBoolean(Tags.TAG_CAN_EAT_WHEN_FULL,foodIn.canEatWhenFull());
        foodTag.putBoolean(Tags.TAG_IS_FAST_EATING,foodIn.isFastEating());
        foodTag.putBoolean(Tags.TAG_IS_MEAT,foodIn.isMeat());

        tagIn.put(Tags.TAG_FOOD_HEADER,foodTag);
        return foodTag;
    }
    public static Food readFoodFromNBT(CompoundNBT nbt){
        try {
            if(nbt.contains(Tags.TAG_FOOD_HEADER)){
                Food food =
                        new Food.Builder().hunger(nbt.getInt(Tags.TAG_HUNGER)).saturation(nbt.getFloat(Tags.TAG_SAT)).build();
                return food;
            }
        }
        catch (Exception e){
            AddWorms.LOGGER.log(Level.ERROR,String.format("ItemStack at & is not a custome food NBT : ERROR &",nbt, e));
            return null;
        }
        return null;
    }

    public class Tags {
        //Foods
        public static final String TAG_FOOD_HEADER = "Food";
        public static final String TAG_HUNGER = "Hunger";
        public static final String TAG_SAT = "Saturation";
        public static final String TAG_CAN_EAT_WHEN_FULL = "CanEatWhenFull";
        public static final String TAG_IS_FAST_EATING = "IsFastEating";
        public static final String TAG_IS_MEAT = "IsMeat";
    }
}
