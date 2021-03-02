//package com.bioast.addworms.entities;
//
//import com.bioast.addworms.init.ModItems;
//import com.bioast.addworms.utils.helpers.DefaultFarmerBehavior;
//import com.bioast.addworms.utils.helpers.EntityHelper;
//import net.minecraft.block.*;
//import net.minecraft.entity.EntityType;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.server.ServerWorld;
//import net.minecraftforge.common.IPlantable;
//
///**
// * TODO: this code is bound to be moved
// */
//public class WormEntityFast extends WormEntityBase {
//    ItemStack dropStack = new ItemStack(ModItems.WORM_FAST.get(), 1);
//
//    public WormEntityFast(EntityType<?> entityTypeIn, World worldIn) {
//        super(entityTypeIn, worldIn);
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        if (!world.isRemote) {
//            if (this.timer % 50 == 0) {
//                for (int x = -1; x <= 1; x++) {
//                    for (int z = -1; z <= 1; z++) {
//                        BlockPos pos = new BlockPos(this.getPosX() + x, this.getPosY(), this.getPosZ() + z);
//                        BlockState state = this.world.getBlockState(pos);
//                        Block block = state.getBlock();
//                        boolean isMiddlePose = x == 0 && z == 0;
//
//                        if (canWormify(this.world, pos, state)) {
//                            boolean isFarmland = block instanceof FarmlandBlock;
//                            if (!isFarmland || state.get(BlockStateProperties.MOISTURE_0_7).intValue() < 7 || state
//                            .getBlock() instanceof GrassBlock || state.getBlock() == Blocks.GRASS_BLOCK) {
//                                if (isMiddlePose || this.world.rand.nextFloat() >= 0.25F) {
//                                    if (!isFarmland) DefaultFarmerBehavior.useHoeAt(this.world, pos);
//                                    state = this.world.getBlockState(pos);
//                                    isFarmland = state.getBlock() instanceof FarmlandBlock;
//
//                                    if (isFarmland)
//                                        this.world.setBlockState(pos, state.with(BlockStateProperties.MOISTURE_0_7,
//                                                7), 2);
//                                }
//                            }
//
//                            if (isFarmland && this.world.rand.nextFloat() >= 0.15F) {
//                                BlockPos plant = pos.up();
//                                if (!this.world.isAirBlock(plant)) {
//                                    // Here it dose the magic crop acceleratings
//                                    BlockState plantState = this.world.getBlockState(plant);
//                                    Block plantBlock = plantState.getBlock();
//                                    DefaultFarmerBehavior.useBonemeal(world, plant);
//                                    DefaultFarmerBehavior.useBonemeal(world, plant);
//                                    DefaultFarmerBehavior.useBonemeal(world, plant);
//
//                                    if ((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !
//                                    (plantBlock instanceof GrassBlock || plantBlock == Blocks.DIRT)) {
//                                        plantBlock.tick(plantState, (ServerWorld) this.world, plant, this.world.rand);
//                                        DefaultFarmerBehavior.useBonemeal(world, plant);
//                                        BlockState newState = this.world.getBlockState(plant);
//                                        if (newState != plantState) {
//                                            this.world.playEvent(2005, plant, 0);
//                                        }
//                                    }
//                                }
//                            }
//                        } else if (isMiddlePose) {
//                            this.setDead();
//                        }
//                    }
//                }
//            }
//// todo: set the configs later
//
//            //int dieTime = ConfigIntValues.WORMS_DIE_TIME.getValue();
//            boolean noDieTime = true;
//            int dieTime = (!noDieTime) ? 1000 : 0;
//            if (dieTime > 0 && this.timer >= dieTime) {
//                this.setDead();
//            }
//        }
//
//    }
//
//    @Override
//    public void setDead() {
//        EntityHelper.dropItem(getPosition(), new ItemStack(ModItems.WORM_FAST.get()), getEntityWorld());
//        super.setDead();
//    }
//
//
//    public ItemStack getItemDrop() {
//        return new ItemStack(ModItems.WORM_FAST.get(), 1);
//    }
//
//    @Override
//    protected void readAdditional(CompoundNBT compound) {
//        super.readAdditional(compound);
//    }
//
//    @Override
//    protected void writeAdditional(CompoundNBT compound) {
//        super.writeAdditional(compound);
//    }
//}
