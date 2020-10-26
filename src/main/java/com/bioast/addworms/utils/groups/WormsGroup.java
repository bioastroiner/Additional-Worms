package com.bioast.addworms.utils.groups;

import com.bioast.addworms.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WormsGroup extends ItemGroup {

    public WormsGroup(int index, String label) {
        super(index, label);
    }

    public static final WormsGroup getInstance() {
        return new WormsGroup(ItemGroup.GROUPS.length,"WormsTab");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.WORM.get());
    }
}
