package com.bioast.addworms.utils.interfaces;

import net.minecraft.item.ItemStack;

public interface IWorm {

    public void tick();

    public void setDead();

    public ItemStack getItemDrop();
}
