package com.bioast.addworms.blocks;

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

    List<ItemStack> dropList = new ArrayList<ItemStack>();

    public MudyDirt(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockHarvested(
            World worldIn,
            BlockPos pos,
            BlockState state,
            PlayerEntity player
    ) {
        // loosers use loottables
        if (worldIn.isRemote())
            return;
        if (!player.isCreative()) {
            Random random = new Random();
            float f =
                    random.nextFloat();
            if (f > 0.96f)
                dropList.add(new ItemStack(ModItems.WORM.get(), 1));
            for (ItemStack stack : dropList) {
                ItemStack dropStack = stack;
                worldIn.addEntity(
                        new ItemEntity(
                                worldIn,
                                pos.getX() + 0.5,
                                pos.getY() + 0.5,
                                pos.getZ() + 0.5, dropStack
                        )
                );
            }
            dropList
                    .clear();
            ParticleHelper
                    .spawnParticles(
                            worldIn,
                            pos.add(
                                    0.5d,
                                    0.5d,
                                    0.5d
                            ),
                            10,
                            ParticleTypes.COMPOSTER
                    );
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
