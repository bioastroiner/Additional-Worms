package com.bioast.addworms.init;

import com.bioast.addworms.utils.groups.WormsBlockGroup;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.InitRegister.BLOCKS;
import static net.minecraft.block.Blocks.STONE;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static final RegistryObject<Block> DEBUG = BLOCKS.register("debug_block",()->new Block(Block.Properties.from(STONE)));

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();

        BLOCKS.getEntries().stream().filter(block -> !(block.get() instanceof FlowingFluidBlock))
                .map(RegistryObject::get).forEach(block->{
            final Item.Properties properties = new Item.Properties().group(WormsBlockGroup.getInstance());
            final BlockItem blockItem = new BlockItem(block,properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }

}
