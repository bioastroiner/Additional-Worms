package com.bioast.addworms;

import com.bioast.addworms.init.ModBlocks;
import com.bioast.addworms.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.bioast.addworms.AddWorms.MODID;

@Mod(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddWorms {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "addworms";

    public static final ItemGroup WormsBlockGroup = new ItemGroup(ItemGroup.GROUPS.length,
            new TranslationTextComponent("tab." + MODID + "blocks").getString()) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.MUDY_DIRT.get());
        }
    };

    public static final ItemGroup WormsItemGroup = new ItemGroup(ItemGroup.GROUPS.length,
            new TranslationTextComponent("tab." + MODID + "items").getString()) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.WORM.get());
        }
    };

    public static AddWorms instance;

    public AddWorms() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Registration.registerEntries();
        bus.addListener(this::setup);
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }
}
