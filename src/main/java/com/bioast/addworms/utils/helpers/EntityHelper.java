package com.bioast.addworms.utils.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper {

    public static boolean dropItem(BlockPos posIn, ItemStack itemStackIn, World worldIn){
        if(itemStackIn == null || posIn.getY() < -10) throw new IllegalArgumentException("drop Item argument was missing!");
        if(!itemStackIn.isEmpty()){
            worldIn.addEntity(new ItemEntity(worldIn,posIn.getX(),posIn.getY() + 1,posIn.getZ(),itemStackIn));
            return true;
        }
        else return false;
    }

    public static List<Entity> getEntitiesInArea(BlockPos posStart,BlockPos posEnd,World world){
        List<Entity> entities = new ArrayList<>();
        AxisAlignedBB alignedBB = new AxisAlignedBB(posStart,posEnd);
        entities = world.getEntitiesWithinAABB(ItemEntity.class,alignedBB);
        return entities;
    }

    /**
     * @param posStart
     * @param posEnd
     * @param world
     * @return a list of Items Dropped in an Squear Blocky Area
     */
    public static List<ItemEntity> getItemEntitiesInArea(BlockPos posStart,BlockPos posEnd,World world){
        List<ItemEntity> items = new ArrayList<>();
        for(Entity e : getEntitiesInArea(posStart,posEnd,world)){
            if(e instanceof ItemEntity) items.add((ItemEntity) e);
        }
        return items;
    }
}
