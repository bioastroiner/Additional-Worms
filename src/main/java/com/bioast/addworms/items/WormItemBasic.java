package com.bioast.addworms.items;

import com.bioast.addworms.entities.BasicWormEntity;
import com.bioast.addworms.entities.WormEntityBase;
import com.bioast.addworms.init.ModEntityTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WormItemBasic extends WormItemBase {
    public WormItemBasic(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        // Prevents worms from being placed next to each other
        List<BasicWormEntity> worms = world.getEntitiesWithinAABB(BasicWormEntity.class, new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2));
        if (worms == null || worms.isEmpty()) {
            if (!world.isRemote) {
                BasicWormEntity worm = new BasicWormEntity(ModEntityTypes.WORM_ENTITY.get(),world);
                worm.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                worm.setCustomName(stack.getDisplayName());
                //LOGGER.log(Level.DEBUG,"i used It");
                world.addEntity(worm);
                if (!player.isCreative()) stack.shrink(1);
            }
            //LOGGER.log(Level.DEBUG,"SUCCESS");
            return true;

        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Classic worm nothing Special -- Tills , makes wet , Bonemill effect somewhat"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
