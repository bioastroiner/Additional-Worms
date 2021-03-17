package com.bioast.addworms.data;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.data.providers.ModBlockTagsProvider;
import com.bioast.addworms.data.providers.ModItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AddWorms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerator {
    private DataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        net.minecraft.data.DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            // Loot & Recipe
            //gen.addProvider(new ModLootTableProvider(gen));
            //gen.addProvider(new ModRecipeProvider(gen));
            // Tags
            ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
            ModItemTagsProvider blockItemTags = new ModItemTagsProvider(gen, blockTags, existingFileHelper);
            gen.addProvider(blockTags);
            gen.addProvider(blockItemTags);
        }
        if (event.includeClient()) {
            // BlockState
            //gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
            // ItemModel
            //gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        }
    }
}