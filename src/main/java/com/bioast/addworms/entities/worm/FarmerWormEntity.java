package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.Debug;
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
        Debug.log(String.valueOf(getLevel()));
        if (timer % (int) (50 / getSpeed()) == 0 && !world.isRemote) {
            Map<BlockPos, BlockState> mapTillable = getBlockStatesAround(getRange(), 0,
                    block -> block.isIn(Tags.Blocks.DIRT) || block instanceof SnowyDirtBlock);
            for (Map.Entry<BlockPos, BlockState> entry : mapTillable.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState blockState = entry.getValue();
                DefaultFarmerBehavior.useHoeAt(world, pos);
            }
            Map<BlockPos, BlockState> mapFarmland = getBlockStatesAround(getRange(), 0,
                    block -> block instanceof FarmlandBlock);
            for (Map.Entry<BlockPos, BlockState> entry : mapFarmland.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState blockState = entry.getValue();
                if (new Random().nextFloat() > 0.1f * getSpeed())
                    continue;
                if (blockState.get(BlockStateProperties.MOISTURE_0_7) < 7)
                    world.setBlockState(pos, blockState.with(BlockStateProperties.MOISTURE_0_7, 7));
            }
            Map<BlockPos, BlockState> mapPlantable = getBlockStatesAround(getRange(), 1,
                    block -> block instanceof IPlantable || block instanceof IGrowable);
            for (Map.Entry<BlockPos, BlockState> entry : mapPlantable.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState blockState = entry.getValue();
                if (new Random().nextFloat() > 0.1f * getSpeed()) //Higher Speed lower Chance to skip this part
                    continue;
                //Bonemill plants , Grow Cacti sugarCane , ...
            }
        }
    }
}
