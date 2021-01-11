package com.bioast.addworms.items;

import com.bioast.addworms.entities.WormEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * TODO: this code is bound to be removed
 */
public abstract class WormItemBase extends ModItem {

    boolean wormsEnabled = true;

    public WormItemBase(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        ItemStack stack = player.getHeldItem(hand);
        BlockState state = world.getBlockState(pos);
        if (WormEntityBase.canWormify(world, pos, state)) {
            if (placeWorm(player, pos, world, stack)) return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        return false;
    }

    protected boolean checkForWormsInArea(World world, BlockPos pos, AxisAlignedBB axisAlignedBB) {
        List<WormEntityBase> worms = world.getEntitiesWithinAABB(WormEntityBase.class, axisAlignedBB);
        return worms == null || worms.isEmpty();
    }

    protected boolean checkForWormsInArea(World world, BlockPos pos) {
        List<WormEntityBase> worms = world.getEntitiesWithinAABB(WormEntityBase.class,
                new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1,
                        pos.getZ() + 2));
        return worms.isEmpty();
    }

    protected boolean checkForWormsInArea(World world, BlockPos pos, int oddnumberIn) {
        if (oddnumberIn % 2 == 0) throw new IllegalArgumentException("Insert an odd number please!");
        int oddInput = oddnumberIn;
        oddInput -= 3;
        List<WormEntityBase> worms = world.getEntitiesWithinAABB(WormEntityBase.class,
                new AxisAlignedBB(pos.getX() - 1 + oddInput, pos.getY(), pos.getZ() - 1 + oddInput,
                        pos.getX() + 2 + oddInput, pos.getY() + 1, pos.getZ() + 2 + oddInput));
        return worms == null || worms.isEmpty();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn) {
        //tooltip.add(new StringTextComponent("place it on the ground"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
