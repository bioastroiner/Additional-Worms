package com.bioast.addworms.items;

import com.bioast.addworms.entities.WormEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static com.bioast.addworms.AddWorms.MODID;

public abstract class WormItemBase extends Item {

    boolean wormsEnabled = true;

    public WormItemBase(Properties properties) {
        super(properties);
        this.addPropertyOverride(new ResourceLocation(MODID, "worm"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
                return "worm mail".equalsIgnoreCase(String.valueOf(stack.getDisplayName())) ? 1f : 0f;
            }
        });
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        ItemStack stack = player.getHeldItem(hand);
        BlockState state = world.getBlockState(pos);
        if (WormEntityBase.canWormify(world, pos, state)) {
            if (placeWorm(player, pos, world, stack)) return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("===a worm==="));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}