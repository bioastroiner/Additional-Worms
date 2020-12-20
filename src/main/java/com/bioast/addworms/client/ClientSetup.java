package com.bioast.addworms.client;

import com.bioast.addworms.client.models.SimpleModelLoader;
import com.bioast.addworms.client.models.food.DigestedFoodModelLoader;
import com.bioast.addworms.client.render.entities.worm.*;
import com.bioast.addworms.entities.WormEntityBasic;
import com.bioast.addworms.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.ModEntityTypes.*;
import static com.bioast.addworms.init.ModEntityTypes.WORM_ENTITY_DIGESTER;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {

        if (Minecraft.getInstance() != null) {
            registerRenderers();
            registerModelLoaders();
        }
    }

    public static void registerRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY.get(),
                renderManager -> new GeneralWormRenderer<WormEntityBasic>(renderManager, ModItems.WORM.get()));
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

//        addBuiltInModel("glass", GlassModel::new);
//        addBuiltInModel("sky_compass", SkyCompassModel::new);
//        addBuiltInModel("dummy_fluid_item", DummyFluidItemModel::new);
//        addBuiltInModel("memory_card", MemoryCardModel::new);
//        addBuiltInModel("biometric_card", BiometricCardModel::new);
//        addBuiltInModel("drive", DriveModel::new);
//        addBuiltInModel("color_applicator", ColorApplicatorModel::new);
//        addBuiltInModel("spatial_pylon", SpatialPylonModel::new);
//        addBuiltInModel("paint_splotches", PaintSplotchesModel::new);
//        addBuiltInModel("quantum_bridge_formed", QnbFormedModel::new);
//        addBuiltInModel("p2p_tunnel_frequency", P2PTunnelFrequencyModel::new);
//        addBuiltInModel("facade", FacadeItemModel::new);
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(AppEng.MOD_ID, "encoded_pattern"),
//                EncodedPatternModelLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(AppEng.MOD_ID, "part_plane"),
//                PlaneModelLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(AppEng.MOD_ID, "crafting_cube"),
//                CraftingCubeModelLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(AppEng.MOD_ID, "uvlightmap"), UVLModelLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(new ResourceLocation(AppEng.MOD_ID, "cable_bus"),
//                new CableBusModelLoader((PartModels) Api.INSTANCE.registries().partModels()));

        ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, "digested_food"),
                DigestedFoodModelLoader.INSTANCE);

    }

    @OnlyIn(Dist.CLIENT)
    private static <T extends IModelGeometry<T>> void addBuiltInModel(String id, Supplier<T> modelFactory) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, id),
                new SimpleModelLoader<>(modelFactory));
    }


    public static void onModelBake(ModelBakeEvent event){

//        event.getModelRegistry().put(new ResourceLocation(MODID,"digested_food"),
//                new DigestedFoodBakedModel(event.getModelLoader()
//                        .getBakedModel(new ModelResourceLocation(ModItems.DIGESTED_FOOD.get().getRegistryName(), "inventory")
//                                , SimpleModelTransform.IDENTITY, Material::getSprite)));

//        event.getModelRegistry().put(new ModelResourceLocation(ModItems.DIGESTED_FOOD.get().getRegistryName(), "inventory"),
//                new DigestedFoodBakedModel(DefaultVertexFormats.ITEM));
    }
}
