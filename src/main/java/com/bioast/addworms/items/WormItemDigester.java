package com.bioast.addworms.items;

import com.bioast.addworms.entities.WormEntityDigester;
import com.bioast.addworms.init.ModEntityTypes;
import com.bioast.addworms.utils.interfaces.IItemWorm;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WormItemDigester extends WormItemBase implements IItemWorm {

    public WormItemDigester(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        if (checkForWormsInArea(world,pos)) {
            return addWormToWorld(world,pos,stack,player,new WormEntityDigester(ModEntityTypes.WORM_ENTITY_DIGESTER.get(),
                    world));
        }
        return false;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        // looks like have to wait
        tooltip.add(new StringTextComponent("digester"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

//    @Override
//    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
//        if(player.world.isRemote) return;
//        NonNullList<ItemStack> Inv = ((PlayerEntity)player).inventory.mainInventory;
//        for (ItemStack itemStack : Inv){
//            if(itemStack.getItem().isFood()){
//                // here we digest stuffs
//                DigestFood(itemStack);
//            }
//        }
//
//    }
}
