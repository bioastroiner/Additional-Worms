package com.bioast.addworms.utils.intefaces;

import com.bioast.addworms.entities.WormEntityBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IItemWorm {
    default boolean addWormToWorld(World world, BlockPos pos, ItemStack stack, PlayerEntity player, WormEntityBase worm){
        if (!world.isRemote) {
            worm.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            worm.setCustomName(stack.getDisplayName());
            //LOGGER.log(Level.DEBUG,"i used It");
            world.addEntity(worm);
            if (!player.isCreative()) stack.shrink(1);
            return true;
        }
        return false;
    }
}
