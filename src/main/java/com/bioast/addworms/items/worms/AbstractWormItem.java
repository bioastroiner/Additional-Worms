package com.bioast.addworms.items.worms;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import com.bioast.addworms.items.ModItem;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class AbstractWormItem<W extends AbstractWormEntity> extends ModItem {

    protected Function instFunc = null;

    public AbstractWormItem(Properties properties, Function<World,?
                extends AbstractWormEntity> func) {
        super(properties);
        instFunc = func;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos wormPos = context.getPos().add(0.5,0.5,0.5);
        addWormToWorld(context.getWorld(),context.getPos(),context.getItem(),context.getPlayer(),
                instantiate(context.getWorld()));
        return super.onItemUse(context);
    }

    /**
     * add a wormEntitiy in given pos
     * @return false if the given itemstack is not shrinken or wormEntities fails to place
     * @return false in client always
     */
    private boolean addWormToWorld(World world, BlockPos pos, ItemStack stack, PlayerEntity player,
                               AbstractWormEntity worm){
        if (!world.isRemote) {
            //add the entity to world(it should be instantated in the method call)
            worm.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            worm.setCustomName(stack.getDisplayName());
            world.addEntity(worm);
            //set the drop of entity to its item
            ItemStack dropItemStack = stack.copy();
            dropItemStack.setCount(1);
            worm.addItems(dropItemStack);
            //check if our worm can live there, remove it if can't
            if(!worm.getPlacingCriteria()){
                worm.remove(false);
                return false;
            }
            //use worm in your hand if not creative
            if (!player.isCreative()) stack.shrink(1);
            return true;
        }
        //do nothing on client
        return false;
    }

    /**
     * @return calls the Function declared in Item registry to instansiate a desired WormEntity of its kind
     * at the right moment
     */
    protected W instantiate(World worldIn){
        return (W) instFunc.apply(worldIn);
    }
}
