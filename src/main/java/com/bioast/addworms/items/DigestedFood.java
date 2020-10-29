package com.bioast.addworms.items;

import com.bioast.addworms.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
        memicStack = itemStackIn;
        if(itemStackIn.isEmpty() || itemStackIn == null){
            memicStack = new ItemStack(this);
        }
        nbt.putString("name",memicStack.getTranslationKey());
    }
    public DigestedFood(Properties properties){
        super(properties.food(ModItems.DIGESTED_FOOD_FOOD));
        memicStack = new ItemStack(this.getItem());
        nbt.putString("name",memicStack.getTranslationKey());
    }

//    @Override
//    public ITextComponent getDisplayName(ItemStack stack) {
//        return memicStack.getDisplayName();
//    }

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

//    @Override
//    public String getTranslationKey(ItemStack stack) {
//        return memicStack.getTranslationKey();
//    }

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
