package com.bioast.addworms.entities.worm;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;

import java.util.function.Predicate;

public interface IWormProperty {
    /**
     * a Predicate that returns true when Block is one that worm lives on
     */
    Predicate<Block> getFloorBlock();

    EntityType<?> getEntityType();

    int getDieTime();

    int getDefaultBaseRange();

    boolean willDie();

    boolean doesDropWhenRemoved();

    boolean isHostile();
}
