package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.DefaultFarmerBehavior;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.Random;

/**
 * this is a very basic worm entity that will collect any plantable items
 * around it and plant them around itself in a {1 > 3 by 3 , 2 > 5 by 5 , 3...} area around it and occasionally
 * accelerate their growth by some magnitude especefic to its breed.
 * TODO : for future : also it can collect bonemills around it and apply instant effect on sorrunding crops
 * the farmer worm also tills and hydrates its surrounding dirts, it can't be placed
 * on non tillable (aka not dirt) blocks
 */
public class FarmerWormEntity extends AbstractWormEntity {

    public FarmerWormEntity(EntityType<?> entityType, World worldIn, Item wormItem) {
        super(entityType, worldIn, null, wormItem, ((GeneralWormItem) wormItem).wormProperty);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer % (int) (50 / getTier().speed) == 0) {
            Map<BlockPos, BlockState> mapTillable = getBlockStatesAround(getRange(), 0,
                    block -> block.isIn(Tags.Blocks.DIRT) || block instanceof SnowyDirtBlock);
            mapTillable.forEach((p, b) -> DefaultFarmerBehavior.useHoeAt(world, p));
            Map<BlockPos, BlockState> mapFarmland = getBlockStatesAround(getRange(), 0,
                    block -> block instanceof FarmlandBlock);
            mapFarmland.forEach((p, b) -> {
                if (new Random().nextFloat() > 0.7f)
                    return;
                if (b.get(BlockStateProperties.MOISTURE_0_7) < 7)
                    world.setBlockState(p, b.with(BlockStateProperties.MOISTURE_0_7, 7));
            });
            Map<BlockPos, BlockState> mapPlantable = getBlockStatesAround(getRange(), 1,
                    block -> block instanceof IPlantable || block instanceof IGrowable);
            mapPlantable.forEach((p, b) -> {
                //Bonemill plants , Grow Cacti sugarCane , ...
                if (new Random().nextFloat() > 0.5f * getTier().speed) //Higher Speed lower Chance to skip this part
                    return;
            });
        }
    }
}
