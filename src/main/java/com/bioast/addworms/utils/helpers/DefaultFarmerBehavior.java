package com.bioast.addworms.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

import static net.minecraft.block.Blocks.COARSE_DIRT;
import static net.minecraft.block.Blocks.DIRT;
import static net.minecraftforge.event.ForgeEventFactory.onHoeUse;
import static net.minecraftforge.event.ForgeEventFactory.onItemUseTick;

public class DefaultFarmerBehavior implements IFarmerBehavior {

//    public static boolean defaultPlant(World world, BlockPos pos, BlockState toPlant, IFarmer farmer, int use) {
//        if (toPlant != null) {
//            BlockPos farmland = pos.down();
//            Block farmlandBlock = world.getBlockState(farmland).getBlock();
//            if (farmlandBlock instanceof IGrowable || farmlandBlock instanceof GrassBlock) {
//                world.setBlockState(pos, Blocks.AIR.getDefaultState());
//                useHoeAt(world, farmland);
//                world.playSound(null, farmland, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                //farmer.extractEnergy(use);
//            }
//
//            if (tryPlant(toPlant, world, pos)) {
//                //farmer.extractEnergy(use);
//                return true;
//            }
//        }
//        return false;
//    }

//    private static boolean tryPlant(BlockState toPlant, World world, BlockPos pos) {
//        if (toPlant.getBlock().canPlaceBlockAt(world, pos)) {
//            world.setBlockState(pos, toPlant);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
//        int use = 350;
//        if (farmer.getEnergy() >= use * 2) {
//            if (defaultPlant(world, pos, this.getPlantablePlantFromStack(seed, world, pos), farmer, use)) return FarmerResult.SUCCESS;
//        }
//        return FarmerResult.FAIL;
//    }

//    @Override
//    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
//        int use = 250;
//        if (farmer.getEnergy() >= use) {
//            IBlockState state = world.getBlockState(pos);
//            Block block = state.getBlock();
//
//            if (block instanceof BlockCrops) {
//                if (((BlockCrops) block).isMaxAge(state)) { return this.doFarmerStuff(state, world, pos, farmer); }
//            } else if (BlockCrops.AGE.equals(block.getBlockState().getProperty("age"))) {
//                if (state.getValue(BlockCrops.AGE) >= 7 && !(block instanceof BlockStem)) return this.doFarmerStuff(state, world, pos, farmer);
//            }
//        }
//        return FarmerResult.FAIL;
//    }

//    private FarmerResult doFarmerStuff(IBlockState state, World world, BlockPos pos, IFarmer farmer) {
//        List<ItemStack> seeds = new ArrayList<>();
//        List<ItemStack> other = new ArrayList<>();
//        NonNullList<ItemStack> drops = NonNullList.create();
//        state.getBlock().getDrops(drops, world, pos, state, 0);
//        for (ItemStack stack : drops) {
//            if (this.getPlantableFromStack(stack) != null) {
//                seeds.add(stack);
//            } else {
//                other.add(stack);
//            }
//        }
//
//        boolean addSeeds = true;
//        if (!farmer.canAddToSeeds(seeds)) {
//            other.addAll(seeds);
//            addSeeds = false;
//        }
//
//        if (farmer.canAddToOutput(other)) {
//            farmer.addToOutput(other);
//
//            if (addSeeds) {
//                farmer.addToSeeds(seeds);
//            }
//
//            world.playEvent(2001, pos, Block.getStateId(state));
//            world.setBlockToAir(pos);
//
//            farmer.extractEnergy(250);
//            return FarmerResult.SUCCESS;
//        }
//        return FarmerResult.FAIL;
//    }

//    @Override
//    public int getPriority() {
//        return 0;
//    }
//
//    private BlockState getPlantablePlantFromStack(ItemStack stack, World world, BlockPos pos) {
//        if (StackUtil.isValid(stack)) {
//            IPlantable plantable = this.getPlantableFromStack(stack);
//            if (plantable != null) {
//                BlockState state = plantable.getPlant(world, pos);
//                if (state != null && state.getBlock() instanceof IGrowable) return state;
//            }
//        }
//        return null;
//    }
//
//    private IPlantable getPlantableFromStack(ItemStack stack) {
//        Item item = stack.getItem();
//        if (item instanceof IPlantable) {
//            return (IPlantable) item;
//        } else if (item instanceof BlockItem) {
//            Block block = Block.getBlockFromItem(item);
//            if (block instanceof IPlantable) return (IPlantable) block;
//        }
//        return null;
//    }

    private static ItemStack hoe = ItemStack.EMPTY;

    private static ItemStack getHoeStack() {
        if (hoe.isEmpty()) hoe = new ItemStack(Items.DIAMOND_HOE);
        return hoe;
    }

    public static ActionResultType useHoeAt(World world, BlockPos pos) {

        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);
        ItemStack itemstack = getHoeStack();

        if (!player.canPlayerEdit(pos.offset(Direction.UP), Direction.UP, itemstack)) {
            return ActionResultType.FAIL;
        } else {
            //int hook = onHoeUse(itemstack, player, world, pos);
            int hook = onHoeUse(new ItemUseContext(player, Hand.MAIN_HAND, new BlockRayTraceResult(Vec3d.ZERO, Direction.UP,pos,false)));
            if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;

            BlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return ActionResultType.SUCCESS;
                }
                if(block == DIRT){
                    world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                    return ActionResultType.SUCCESS;
                } else if(block == COARSE_DIRT){
                    world.setBlockState(pos, DIRT.getDefaultState());
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.PASS;
        }
    }

    public static ActionResultType useBonemeal(World world, BlockPos pos){
        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);
        ItemStack itemstack = new ItemStack(Items.BONE_MEAL,1000);

        if(!player.canPlayerEdit(pos.offset(Direction.UP), Direction.UP,itemstack)){
            return ActionResultType.FAIL;
        } else {
            int hook = onItemUseTick(player,itemstack,40);
            if(hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;

            BlockState plantState = world.getBlockState(pos);
            Block plantBlock = plantState.getBlock();

            if(world.isAirBlock(pos.up())){
                if(world.getBlockState(pos).getBlock() instanceof IGrowable){
                    Block plant = world.getBlockState(pos).getBlock();
                    ((IGrowable)plant).grow((ServerWorld)world,world.rand,pos,plant.getDefaultState());
                    BoneMealItem.applyBonemeal(itemstack,world,pos,player);
                    BoneMealItem.spawnBonemealParticles(world,pos, 1);
                }
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
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
