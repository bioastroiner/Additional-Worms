package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.ParticleHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MinerWormEntity extends AbstractWormEntity {

    int ver = 1;
    BlockPos currentPos;
    int rate = 20;
    boolean doseVoid = false;

    public MinerWormEntity(EntityType<?> entityType, World worldIn, Item wormItem) {
        super(entityType, worldIn, null, wormItem, ((GeneralWormItem) wormItem).wormProperty);
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer % getMiningRate() == 0) {
            mine();
        }
    }

    private int getMiningRate() {
        int rate = 20 - (int) getSpeed();
        if (doseVoid && rate > 1) --rate;
        return rate;
    }

    private int getMiningRange() {
        return getRange();
    }

    private int getVerRange() {
        return -5;
        // applying pistone increaces heaigh
        // applying st-pistone decreaces heigh
        // applying obsidian will make it void worm
        // they all still start at the worm's itself heigh (change)
    }

    private void mine() {
        int size = getMiningRange();
        BlockPos.getAllInBox(
                new BlockPos(getPosition().getX() - size, getPosition().getY(), getPosition().getZ() - size),
                new BlockPos(getPosition().getX() + size, getPosition().getY(), getPosition().getZ() + size))
                .filter(b -> !world.isAirBlock(b))
                .filter(b -> world.getBlockState(b).isSolid())
                .filter(b -> world.getBlockState(b).getBlockHardness(world, b) > 0)
                .filter(b -> !getPosition().equals(b.toImmutable()))
                .findAny()
                .ifPresent(b -> currentPos = b.toImmutable());
        if (!world.isRemote) {
            world.destroyBlock(currentPos, !doseVoid);
            ParticleHelper.spawnParticles(world, getPosition(), 15, ParticleTypes.DUST);
        }
    }
}
