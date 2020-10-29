package com.bioast.addworms.entities;

import com.bioast.addworms.items.WormItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WormItemDigester extends WormItemBase {

    public WormItemDigester(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        // looks like have to wait
        tooltip.add(new StringTextComponent("digester"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player.world.isRemote) return;
        NonNullList<ItemStack> Inv = ((PlayerEntity)player).inventory.mainInventory;
        for (ItemStack itemStack : Inv){
            if(itemStack.getItem().isFood()){
                DigestFood(itemStack);
            }
        }
    }
    ItemStack DigestFood(ItemStack foodIn){
        // do stuff with it here later
        return ItemStack.EMPTY;
    }
}
