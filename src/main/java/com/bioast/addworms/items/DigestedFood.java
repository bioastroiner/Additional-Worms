package com.bioast.addworms.items;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.interfaces.IFoodable;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class DigestedFood extends Item implements IFoodable {

    public DigestedFood(Properties properties){
        super(properties);
    }

    public void addFood(Food food){
        this.food = food;
    }

    public void setName(ITextComponent text){
        textComponent = text;
    }
    private ITextComponent textComponent;

    public void makeItFood(ItemStack currentStack,ItemStack FoodStack){
        if(currentStack.serializeNBT().contains("isdigested")){

        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name",FoodStack.getDisplayName().getString());
        nbt.putInt("hunger",FoodStack.getItem().getFood().getHealing() * 2);
        nbt.putFloat("saturation",FoodStack.getItem().getFood().getSaturation() * 2);
        nbt.putBoolean("isdigested",true);
        nbt = currentStack.serializeNBT().merge(nbt);
        currentStack.deserializeNBT(nbt);
        Debug.logNBT(nbt);
        if(currentStack.serializeNBT().contains("isdigested")){
            Debug.log("it has it",true);
            if(FoodStack.isFood()){
                CompoundNBT nbt1 = currentStack.serializeNBT();
                Food copiedFood = new Food.Builder()
                        .hunger(nbt.getInt("hunger"))
                        .saturation(nbt.getFloat("saturation"))
                        .setAlwaysEdible()
                        .fastToEat()
                        .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.5f)
                        .build();
                if(currentStack.getItem() instanceof DigestedFood){
                    ((DigestedFood)currentStack.getItem()).addFood(copiedFood);
                }
                currentStack.setDisplayName(new StringTextComponent(currentStack.serializeNBT().getString("name")).applyTextStyle(TextFormatting.GREEN));
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        //makeItFood(playerIn.getHeldItem(handIn).getStack(),new ItemStack(Items.APPLE));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        return super.updateItemStackNBT(nbt);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        //makeItFood(stack);
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        stack.setDisplayName(new StringTextComponent(stack.serializeNBT().getString("name")).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new StringTextComponent("do you wanna eat this?!").applyTextStyle(TextFormatting.BLUE));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
