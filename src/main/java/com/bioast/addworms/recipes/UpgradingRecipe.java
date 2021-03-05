package com.bioast.addworms.recipes;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.Level;

public class UpgradingRecipe extends SpecialRecipe {
    public UpgradingRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean foundWorm = false;
        boolean foundAugment = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (isAugment(stack)) {
                    if (foundAugment)
                        return false;
                    foundAugment = true;
                } else if (stack.getItem() == ModItems.WORM_MINER.get()) {
                    if (foundWorm)
                        return false;
                    foundWorm = true;
                } else return false;
            }
        }
        AddWorms.LOGGER.log(Level.DEBUG, foundAugment && foundWorm);
        return foundAugment && foundWorm;
    }

    private boolean isAugment(ItemStack stack) {
        return stack.getItem() == Items.OBSIDIAN
                || stack.getItem() == Items.GLOWSTONE
                || stack.getItem() == Items.PISTON
                || stack.getItem() == Items.OBSERVER
                || stack.getItem() == Items.GLOWSTONE_DUST
                || stack.getItem() == Items.SLIME_BALL
                || stack.getItem() == Items.NETHER_STAR
                || stack.getItem() == Items.DIRT
                || stack.getItem().isIn(Tags.Items.STONE);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack augment = ItemStack.EMPTY;
        ItemStack worm = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.WORM_MINER.get())
                    worm = stack;
                else augment = stack;
            }
        }
        boolean dontTag = false;
        ItemStack copy = worm.copy();
        CompoundNBT cmp = copy.getOrCreateTag();
        CompoundNBT miner_tag;
        boolean doVoid;
        boolean doSilk;
        int height;
        int speed;
        if (!cmp.contains(NBTHelper.Tags.TAG_MINER_HEADER)) {
            miner_tag = new CompoundNBT();
            doVoid = false;
            doSilk = false;
            height = 0;
            speed = 0;
        } else {
            miner_tag = cmp.getCompound(NBTHelper.Tags.TAG_MINER_HEADER);
            doVoid = miner_tag.getBoolean(NBTHelper.Tags.TAG_MINER_VOID);
            doSilk = cmp.getBoolean(NBTHelper.Tags.TAG_MINER_SILK);
            height = cmp.getInt(NBTHelper.Tags.TAG_MINER_HEIGHT);
            speed = cmp.getInt(NBTHelper.Tags.TAG_MINER_SPEED);
        }
        //Switch to tags
        //HEIGHT
        if (augment.getItem() == Items.PISTON && height < 11) // TODO the max height and speed put it into configs
            miner_tag.putInt(NBTHelper.Tags.TAG_MINER_HEIGHT, height + 1);
        if (augment.getItem() == Items.OBSERVER)
            miner_tag.putInt(NBTHelper.Tags.TAG_MINER_HEIGHT, 0);
        //SPEED
        if (augment.getItem() == Items.GLOWSTONE && speed < 12) // FIXME max Speed is currently 15
            miner_tag.putInt(NBTHelper.Tags.TAG_MINER_SPEED, speed + 4);
        if (augment.getItem() == Items.GLOWSTONE_DUST && speed < 15)
            miner_tag.putInt(NBTHelper.Tags.TAG_MINER_SPEED, speed + 1);
        if (augment.getItem() == Items.SLIME_BALL)
            miner_tag.putInt(NBTHelper.Tags.TAG_MINER_SPEED, 0);
        //VOID
        if (augment.getItem() == Items.OBSIDIAN && !doVoid)
            miner_tag.putBoolean(NBTHelper.Tags.TAG_MINER_VOID, true);
        if (augment.getItem().isIn(Tags.Items.STONE) && doVoid)
            miner_tag.putBoolean(NBTHelper.Tags.TAG_MINER_VOID, false);
        //SILK
        if (augment.getItem() == Items.NETHER_STAR && !doSilk)
            miner_tag.putBoolean(NBTHelper.Tags.TAG_MINER_SILK, true);
        if (augment.getItem() == Items.DIRT && doSilk)
            miner_tag.putBoolean(NBTHelper.Tags.TAG_MINER_SILK, false);
        //WIPE_UPGRADES
        if (augment.getItem() == Items.MILK_BUCKET) {
            dontTag = true;
            if (cmp.contains(NBTHelper.Tags.TAG_MINER_HEADER))
                cmp.remove(NBTHelper.Tags.TAG_MINER_HEADER);
        }

        if (!dontTag)
            cmp.put(NBTHelper.Tags.TAG_MINER_HEADER, miner_tag);
        return copy;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializer.UPGRADE;
    }
}
