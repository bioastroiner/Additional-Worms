package com.bioast.addworms.entities;

import com.bioast.addworms.utils.interfaces.IWorm;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * TODO: this code is bound to be removed
 */
public abstract class WormEntityBase extends Entity implements IWorm {

    public int timer;

    public WormEntityBase(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.setBoundingBox(null);
    }
    public static boolean canWormify(World world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        boolean rightBlock = block instanceof FarmlandBlock || block instanceof GrassBlock || block == Blocks.GRASS_BLOCK || block == Blocks.DIRT;
        if (rightBlock) {
            BlockPos posUp = pos.up();
            BlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BushBlock || blockUp.canBeReplacedByLeaves(blockUp.getDefaultState(),world,posUp);
        } else {
            return false;
        }
    }
    public static boolean canWormify(World world, BlockPos pos, BlockState state,boolean rightBlock) {
        if (rightBlock) {
            BlockPos posUp = pos.up();
            BlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BushBlock || blockUp.canBeReplacedByLeaves(blockUp.getDefaultState(),world,posUp);
        } else {
            return false;
        }
    }
    @Override
    public void tick() {
        if(!this.world.isRemote){
            this.timer++;
        }
    }
    public void setDead(){
        BlockPos pos = getPosition();
        Minecraft.getInstance().player.sendMessage(new StringTextComponent("worm died at: "+pos.toString()));
        this.remove();
    }

    @Override
    protected void registerData() {

    }
    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.timer = compound.getInt("Timer");
    }
    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
    }
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
