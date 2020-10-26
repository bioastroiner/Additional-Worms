// imported from Actually Addition
package com.bioast.addworms.utils.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

import java.util.List;

/**
 * This is a helper interface for IFarmerBehavior.
 * <p>
 * This is not supposed to be implemented.
 * Can be cast to TileEntity.
 */
public interface IFarmer {

    Direction getOrientation();

    boolean canAddToSeeds(List<ItemStack> stacks);

    boolean canAddToOutput(List<ItemStack> stacks);

    void addToSeeds(List<ItemStack> stacks);

    void addToOutput(List<ItemStack> stacks);
}
