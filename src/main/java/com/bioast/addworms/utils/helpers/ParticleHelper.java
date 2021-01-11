package com.bioast.addworms.utils.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public final class ParticleHelper {
    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(IWorld worldIn, BlockPos posIn, int data, ParticleType particleType) {
        if (data == 0) {
            data = 15;
        }

        Random random = new Random();

        BlockState blockstate = worldIn.getBlockState(posIn);
        if (!blockstate.isAir(worldIn, posIn)) {
            for(int i = 0; i < data; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                worldIn.addParticle((IParticleData) particleType, (float) posIn.getX() + random.nextFloat(),
                        (double) posIn.getY() + (double) random.nextFloat() * blockstate.getShape(worldIn, posIn).getEnd(Direction.Axis.Y), (float) posIn.getZ() + random.nextFloat(), d0, d1, d2);
            }

        }
    }
    public static void spawnBonemealParticles(IWorld worldIn, BlockPos posIn, int data){
        spawnParticles(worldIn,posIn,data,ParticleTypes.HAPPY_VILLAGER);
    }
}
