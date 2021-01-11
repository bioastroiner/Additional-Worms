package com.bioast.addworms.client;

import com.bioast.addworms.client.models.SimpleModelLoader;
import com.bioast.addworms.client.models.food.DigestedFoodModelLoader;
import com.bioast.addworms.client.render.entities.worm.GeneralWormRenderer;
import com.bioast.addworms.entities.worm.FarmerWormEntity;
import com.bioast.addworms.init.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.ModEntityTypes.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        registerRenderers();
        registerModelLoaders();
    }

    public static void registerRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY.get(),
                renderManager -> new GeneralWormRenderer<FarmerWormEntity>(renderManager, ModItems.WORM.get()));
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_RED.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_RED.get()));
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_FAST.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_FAST.get()));
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_DIGESTER.get(),
                renderManager -> new GeneralWormRenderer(renderManager, ModItems.WORM_DIGESTER.get()));
    }

    // In later forge versions, this runs before resource loads, in 1.15 the
    // ModelRegistryEvent runs concurrently
    // with resource reloading, which makes these non-deterministic
    private static void registerModelLoaders() {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, "digested_food"),
                DigestedFoodModelLoader.INSTANCE);
    }

    @OnlyIn(Dist.CLIENT)
    private static <T extends IModelGeometry<T>> void addBuiltInModel(String id, Supplier<T> modelFactory) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, id),
                new SimpleModelLoader<>(modelFactory));
    }
}
