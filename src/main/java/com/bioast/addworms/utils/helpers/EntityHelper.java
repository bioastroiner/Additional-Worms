package com.bioast.addworms.utils.helpers;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityHelper {

    public static boolean dropItem(BlockPos posIn, ItemStack itemStackIn, World worldIn){
        if(itemStackIn == null || posIn.getY() < -10) throw new IllegalArgumentException("drop Item argument was missing!");
        if(!itemStackIn.isEmpty()){
            worldIn.addEntity(new ItemEntity(worldIn,posIn.getX(),posIn.getY() + 1,posIn.getZ(),itemStackIn));
            return true;
        }
        else return false;
    }
}
