package com.bioast.addworms.utils.interfaces;

import net.minecraft.item.ItemStack;

public interface IWorm {

    void tick();

    void setDead();

    ItemStack getItemDrop();
}
