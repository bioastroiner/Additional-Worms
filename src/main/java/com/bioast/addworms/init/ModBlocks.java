package com.bioast.addworms.init;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.blocks.MudyDirt;
import com.bioast.addworms.blocks.MudyDirtDried;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

import static com.bioast.addworms.init.InitRegister.BLOCKS;
import static com.bioast.addworms.init.InitRegister.ITEMS;
import static net.minecraft.block.Blocks.DIRT;

public final class ModBlocks {
    //Objects
    public static final RegistryObject<Block> MUDY_DIRT = register(
            "mudy_dirt",
            () -> new MudyDirt(Block.Properties.from(DIRT)
                    .speedFactor(0.8f)
                    .jumpFactor(0.7f)));

    public static final RegistryObject<Block> MUDY_DIRT_DRIED = register(
            "mudy_dirt_dried",
            () -> new MudyDirtDried(Block.Properties.from(MUDY_DIRT.get())
                    .speedFactor(1.2f)));

    //Registration
    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> reg = registerNoItem(name, block);
        ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties().group(AddWorms.WormsBlockGroup)));
        return reg;
    }

    static void register() {
    }
}
