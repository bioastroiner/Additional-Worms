package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class MinerWormEntity extends AbstractWormEntity {

    public MinerWormEntity(EntityType<?> entityType, World worldIn, Item wormItem) {
        super(entityType, worldIn, null, wormItem, ((GeneralWormItem) wormItem).wormProperty);
    }

    @Override
    public void tick() {
        super.tick();
        breakBlocksAround(getRange(), 1, 10, ToolType.PICKAXE, null);
    }
}
