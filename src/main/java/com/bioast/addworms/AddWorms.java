package com.bioast.addworms;

import com.bioast.addworms.client.*;
import com.bioast.addworms.init.InitRegister;
import com.bioast.addworms.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

import static com.bioast.addworms.AddWorms.*;
import static com.bioast.addworms.init.ModEntityTypes.*;

@Mod(MODID)
@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddWorms
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "addworms";
    public static AddWorms instance;

    public AddWorms() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        InitRegister.ENTITY_TYPES.register(bus);
        InitRegister.ITEMS.register(bus);
        InitRegister.BLOCKS.register(bus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY.get(), WormBasicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_RED.get(), WormRedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_FAST.get(), WormFastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WORM_ENTITY_DIGESTER.get(), WormDigesterRenderer::new);

//        ModItems.DIGESTED_FOOD.get().addPropertyOverride(new ResourceLocation(MODID, "item"), new IItemPropertyGetter() {
//            @Override
//            public float call(ItemStack p_call_1_, @Nullable World p_call_2_, @Nullable LivingEntity p_call_3_) {
//                return 0;
//            }
//        });
    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }
}
