package com.bioast.addworms.items;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class DigestedFood extends Item {


    public DigestedFood(Properties properties){
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        Food food = NBTHelper.readFoodFromNBT((CompoundNBT) itemstack.serializeNBT().get("tag"));

        if (playerIn.canEat(true)) {

            playerIn.setActiveHand(handIn);

            AddWorms.LOGGER.log(Level.DEBUG,itemstack.serializeNBT());
            AddWorms.LOGGER.log(Level.DEBUG,food.getHealing());

            playerIn.getFoodStats().addStats(food.getHealing(), food.getSaturation());
            //playerIn.addStat(Stats.ITEM_USED.get(itemstack.getItem()));

            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                    SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);

            itemstack.shrink(1);
            return ActionResult.resultConsume(itemstack);
        } else {
            return ActionResult.resultFail(itemstack);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        return entityLiving.onFoodEaten(worldIn, stack);
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {
        return super.updateItemStackNBT(nbt);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(stack.hasTag()){
            tooltip.add(new StringTextComponent("Hunger: "+stack.getTag().getCompound(NBTHelper.Tags.TAG_FOOD_HEADER).getInt(NBTHelper.Tags.TAG_HUNGER)));
            tooltip.add(new StringTextComponent("From: " + getID(stack)));

            if(stack.getTag().contains(NBTHelper.Tags.TAG_FOOD_HEADER)){
                stack.setDisplayName(new TranslationTextComponent(super.getTranslationKey(stack)).appendText(" (" + getID(stack) + ")"));
            }
        }
    }

    public String getID(ItemStack stack) {
        return stack.getTag().getCompound(NBTHelper.Tags.TAG_FOOD_HEADER).getString(NBTHelper.Tags.TAG_FOOD_NAME);
    }
}
