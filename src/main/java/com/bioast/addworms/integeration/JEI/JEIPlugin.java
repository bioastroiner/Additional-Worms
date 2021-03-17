package com.bioast.addworms.integeration.JEI;

import com.bioast.addworms.entities.worm.MinerWormEntity;
import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.recipes.UpgradingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return null;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.WORM_MINER.get(), itemStack -> {
            List<ITextComponent> tooltip = itemStack.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);
            tooltip.add(MinerWormEntity.FormatOutUpgrades(itemStack));
            return "miner";
        });
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
        // adds description
        registration.addIngredientInfo(new ItemStack(ModItems.WORM_MINER.get()),
                VanillaTypes.ITEM,
                "a worm that mines blocks around it");
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
