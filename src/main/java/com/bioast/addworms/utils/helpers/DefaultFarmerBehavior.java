package com.bioast.addworms.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

import static net.minecraft.block.Blocks.COARSE_DIRT;
import static net.minecraft.block.Blocks.DIRT;
import static net.minecraftforge.event.ForgeEventFactory.onHoeUse;
import static net.minecraftforge.event.ForgeEventFactory.onItemUseTick;

public final class DefaultFarmerBehavior implements IFarmerBehavior {

    private static ItemStack hoe = ItemStack.EMPTY;

    private static ItemStack getHoeStack() {
        if (hoe.isEmpty())
            hoe = new ItemStack(Items.DIAMOND_HOE);
        return hoe;
    }

    public static ActionResultType useHoeAt(World world, BlockPos pos) {
        assert world.isRemote;
        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);
        ItemStack itemstack = getHoeStack();
        if (!player.canPlayerEdit(pos.offset(Direction.UP),
                Direction.UP, itemstack)) {
            return ActionResultType.FAIL;
        } else {
            //int hook = onHoeUse(itemstack, player, world, pos);
            int hook = onHoeUse(new ItemUseContext(player, Hand.MAIN_HAND,
                    new BlockRayTraceResult(Vector3d.ZERO, Direction.UP, pos, false)));
            if (hook != 0)
                return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
            Block block = world.getBlockState(pos).getBlock();
            if (world.isAirBlock(pos.up())) {
                if (block instanceof GrassBlock ||
                        block == Blocks.GRASS_PATH) {
                    Debug.log("Hey I tilled Grass do you see something?");//FIXME
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return ActionResultType.SUCCESS;
                }
                if (block == DIRT) {
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return ActionResultType.SUCCESS;
                } else if (block == COARSE_DIRT) {
                    world.setBlockState(pos, DIRT.getDefaultState());
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.PASS;
        }
    }

    public static ActionResultType useBonemeal(World world, BlockPos pos) {
        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);
        ItemStack itemstack = new ItemStack(Items.BONE_MEAL, 64);
        if (!player.canPlayerEdit(pos.offset(Direction.UP), Direction.UP, itemstack)) {
            return ActionResultType.FAIL;
        } else {
            int hook = onItemUseTick(player, itemstack, 10);
            if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
            Block plantBlock = world.getBlockState(pos).getBlock();
            if (world.isAirBlock(pos.up())) {
                if (world.getBlockState(pos).getBlock() instanceof IGrowable) {
                    Block plant = world.getBlockState(pos).getBlock();
                    ((IGrowable) plant).grow((ServerWorld) world, world.rand,
                            pos, plant.getDefaultState());
                    BoneMealItem.applyBonemeal(itemstack, world, pos, player);
                    BoneMealItem.spawnBonemealParticles(world, pos, 1);
                }
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos,
                                     IFarmer farmer) {
        return null;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
