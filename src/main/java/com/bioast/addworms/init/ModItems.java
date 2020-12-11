package com.bioast.addworms.init;

import com.bioast.addworms.items.WormItemDigester;
import com.bioast.addworms.items.DigestedFood;
import com.bioast.addworms.items.WormItemBasic;
import com.bioast.addworms.items.WormItemFast;
import com.bioast.addworms.items.WormItemRed;
import com.bioast.addworms.utils.groups.WormsGroup;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.InitRegister.ITEMS;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {


    private static final Item.Properties baseProperty = new Item.Properties().group(WormsGroup.getInstance());
    private static final Item.Properties wormBaseProperty = baseProperty.maxStackSize(16);
    //public static final RegistryObject<Item> DEBUG = ITEMS.register("debug_item",()-> new Item(baseProperty));
    public static final RegistryObject<Item> WORM = ITEMS.register("item_worm",()-> new WormItemBasic(wormBaseProperty));
    public static final RegistryObject<Item> WORM_RED = ITEMS.register("item_worm_red",()-> new WormItemRed(wormBaseProperty));
    public static final RegistryObject<Item> WORM_FAST = ITEMS.register("item_worm_fast",()->new WormItemFast(wormBaseProperty));
    public static final RegistryObject<Item> WORM_DIGESTER = ITEMS.register("item_worm_digester",()->new WormItemDigester(wormBaseProperty));
    public static final RegistryObject<Item> DIGESTED_FOOD = ITEMS.register("digested_food", ()->new DigestedFood(baseProperty));


    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        ITEMS.getEntries().stream().forEach(itemRegistryObject -> {
            event.getRegistry().register(itemRegistryObject.get());

        });
//        event.getRegistry().getEntries().stream().forEach(resourceLocationItemEntry -> {
//            if(resourceLocationItemEntry.getValue().isFood()){
//                Food food = resourceLocationItemEntry.getValue().getFood();
//                Food godFood = new Food.Builder()
//                        .hunger((int)(food.getHealing() * 1.5))
//                        .saturation(food.getSaturation() * 3)
//                        .setAlwaysEdible()
//                        .fastToEat()
//                        .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.7f)
//                        .build();
//                Item.Properties properties = new Item.Properties().food(godFood).group(ItemGroup.FOOD);
//                event.getRegistry().register(new DigestedFood(properties));
//            }
//        });
    }
}
