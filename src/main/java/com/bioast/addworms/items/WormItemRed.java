//package com.bioast.addworms.items;
//
//import com.bioast.addworms.entities.WormEntityBase;
//import com.bioast.addworms.entities.WormEntityRed;
//import com.bioast.addworms.init.ModEntityTypes;
//import com.bioast.addworms.utils.interfaces.IItemWorm;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemUseContext;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.util.Hand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.StringTextComponent;
//import net.minecraft.world.World;
//
//import javax.annotation.Nullable;
//import java.util.List;
///**
// * TODO: this code is bound to be removed
// */
//public class WormItemRed extends WormItemBase implements IItemWorm {
//    public WormItemRed(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
//        if (checkForWormsInArea(world,pos)) {
//            return addWormToWorld(world,pos,stack,player,new WormEntityRed(ModEntityTypes.WORM_ENTITY_RED.get(),
//            world));
//        }
//        return false;
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag
//    flagIn) {
//        tooltip.add(new StringTextComponent("red"));
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//    }
//
//    @Override
//    public ActionResultType onItemUse(ItemUseContext context) {
//
//        PlayerEntity player = context.getPlayer();
//        Hand hand = context.getHand();
//        BlockPos pos = context.getPos();
//        World world = context.getWorld();
//
//        ItemStack stack = player.getHeldItem(hand);
//        BlockState state = world.getBlockState(pos);
//        if (WormEntityBase.canWormify(world, pos, state,state == Blocks.STONE.getDefaultState())) {
//            if (placeWorm(player, pos, world, stack)) return ActionResultType.SUCCESS;
//        }
//        return ActionResultType.FAIL;
//    }
//
//
//}
