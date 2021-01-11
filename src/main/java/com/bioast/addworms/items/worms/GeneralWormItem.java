package com.bioast.addworms.items.worms;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import com.bioast.addworms.items.ModItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

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
 * data to change it in realtime, the wormRenderer classes are quite Abstract,
 * // TODO may use Baked models in future
 */
public class GeneralWormItem extends ModItem {

    /**
     * Function designed when
     * Item has been registered for the worm
     * must include the right method to
     * instantiate an instance of the worm Ent
     */
    protected Function<World, ? extends AbstractWormEntity> instFunc;

    public GeneralWormItem(
            Properties properties,
            Function<World, ? extends AbstractWormEntity> func
    ) {
        super(properties);
        instFunc = func;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos wormPos = context.getPos()
                .add(
                        0.5,
                        0.5,
                        0.5
                );
        addWormToWorld(
                context.getWorld(),
                context.getPos(),
                context.getItem(),
                context.getPlayer(),
                instantiate(context.getWorld())
        );
        return super.onItemUse(context);
    }

    /**
     * add a wormEntitiy in given pos
     *
     * @return false in client always
     */
    private boolean addWormToWorld(
            World world,
            BlockPos pos,
            ItemStack stack,
            PlayerEntity player,
            AbstractWormEntity worm
    ) {
        if (!world.isRemote) {
            //FIXME
            //check if our worm can live there, remove it if can't
            if (!worm.getPlacingCriteria()) {
                worm.remove(false);
                return false;
            }
            //add the entity to world(it should be instantated in the method call)
            worm.setPosition(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5
            );
            worm.setCustomName(
                    stack.getDisplayName()
            );
            world.addEntity(worm);
            //set the drop of entity to its item
            ItemStack dropItemStack =
                    stack.copy();
            dropItemStack.setCount(1);
            worm.addItems(
                    dropItemStack
            );
            //use worm in your hand if not creative
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        //do nothing on client
        return false;
    }

    /**
     * @return calls the Function declared in Item registry to instantiate a desired WormEntity of its kind
     * at the right moment
     */
    protected AbstractWormEntity instantiate(World worldIn) {
        return instFunc.apply(worldIn);
    }
}
