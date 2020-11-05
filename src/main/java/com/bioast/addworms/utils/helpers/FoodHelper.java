package com.bioast.addworms.utils.helpers;

import com.bioast.addworms.items.DigestedFood;
import com.bioast.addworms.utils.interfaces.IFoodable;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

// NOTE: this class is effected by accesstransformer witch let it manipulate Food in an Item
public class FoodHelper {

    public static CompoundNBT writeFoodData(Food foodIn, CompoundNBT nbtIn,float multiplier){
        CompoundNBT nbtFood = new CompoundNBT();
        nbtFood.putString("name",foodIn.toString());
        nbtFood.putInt("hunger",foodIn.getHealing()*(int)multiplier);
        nbtFood.putFloat("saturation",foodIn.getSaturation()*multiplier);
        nbtIn.put("food",nbtFood);
        return nbtIn;
    }

    public static ItemStack generateFood(ItemStack itemStackIn,Food foodIn,Boolean isGodFood,float multiplier){
        itemStackIn.deserializeNBT(writeFoodData(foodIn,itemStackIn.serializeNBT(),multiplier));
        CompoundNBT nbt = (CompoundNBT) itemStackIn.serializeNBT().get("food");
        Food godFood = new Food.Builder()
                .hunger(nbt.getInt("hunger"))
                .saturation(nbt.getFloat("saturation"))
                .setAlwaysEdible()
                .fastToEat()
                .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.5f)
                .build();
        Food food = new Food.Builder()
                .hunger(nbt.getInt("hunger"))
                .saturation(nbt.getFloat("saturation"))
                .build();
        itemStackIn.setDisplayName(new StringTextComponent(nbt.getString("name")).applyTextStyle(TextFormatting.AQUA));
        ((IFoodable)(itemStackIn.getItem())).addFood((isGodFood)?godFood:food);
        return itemStackIn;
    }
}
