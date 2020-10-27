package com.bioast.addworms.utils.intefaces;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWorm {

    public void tick();

    public void setDead();

    public ItemStack getItemDrop();
}
