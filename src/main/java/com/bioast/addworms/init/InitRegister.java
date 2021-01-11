package com.bioast.addworms.init;

import com.bioast.addworms.AddWorms;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.bioast.addworms.AddWorms.MODID;

/**
 * a simple class for holding refrences to most DeferredRegistries
 * that we use to decrease the pain in the long run
 * also unifies all part of regiseries in the near future
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class InitRegister {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES =
            DeferredRegister.create(
                    ForgeRegistries.RECIPE_SERIALIZERS,
                    MODID
            );

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(
                    ForgeRegistries.ITEMS,
                    MODID
            );
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(
                    ForgeRegistries.BLOCKS,
                    MODID
            );
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.ENTITIES,
                    MODID
            );

    public static void registerEntries() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        BLOCKS
                .getEntries()
                .stream()
                .map(RegistryObject::get)
                .forEach(block -> {
                            final BlockItem blockItem =
                                    new BlockItem(
                                            block,
                                            new Item.Properties()
                                                    .group(
                                                            AddWorms.WormsBlockGroup
                                                    )
                                    );
                            blockItem
                                    .setRegistryName(
                                            Objects.requireNonNull(
                                                    block.getRegistryName()
                                            )
                                    );
                            event
                                    .getRegistry()
                                    .register(
                                            blockItem
                                    );
                        }
                );
    }
}
