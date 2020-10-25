package com.bioast.addworms.init;


import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import static com.bioast.addworms.init.InitRegister.BLOCKS;
import static net.minecraft.block.Blocks.STONE;

public class ModBlocks {

    public static final RegistryObject<Block> DEBUG = BLOCKS.register("debug_block",()->new Block(Block.Properties.from(STONE)));


}
