package com.bioast.addworms.client;

import com.bioast.addworms.client.models.food.DigestedFoodModelLoader;
import com.bioast.addworms.client.render.entities.worm.GeneralWormRenderer;
import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.items.misc.LauncherStick;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.ModEntityTypes.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void doClientStuff(FMLClientSetupEvent event) {
        registerRenderers();
        registerProperties();
    }

    private static void registerProperties() {
        ItemModelsProperties.registerProperty(
                ModItems.ITEM_LAUNCHER.get(),
                new ResourceLocation(MODID, "use"),
                LauncherStick.ACTIVATE_PROPERTY
        );
    }

    private static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(
                WORM_ENTITY_FARMER.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_FARMER.get()));
        RenderingRegistry.registerEntityRenderingHandler(
                WORM_ENTITY_RED.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_RED.get()));
        RenderingRegistry.registerEntityRenderingHandler(
                WORM_ENTITY_FAST.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_FAST.get()));
        RenderingRegistry.registerEntityRenderingHandler(
                WORM_ENTITY_DIGESTER.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_DIGESTER.get()));
    }

    @SubscribeEvent
    public static void modelRegisteryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(
                new ResourceLocation(MODID, "digested_food"),
                DigestedFoodModelLoader.INSTANCE);
    }
}
