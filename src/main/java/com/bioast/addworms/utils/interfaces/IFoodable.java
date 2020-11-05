package com.bioast.addworms.utils.interfaces;

import net.minecraft.item.Food;

// NOTE: this interface is effected by accesstransformer witch let it manipulate Food in an Item
public interface IFoodable {
    public void addFood(Food food);
}
