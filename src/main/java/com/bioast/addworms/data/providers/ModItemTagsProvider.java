package com.bioast.addworms.data.providers;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.init.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,
                               ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, AddWorms.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        //copy(ModTags.Blocks.ORES_SILVER, ModTags.Items.ORES_SILVER);
        //copy(Tags.Blocks.ORES, Tags.Items.ORES);
        //copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
        //copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        createBuilderIfAbsent(ModTags.Items.WORM);
        //getOrCreateBuilder(ModTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        //getOrCreateBuilder(Tags.Items.INGOTS).addTag(ModTags.Items.INGOTS_SILVER);
    }
}