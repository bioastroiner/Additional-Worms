package com.bioast.addworms.init;

import com.bioast.addworms.blocks.MudyDirt;
import com.bioast.addworms.blocks.MudyDirtDried;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import static com.bioast.addworms.init.InitRegister.BLOCKS;
import static net.minecraft.block.Blocks.DIRT;

public final class ModBlocks {
    public static final RegistryObject<Block> MUDY_DIRT = BLOCKS.register("mudy_dirt",
            () -> new MudyDirt(Block.Properties.from(DIRT)));
    public static final RegistryObject<Block> MUDY_DIRT_DRIED = BLOCKS.register("mudy_dirt_dried",
            () -> new MudyDirtDried(Block.Properties.from(MUDY_DIRT.get())));
}
