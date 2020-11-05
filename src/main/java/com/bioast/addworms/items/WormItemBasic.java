package com.bioast.addworms.items;

import com.bioast.addworms.entities.BasicWormEntity;
import com.bioast.addworms.init.ModEntityTypes;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.interfaces.IItemWorm;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WormItemBasic extends WormItemBase implements IItemWorm {
    public WormItemBasic(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        if (checkForWormsInArea(world,pos)) {
            Debug.log("",stack);
            return addWormToWorld(world,pos,stack,player,new BasicWormEntity(ModEntityTypes.WORM_ENTITY.get(),world));
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Classic worm nothing Special -- Tills , makes wet , Bonemill effect somewhat"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
