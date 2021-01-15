package com.bioast.addworms.init;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.bioast.addworms.AddWorms.MODID;

/**
 * a simple class for holding refrences to most DeferredRegistries
 * that we use to decrease the pain in the long run
 * also unifies all part of regiseries in the near future
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Registration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, MODID);

    public static void registerEntries() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Registration.BLOCKS.register(bus);
        Registration.ITEMS.register(bus);
        Registration.ENTITY_TYPES.register(bus);
    }
}
