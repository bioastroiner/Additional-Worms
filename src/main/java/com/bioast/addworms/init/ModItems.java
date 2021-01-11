package com.bioast.addworms.init;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.entities.worm.FarmerWormEntity;
import com.bioast.addworms.items.WormItemDigester;
import com.bioast.addworms.items.WormItemFast;
import com.bioast.addworms.items.WormItemRed;
import com.bioast.addworms.items.misc.DigestedFood;
import com.bioast.addworms.items.misc.LauncherStick;
import com.bioast.addworms.items.worms.GeneralWormItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import static com.bioast.addworms.init.InitRegister.ITEMS;

public final class ModItems {

    //Fields
    private static final Item.Properties baseProperty =
            new Item.Properties()
                    .group(
                            AddWorms.WormsItemGroup
                    );

    public static final RegistryObject<Item> DIGESTED_FOOD = ITEMS.register(
            "digested_food",
            () -> new DigestedFood(
                    baseProperty
            )
    );
    //Misc
    public static final RegistryObject<Item> ITEM_LAUNCHER = ITEMS.register(
            "launcher",
            () -> new LauncherStick(
                    baseProperty
            )
    );
    private static final Item.Properties wormBaseProperty =
            baseProperty
                    .maxStackSize(16);
    //Worms
    public static final RegistryObject<Item> WORM = ITEMS.register(
            "item_worm",
            () -> new GeneralWormItem(
                    wormBaseProperty,
                    world -> new FarmerWormEntity(
                            ModEntityTypes.WORM_ENTITY.get(),
                            world
                    )
            )
    );
    public static final RegistryObject<Item> WORM_RED = ITEMS.register(
            "item_worm_red",
            () -> new WormItemRed(
                    wormBaseProperty
            )
    );
    public static final RegistryObject<Item> WORM_FAST = ITEMS.register(
            "item_worm_fast",
            () -> new WormItemFast(
                    wormBaseProperty
            )
    );
    public static final RegistryObject<Item> WORM_DIGESTER = ITEMS.register(
            "item_worm_digester",
            () -> new WormItemDigester
                    (wormBaseProperty
                    )
    );

}
