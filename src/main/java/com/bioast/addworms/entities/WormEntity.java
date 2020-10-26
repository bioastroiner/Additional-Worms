//package com.bioast.addworms.entities;
//
//import com.bioast.addworms.utils.helpers.DefaultFarmerBehavior;
//import com.bioast.addworms.utils.helpers.ParticleHelper;
//import net.minecraft.block.*;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.item.ItemEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.network.IPacket;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.StringTextComponent;
//import net.minecraft.world.World;
//import net.minecraft.world.server.ServerWorld;
//import net.minecraftforge.common.IPlantable;
//import net.minecraftforge.fml.network.NetworkHooks;
//import org.apache.logging.log4j.Level;
//
//import javax.annotation.Nullable;
//
//public class WormEntity extends Entity {
//
//    public int timer;
//    public ItemStack onDieItemDrop;
//
//    public WormEntity(EntityType<?> entityTypeIn, World worldIn) {
//        super(entityTypeIn, worldIn);
//        this.setBoundingBox(null);
//    }
//
//    @Override
//    public BlockPos getPosition() {
//        return super.getPosition();
//    }
//
//    @Override
//    public void setCustomName(@Nullable ITextComponent name) {
//        super.setCustomName(name);
//    }
//
//    public static boolean canWormify(World world, BlockPos pos, BlockState state) {
//        Block block = state.getBlock();
//        boolean rightBlock = block instanceof FarmlandBlock || block instanceof GrassBlock || block == Blocks.GRASS_BLOCK || block == Blocks.DIRT;
//        if (rightBlock) {
//            BlockPos posUp = pos.up();
//            BlockState stateUp = world.getBlockState(posUp);
//            Block blockUp = stateUp.getBlock();
//            return blockUp instanceof IPlantable || blockUp instanceof BushBlock || blockUp.canBeReplacedByLeaves(blockUp.getDefaultState(),world,posUp);
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void tick() {
//        if (!this.world.isRemote) {
//        this.timer++;
//
//        if (this.timer % 50 == 0) {
//            for (int x = -1; x <= 1; x++) {
//                for (int z = -1; z <= 1; z++) {
//                    BlockPos pos = new BlockPos(this.getPosX() + x, this.getPosY(), this.getPosZ() + z);
//                    BlockState state = this.world.getBlockState(pos);
//                    Block block = state.getBlock();
//                    boolean isMiddlePose = x == 0 && z == 0;
//
//                    if (canWormify(this.world, pos, state)) {
//                        boolean isFarmland = block instanceof FarmlandBlock;
//                        if (!isFarmland || state.get(BlockStateProperties.MOISTURE_0_7).intValue() < 7 || state.getBlock() instanceof GrassBlock) {
//                            if (isMiddlePose || this.world.rand.nextFloat() >= 0.45F) {
//                                if (!isFarmland) DefaultFarmerBehavior.useHoeAt(this.world, pos);
//                                state = this.world.getBlockState(pos);
//                                isFarmland = state.getBlock() instanceof FarmlandBlock;
//
//                                if (isFarmland) this.world.setBlockState(pos, state.with(BlockStateProperties.MOISTURE_0_7,7), 2);
//                            }
//                        }
//
//                        if (isFarmland && this.world.rand.nextFloat() >= 0.95F) {
//                            BlockPos plant = pos.up();
//                            if (!this.world.isAirBlock(plant)) {
//                                // Here it dose the magic crop acceleratings
//                                BlockState plantState = this.world.getBlockState(plant);
//                                Block plantBlock = plantState.getBlock();
//                                DefaultFarmerBehavior.useBonemeal(world,plant);
//                                DefaultFarmerBehavior.useBonemeal(world,plant);
//                                DefaultFarmerBehavior.useBonemeal(world,plant);
//
//                                if ((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !(plantBlock instanceof GrassBlock || plantBlock == Blocks.DIRT)) {
//                                    plantBlock.tick(plantState, (ServerWorld) this.world, plant, this.world.rand);
//                                    BlockState newState = this.world.getBlockState(plant);
//                                    if (newState != plantState) {
//                                        this.world.playEvent(2005, plant, 0);
//                                    }
//                                }
//                            }
//                        }
//                    } else if (isMiddlePose) {
//                        this.setDead();
//                    }
//                }
//            }
//        }
//
//// todo: set the configs later

//        //int dieTime = ConfigIntValues.WORMS_DIE_TIME.getValue();
//            boolean noDieTime = true;
//            int dieTime = (!noDieTime)?1000:0;
//        if (dieTime > 0 && this.timer >= dieTime) {
//            LOGGER.log(Level.DEBUG,"a Worm has Died");
//            this.setDead();
//        }
//    }
//    }
//
//    public void setDead(){
//        BlockPos pos = getPosition();
//        Minecraft.getInstance().player.sendMessage(new StringTextComponent("a worm has died at: "+pos.toString()));
//        ItemStack stack = onDieItemDrop;
//        if(stack != null || !stack.isEmpty()) getEntityWorld().addEntity(new ItemEntity(getEntityWorld(),getPosX(),getPosY() + 1,getPosZ(),stack));
//        else ParticleHelper.spawnParticles(world,pos, 15, ParticleTypes.DUST);
//        getEntityWorld().playSound(getPosX(),getPosY(),getPosZ(), SoundEvents.BLOCK_BAMBOO_PLACE, SoundCategory.NEUTRAL,10f,1f, false);
//        this.remove();
//    }
//
//    @Override
//    protected void registerData() {
//
//    }
//
//    @Override
//    protected void readAdditional(CompoundNBT compound) {
//        this.timer = compound.getInt("Timer");
//    }
//
//    @Override
//    protected void writeAdditional(CompoundNBT compound) {
//        compound.putInt("Timer", this.timer);
//
//    }
//
//
//
//    @Override
//    public IPacket<?> createSpawnPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//
////        return new IPacket<INetHandler>() {
////            @Override
////            public void readPacketData(PacketBuffer buf) throws IOException {
////                buf.readInt();
////                buf.readBlockPos();
////                buf.readVarInt();
////            }
////
////            @Override
////            public void writePacketData(PacketBuffer buf) throws IOException {
////                buf.writeInt(timer);
////            }
////
////            @Override
////            public void processPacket(INetHandler handler) {
////
////            }
////        };
//    }
//}
