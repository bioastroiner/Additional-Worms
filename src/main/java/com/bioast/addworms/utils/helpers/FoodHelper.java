package com.bioast.addworms.utils.helpers;

import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class FoodHelper {

    public static CompoundNBT writeFoodData(Food foodIn, CompoundNBT nbtIn){
        CompoundNBT nbtFood = new CompoundNBT();
        nbtFood.putString("name",foodIn.toString());
        nbtFood.putInt("hunger",foodIn.getHealing());
        nbtFood.putFloat("saturation",foodIn.getSaturation());
        nbtIn.put("food",nbtFood);
        return nbtIn;
    }

    public static ItemStack generateFood(ItemStack itemStackIn,Food foodIn){
        itemStackIn.deserializeNBT(writeFoodData(foodIn,itemStackIn.serializeNBT()));
        CompoundNBT nbt = (CompoundNBT) itemStackIn.serializeNBT().get("food");
        Food food = new Food.Builder()
                .hunger(nbt.getInt("hunger"))
                .saturation(nbt.getFloat("saturation"))
                .setAlwaysEdible()
                .fastToEat()
                .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.5f)
                .build();
        itemStackIn.setDisplayName(new StringTextComponent(nbt.getString("name")).applyTextStyle(TextFormatting.AQUA));
        return itemStackIn;
    }
}
