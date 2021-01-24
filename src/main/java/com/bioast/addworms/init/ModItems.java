package com.bioast.addworms.init;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.items.WormItemDigester;
import com.bioast.addworms.items.WormItemFast;
import com.bioast.addworms.items.WormItemRed;
import com.bioast.addworms.items.misc.DigestedFood;
import com.bioast.addworms.items.misc.LauncherStick;
import com.bioast.addworms.items.worms.GeneralWormItem;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import static com.bioast.addworms.init.Registration.ITEMS;

public final class ModItems {
    //Misc
    public static final RegistryObject<Item> DIGESTED_FOOD = ITEMS.register(
            "digested_food",
            () -> new DigestedFood(new Item.Properties().group(AddWorms.WormsItemGroup)));
    public static final RegistryObject<Item> ITEM_LAUNCHER = ITEMS.register(
            "launcher",
            () -> new LauncherStick(new Item.Properties().group(AddWorms.WormsItemGroup)
                    .maxDamage(64)
                    .group(ItemGroup.TOOLS)));
    //Worms
    public static final RegistryObject<Item> WORM_FARMER = ITEMS.register(
            "item_worm",
            () -> new GeneralWormItem(new Item.Properties().group(AddWorms.WormsItemGroup)
                    .maxStackSize(16)
                    , new GeneralWormItem.Properties()
                    .setFloorBlocks(block -> block instanceof GrassBlock)
                    .setEntity(ModEntityTypes.WORM_ENTITY_FARMER)
                    .dropWhenRemoved(true)
                    .finalizeProperty()));
    //*************OLD*********
    public static final RegistryObject<Item> WORM_RED = ITEMS.register(
            "item_worm_red",
            () -> new WormItemRed(new Item.Properties().group(AddWorms.WormsItemGroup)
                    .maxStackSize(16))
    );
    public static final RegistryObject<Item> WORM_FAST = ITEMS.register(
            "item_worm_fast",
            () -> new WormItemFast(new Item.Properties().group(AddWorms.WormsItemGroup)
                    .maxStackSize(16))
    );
    public static final RegistryObject<Item> WORM_DIGESTER = ITEMS.register(
            "item_worm_digester",
            () -> new WormItemDigester(new Item.Properties().group(AddWorms.WormsItemGroup)
                    .maxStackSize(16))
    );

    static void register() {
    }

}
