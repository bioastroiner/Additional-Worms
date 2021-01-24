package com.bioast.addworms.entities.worm;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface IWormProperty {
    /**
     * a Predicate that returns true when Block is one that worm lives on
     */
    Predicate<Block> getFloorBlock();

    /**
     * Function designed when
     * Item has been registered for the worm
     * must include the right method to
     * instantiate an instance of the worm Ent
     * FIXME: what a stupid move, EntityType#create existed and i was idiot, **REMOVE** this
     */
    BiFunction<World, Item, ? extends AbstractWormEntity> getEntity(Item wormItem);

    EntityType<?> getEntityType();

    int getDieTime();

    int getDefaultBaseRange();

    boolean willDie();

    boolean doesDropWhenRemoved();

    boolean isHostile();
}
