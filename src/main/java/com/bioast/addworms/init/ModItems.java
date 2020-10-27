package com.bioast.addworms.init;

import com.bioast.addworms.items.BasicWormItem;
import com.bioast.addworms.items.WormItemBase;
import com.bioast.addworms.utils.groups.WormsGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.InitRegister.ITEMS;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {


    private static final Item.Properties baseProperty = new Item.Properties().group(WormsGroup.getInstance());
    public static final RegistryObject<Item> DEBUG = ITEMS.register("debug_item",()-> new Item(baseProperty));
    public static final RegistryObject<Item> WORM = ITEMS.register("item_worm",()-> new BasicWormItem(baseProperty.maxStackSize(16)));
    public static final RegistryObject<Item> WORM_RED = ITEMS.register("item_worm_red",()-> new Item(baseProperty.maxStackSize(16)));

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        ITEMS.getEntries().stream().forEach(itemRegistryObject -> {
            event.getRegistry().register(itemRegistryObject.get());
        });
    }
}
