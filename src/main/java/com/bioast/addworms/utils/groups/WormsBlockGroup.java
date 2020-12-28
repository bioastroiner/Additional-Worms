package com.bioast.addworms.utils.groups;

import com.bioast.addworms.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WormsBlockGroup extends ItemGroup {


    public WormsBlockGroup(int index, String label) {
        super(index, label);
    }

    public static final WormsBlockGroup instance = new WormsBlockGroup(ItemGroup.GROUPS.length,"WormsBlockTab");

    public static final WormsBlockGroup getInstance() {
        return instance;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.WORM.get());
    }
}
