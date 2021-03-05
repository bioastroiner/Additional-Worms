package com.bioast.addworms.plugin.JEI;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.recipes.UpgradingRecipe;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class UpgradingRecipeWrapper implements ICustomCraftingCategoryExtension {
    ResourceLocation name;

    public UpgradingRecipeWrapper(UpgradingRecipe recipe) {
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
        IFocus<?> focus = recipeLayout.getFocus();
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.set(ingredients);

        if (focus != null) {
            ItemStack focused = (ItemStack) focus.getValue();

            if (focus.getMode() == IFocus.Mode.INPUT && focused.getItem() == ModItems.WORM_MINER.get()) {
                ItemStack copy = focused.copy();
                copy.setCount(1);
                group.set(2, copy);
            } else if (focused.getOrCreateTag().getBoolean("upg")) {
                group.set(1, new ItemStack(focused.getItem()));
            }
        }
    }

    @Override
    public void setIngredients(IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> augments = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> worm = ImmutableList.builder();
        augments.add(new ItemStack(Items.OBSIDIAN));
        augments.add(new ItemStack(Items.PISTON));
        augments.add(new ItemStack(Items.STONE));
        augments.add(new ItemStack(Items.GLOWSTONE));
        augments.add(new ItemStack(Items.GLOWSTONE_DUST));
        augments.add(new ItemStack(Items.SLIME_BALL));
        worm.add(new ItemStack(ModItems.WORM_MINER.get()));

        ItemStack upg_miner = new ItemStack(ModItems.WORM_MINER.get());
        upg_miner.setDisplayName(new StringTextComponent("Upgraded Worm"));
        upg_miner.getOrCreateTag().putBoolean("upg", true);

        builder.add(augments.build());
        builder.add(worm.build());

        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutput(VanillaTypes.ITEM, upg_miner);
    }
}
