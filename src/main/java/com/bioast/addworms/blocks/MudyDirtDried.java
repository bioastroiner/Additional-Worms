package com.bioast.addworms.blocks;

import com.bioast.addworms.init.ModBlocks;
import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.ParticleHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class MudyDirtDried extends MudyDirt {

    public MudyDirtDried(Properties properties) {
        super(properties);
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(worldIn.isRemote()) return;
        if(!player.isCreative()){
            Random random = new Random();
            float f1 = random.nextFloat();
            float f2 = random.nextFloat();
            if(f1 > 0.99f){
                dropList.add(new ItemStack(ModItems.WORM_FAST.get(),1));
            }
            if(f2 > 0.99f){
                dropList.add(new ItemStack(ModItems.WORM_RED.get(),1));
            }
            dropList.add(new ItemStack(Blocks.DIRT));
            for (ItemStack stack:dropList) {
                ItemStack dropStack = stack;
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX() + 0.5,pos.getY()+0.5,pos.getZ()+0.5,dropStack));
            }
            dropList.clear();
            ParticleHelper.spawnParticles(worldIn,pos.add(0.5d,0.5d,0.5d),10, ParticleTypes.COMPOSTER);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    }
}
