package com.bioast.addworms.items.worms;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import com.bioast.addworms.entities.worm.IWormProperty;
import com.bioast.addworms.items.ModItem;
import com.bioast.addworms.utils.helpers.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * almost all worms should use this class
 * and don't need to have their own class for items
 * <p>
 * for entities however it's nicer for them to have different Class for each
 * later for tiering they can share the same class but their NBT may differ
 * in those situations we would try different EntityTypes(same class) or just use
 * bare-bone NBT difference(same class-same type).
 * <p>
 * currently our renderer is bound mainly by the class, tough we can technically use NBT
 * data to change it in realtime, the wormRenderer classes are quite Abstract
 */
public class GeneralWormItem extends ModItem {

    //FIXME: the incantation here is veeery wrong fix it

    public final IWormProperty wormProperty;

    public GeneralWormItem(Item.Properties properties, IWormProperty wormProperty) {
        super(properties);
        this.wormProperty = wormProperty;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos wormPos = context.getPos().add(0.5, 0.5, 0.5);
        addWormToWorld(context.getWorld(), wormPos, context.getItem(), context.getPlayer(),
                () -> (AbstractWormEntity) wormProperty.getEntityType().create(context.getWorld()));
        return super.onItemUse(context);
    }

    /**
     * adds a wormEntity in given pos
     *
     * @return false if {@link IWormProperty#getFloorBlock()} Predicate is false
     */
    private boolean addWormToWorld(World world, BlockPos pos, ItemStack stack, PlayerEntity player,
                                   Supplier<AbstractWormEntity> wormS) {
        if (!world.isRemote) {
            //check if our worm can live there
            if (!wormProperty.getFloorBlock().test(world.getBlockState(pos).getBlock()) ||
                    !world.isAirBlock(pos.up()) ||
                    world.getEntitiesWithinAABB(AbstractWormEntity.class, MathHelper.getAxisAlignedBB(1, pos), null).size() > 0) {
                return false;
            }
            //add the entity to world, we use a supplier to prevent it from instantiating too soon
            AbstractWormEntity worm = wormS.get();
            worm.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            worm.setCustomName(stack.getDisplayName());

            //FIXME i bet this is not always true
            if (stack.getItem() instanceof GeneralWormItem) {
                Item wormItem = stack.getItem();
            }

            world.addEntity(worm);
            //use worm in your hand if not creative
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        //do nothing on client
        return false;
    }

//    /**
//     * @return calls the Function declared in {@link IWormProperty#getEntity()} to instantiate a desired
//     * WormEntity of its kind
//     * at the right moment
//     * FIXME remove this method
//     */
//    private AbstractWormEntity instantiate(World worldIn) {
//        return wormProperty.getEntity().apply(worldIn);
//    }

    public static class Properties {
        //private BiFunction<World, Item, ? extends AbstractWormEntity> createWormFunction;
        RegistryObject<EntityType<Entity>> entityType;
        private Predicate<Block> floorBlocks;
        private int dieTime = 1000;
        private int defaultBaseRange = 1;
        private boolean canDie = true;
        private boolean dropWhenRemoved = true;
        private boolean isHostile = false;

        /**
         * sets a proper method to spawn a new worm Entity
         */
        public Properties setEntity(RegistryObject<EntityType<Entity>> entityTypeIn) {
            entityType = entityTypeIn;
            return this;
        }

        public Properties setFloorBlocks(Predicate<Block> floorBlocks) {
            this.floorBlocks = floorBlocks;
            return this;
        }

        public Properties setDefaultDyingTime(int ticksIn) {
            dieTime = ticksIn;
            return this;
        }

        public Properties canDie(boolean canDie) {
            this.canDie = canDie;
            return this;
        }

        public Properties isHostile(boolean isHostile) {
            this.isHostile = isHostile;
            return this;
        }

        public Properties dropWhenRemoved(boolean dropWhenRemoved) {
            this.dropWhenRemoved = dropWhenRemoved;
            return this;
        }

        public Properties setDefaultBaseRange(int r) {
            this.defaultBaseRange = r;
            return this;
        }

        public IWormProperty finalizeProperty() {
            return new IWormProperty() {
                @Override
                public Predicate<Block> getFloorBlock() {
                    return floorBlocks;
                }

                @Override
                public BiFunction<World, Item, ? extends AbstractWormEntity> getEntity(Item wormItem) {
                    return null;
                }

                @Override
                public EntityType<?> getEntityType() {
                    return entityType.get();
                }

                @Override
                public int getDieTime() {
                    return dieTime;
                }

                @Override
                public int getDefaultBaseRange() {
                    return defaultBaseRange;
                }

                @Override
                public boolean willDie() {
                    return canDie;
                }

                @Override
                public boolean doesDropWhenRemoved() {
                    return dropWhenRemoved;
                }

                @Override
                public boolean isHostile() {
                    return isHostile;
                }
            };
        }
    }
}
