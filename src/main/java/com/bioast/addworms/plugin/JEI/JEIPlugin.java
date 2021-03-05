package com.bioast.addworms.plugin.JEI;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.recipes.UpgradingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return null;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.WORM_MINER.get(), ItemStack::toString);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(UpgradingRecipe.class, UpgradingRecipeWrapper::new);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.WORM_MINER.get()), VanillaTypes.ITEM, "a worm that " +
                "mines blocks around it");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {

    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
