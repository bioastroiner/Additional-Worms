package com.bioast.addworms.utils.helpers;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public final class MathHelper {

    public static final AxisAlignedBB GENERIC_3X3 = new AxisAlignedBB(
            0 - 1,
            0,
            0 - 1,
            0 + 1 + 1,
            0 + 1,
            0 + 1 + 1);
    private static final int ZERO = 0;

    public static AxisAlignedBB generateGeneric2np1x2np1(int radius, BlockPos offset) {
        return new AxisAlignedBB(
                offset.getX() - radius,
                offset.getY() + ZERO,
                offset.getZ() - radius,
                offset.getX() + radius + 1,
                offset.getY() + radius,
                offset.getZ() + radius + 1);
    }

    public static AxisAlignedBB getBoxAxisAlignedBB(int radius, BlockPos center) {
        return generateGeneric2np1x2np1(radius, center);
    }
}
