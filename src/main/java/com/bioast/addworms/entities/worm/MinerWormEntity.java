package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        boolean inr = false;
        if (player.getHeldItem(hand).getItem() == Items.GLOWSTONE) {
            inr = true;
        }
        if (player.getHeldItem(hand).getItem() == Items.REDSTONE) {
            inr = true;
        }
        if (player.getHeldItem(hand).getItem() == Items.OBSIDIAN) {
            addItems(new ItemStack(Items.OBSIDIAN));
            inr = true;
        }
        if (player.getHeldItem(hand).getItem() == Items.PISTON) {
            addItems(new ItemStack(Items.PISTON));
            inr = true;
        }
        if (inr) {
            if (!player.isCreative())
                player.getHeldItem(hand).shrink(1);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (timer % getMiningRate() == 0) {
            mine();
        }
    }

    private int getMiningRate() {
        int rate = 10 - (int) getSpeed();
        if (doseVoid && rate > 1) --rate;
        if (rate < 1) rate = 1;
        return rate;
    }

    private int getMiningRange() {
        return getRange();
    }

    private int getVerRange() {
        ItemStack piss = new ItemStack(Items.PISTON, 0);
        simpleItemStorage.forEach(i -> {
            if (i.getItem() == Items.PISTON) {
                piss.grow(i.getCount());
            }
        });
        return -piss.getCount();
        // applying pistone increaces heaigh
        // applying obsidian will make it void worm
        // they all still start at the worm's itself heigh (change)
    }

    private void mine() {
        int size = getMiningRange();
        BlockPos.getAllInBox(
                new BlockPos(getPosition().getX() - size, getPosition().getY(), getPosition().getZ() - size),
                new BlockPos(getPosition().getX() + size, getPosition().getY() - getVerRange(), getPosition().getZ() + size))
                .filter(b -> !world.isAirBlock(b))
                .filter(b -> world.getBlockState(b).isSolid())
                .filter(b -> world.getBlockState(b).getBlockHardness(world, b) > 0)
                .filter(b -> !getPosition().equals(b.toImmutable()))
                .findAny()
                .ifPresent(b -> currentPos = b.toImmutable());
        //ParticleHelper.spawnParticles(world, getPosition(), 15, ParticleTypes.DUST);
        if (!world.isRemote) {
            world.destroyBlock(currentPos, !doseVoid);
        }
    }
}
