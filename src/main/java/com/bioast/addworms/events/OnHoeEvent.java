package com.bioast.addworms.events;

import com.bioast.addworms.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.bioast.addworms.AddWorms.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OnHoeEvent {
    @SubscribeEvent
    public void onHoe(UseHoeEvent event){
        //wormsEnabled &&
        if (event.getResult() != Event.Result.DENY) {
            World world = event.getContext().getWorld();
            if (!world.isRemote) {
                BlockPos pos = event.getContext().getPos();
                if (world.isAirBlock(pos.up())) {
                    BlockState state = world.getBlockState(pos);
                    if ((state.getBlock() instanceof GrassBlock || state == Blocks.GRASS_BLOCK.getDefaultState() || state == Blocks.DIRT.getDefaultState()) && world.rand.nextFloat() >= 0.20F) {
                        ItemStack stack = new ItemStack(ModItems.WORM.get(), world.rand.nextInt(2) + 1);
                        ItemEntity item = new ItemEntity(event.getContext().getWorld(), pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
                        world.addEntity(item);
                    }
                }
            }
        }
    }
}
