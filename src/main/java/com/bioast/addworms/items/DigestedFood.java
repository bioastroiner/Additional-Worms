package com.bioast.addworms.items;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.Debug;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class DigestedFood extends Item {

    ItemStack memicStack;
    public CompoundNBT nbt;

    public DigestedFood(Properties properties,ItemStack itemStackIn) {
        super(properties.food(itemStackIn.getItem().getFood()));
    }
    public DigestedFood(Properties properties){
        super(properties.food(ModItems.DIGESTED_FOOD_FOOD));
        memicStack = new ItemStack(null);
    }

    public void init(ItemStack stack){
        String name = stack.getDisplayName().getString();
        Food food = stack.getItem().getFood();
        nbt.putString("name",name);
        nbt.put("food",(INBT)food);
        String s = nbt.get("food").getType().toString();
        Debug.log(s);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack newMemicStack = memicStack.copy();
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        Food memicFood = newMemicStack.getItem().getFood();
        Food tempMemicFood = newMemicStack.getItem().getFood();;
        memicFood = new Food.Builder().hunger(tempMemicFood.getHealing() * 2).build();
        newMemicStack.setCount(itemstack.getCount());

        if (memicStack.isFood()) {
            if (playerIn.canEat(memicStack.getItem().getFood().canEatWhenFull())) {
                playerIn.setActiveHand(handIn);
                playerIn.setHeldItem(handIn,newMemicStack);
                return ActionResult.resultConsume(itemstack);
            } else {
                return ActionResult.resultFail(itemstack);
            }
        } else {
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        }
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        return this.isFood() ? entityLiving.onFoodEaten(worldIn, stack) : stack;
    }

    public ItemStack initilizeMemicStack(ItemStack itemstack){
        ItemStack newMemicStack = memicStack.copy();
        Food memicFood = newMemicStack.getItem().getFood();
        Food tempMemicFood = newMemicStack.getItem().getFood();;
        memicFood = new Food.Builder().hunger(tempMemicFood.getHealing() * 2).build();
        newMemicStack.setCount(itemstack.getCount());
        //Item item = new Item();
        return newMemicStack;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return memicStack.getBurnTime();
    }

    @Override
    public SoundEvent getEatSound() {
        return memicStack.getEatSound();
    }

    @Override
    public boolean isFood() {
        // probably not needed
        return memicStack.isFood();
    }
    @Override
    public boolean hasCustomProperties() {
        return true;
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        return super.updateItemStackNBT(nbt);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(nbt.getString("name")));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }


}
