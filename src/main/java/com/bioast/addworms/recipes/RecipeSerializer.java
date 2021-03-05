package com.bioast.addworms.recipes;

import com.bioast.addworms.AddWorms;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AddWorms.MODID)
public class RecipeSerializer {

    @ObjectHolder("upgrade")
    public static IRecipeSerializer<UpgradingRecipe> UPGRADE;

    @Mod.EventBusSubscriber(modid = AddWorms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
            registry.register(new SpecialRecipeSerializer<>(UpgradingRecipe::new).setRegistryName(AddWorms.MODID,
                    "upgrade"));
        }
    }
}
