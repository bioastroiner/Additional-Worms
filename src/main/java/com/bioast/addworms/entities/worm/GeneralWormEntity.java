package com.bioast.addworms.entities.worm;

import com.bioast.addworms.init.ModEntityTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GeneralWormEntity extends AbstractWormEntity {
    public GeneralWormEntity(World worldIn, @Nullable NonNullList<ItemStack> dropList) {
        super(ModEntityTypes.WORM_ENTITY.get(), worldIn, dropList);
    }
}
