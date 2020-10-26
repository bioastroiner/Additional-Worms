package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BasicWormEntity extends WormEntityBase {
    public BasicWormEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        setItemDrop(ModItems.WORM.get());
    }
}
