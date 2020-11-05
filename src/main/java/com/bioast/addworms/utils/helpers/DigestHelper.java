package com.bioast.addworms.utils.helpers;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.items.DigestedFood;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class DigestHelper {
    // okay this method would take an ItemStack witch is a Food and Digest it and make a Diegsted variant of Et so....

    /**
     * this method return an ItemStack witch is a Digested Variant of the FoodIn "very HandyDandy?!
     *
     * @param FoodIn
     * @return
     */
//    public static ItemStack DigestFood(Food FoodIn,float multiplier){
//        ItemStack stackDigested = new ItemStack(ModItems.DIGESTED_FOOD.get());
//        return FoodHelper.generateFood(stackDigested,FoodIn,true,multiplier);
//    }
//    public static ItemStack makeItFood(ItemStack currentStack, ItemStack FoodStack){
//        if(currentStack.serializeNBT().contains("isdigested")){
//
//        }
//        CompoundNBT nbt = new CompoundNBT();
//        nbt.putString("name",FoodStack.getDisplayName().getString());
//        nbt.putInt("hunger",FoodStack.getItem().getFood().getHealing() * 2);
//        nbt.putFloat("saturation",FoodStack.getItem().getFood().getSaturation() * 2);
//        nbt.putBoolean("isdigested",true);
//        nbt = currentStack.serializeNBT().merge(nbt);
//        currentStack.deserializeNBT(nbt);
//        Debug.logNBT(nbt);
//        if(currentStack.serializeNBT().contains("isdigested")){
//            Debug.log("it has it",true);
//            if(FoodStack.isFood()){
//                CompoundNBT nbt1 = currentStack.serializeNBT();
//                Food copiedFood = new Food.Builder()
//                        .hunger(nbt.getInt("hunger"))
//                        .saturation(nbt.getFloat("saturation"))
//                        .setAlwaysEdible()
//                        .fastToEat()
//                        .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.5f)
//                        .build();
//                if(currentStack.getItem() instanceof DigestedFood){
//                    ((DigestedFood)currentStack.getItem()).addFood(copiedFood);
//                }
//                currentStack.setDisplayName(new StringTextComponent(currentStack.serializeNBT().getString("name")).applyTextStyle(TextFormatting.GREEN));
//            }
//        }
//    }
}
