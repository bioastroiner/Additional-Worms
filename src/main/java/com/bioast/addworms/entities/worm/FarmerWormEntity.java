package com.bioast.addworms.entities.worm;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

/**
 * this is a very basic worm entity that will collect any plantable items
 * around it and plant them around itself in a {1 > 3 by 3 , 2 > 5 by 5 , 3...} area around it and occasionally
 * accelerate their growth by some magnitude especefic to its breed.
 * TODO : for future : also it can collect bonemills around it and apply instant effect on sorrunding crops
 * the farmer worm also tills and hydritates its sourronding dirts, it can't be placed
 * on non tillable (aka not dirt) blocks
 */
public class FarmerWormEntity extends AbstractWormEntity {

    public FarmerWormEntity(EntityType<FarmerWormEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn, null);
    }
}
