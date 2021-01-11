package com.bioast.addworms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

// TODO : Don't use this class, remove it
public abstract class MudyDirtBase extends ModBlock {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public MudyDirtBase(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isCrouching()) {
            if (entityIn.isBurning()) entityIn.extinguish();
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        Vector3d vec3d = entityIn.getMotion();
        if (vec3d.y < -0.13D) {
            double d0 = -0.05D / vec3d.y;
            entityIn.setMotion(new Vector3d(vec3d.x * d0, -0.05D, vec3d.z * d0));
        } else {
            entityIn.setMotion(new Vector3d(vec3d.x, -0.05D, vec3d.z));
        }

        entityIn.fallDistance = 0.0F;
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.playSound(SoundEvents.BLOCK_SLIME_BLOCK_FALL, 1.0F, 1.0F);
        if (!worldIn.isRemote) {
            worldIn.setEntityState(entityIn, (byte) 54);
        }
        if (entityIn.onLivingFall(fallDistance, 0.2F)) {
            entityIn.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F,
                    this.soundType.getPitch() * 0.75F);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return false;
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                                        ISelectionContext context) {
        return SHAPE;
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.SHOVEL;
    }
}
