package com.bioast.addworms.blocks;

import com.bioast.addworms.init.ModBlocks;
import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.ParticleHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MudyDirt extends MudyDirtBase {

    public MudyDirt(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!player.isCreative()){
            List<ItemStack> dropList = new ArrayList<ItemStack>();
            Random random = new Random();
            if(random.nextFloat() > 0.70f)
            dropList.add(new ItemStack(ModItems.WORM.get(),random.nextInt(1)));
            dropList.add(new ItemStack(ModBlocks.MUDY_DIRT.get()));
            for (ItemStack stack:dropList) {
                ItemStack dropStack = stack;
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX() + 0.5,pos.getY()+0.5,pos.getZ()+0.5,dropStack));
            }
            ParticleHelper.spawnBonemealParticles(worldIn,pos, 15);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
