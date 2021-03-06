package com.bioast.addworms.items.misc;

import com.bioast.addworms.items.ModItem;
import com.bioast.addworms.utils.helpers.NBTHelper;
import com.bioast.addworms.utils.helpers.ParticleHelper;
import com.bioast.addworms.utils.helpers.SoundHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DigestedFood extends ModItem {

    private final Random rand = new Random();

    public DigestedFood(Properties properties) {
        super(properties.group(null));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        Food food = NBTHelper.readFoodFromNBT(itemstack.getOrCreateTag());

        if (playerIn.canEat(true)) {
            playerIn.setActiveHand(handIn);
            assert food != null;
            playerIn.getFoodStats().addStats(
                    food.getHealing(),
                    food.getSaturation());

            worldIn.playSound(null,
                    playerIn.getPosX(),
                    playerIn.getPosY(),
                    playerIn.getPosZ(),
                    SoundEvents.ENTITY_PLAYER_BURP,
                    SoundCategory.PLAYERS,
                    0.5F,
                    worldIn.rand.nextFloat() * 0.1F + 0.9F);

            if (!playerIn.isCreative()) {
                itemstack.shrink(1);
                SoundHelper.playSimpleSound(worldIn, playerIn.getPosition()
                        , SoundEvents.ENTITY_PLAYER_LEVELUP, 0.1f);
            }
            if (rand.nextFloat() > 0.8f) {
                playerIn.heal(Math.min(new Random(1).nextInt(1),
                        new Random(2).nextInt(1)) * 0.5f);
                ParticleHelper.spawnParticles(worldIn, playerIn.getPosition().up(),
                        rand.nextInt(1),
                        ParticleTypes.HEART);
                SoundHelper.playSimpleSound(worldIn, playerIn.getPosition(),
                        SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                        0.1f);
            }
            playerIn.setActiveHand(handIn);
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
                               ITooltipFlag flagIn
    ) {
        if (stack.hasTag()) {
            tooltip.add(new StringTextComponent(
                    "Hunger: " + stack.getOrCreateTag().getCompound(NBTHelper.Tags.TAG_FOOD_HEADER)
                            .getInt(NBTHelper.Tags.TAG_FOOD_HUNGER)));
            tooltip.add(new StringTextComponent(
                    "Saturation: " + stack.getOrCreateTag().getCompound(NBTHelper.Tags.TAG_FOOD_HEADER)
                            .getFloat(NBTHelper.Tags.TAG_FOOD_SAT)));
            tooltip.add(new StringTextComponent("Based on: " + getStoredFoood(stack)));
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }

    /**
     * get a potential digested food
     * to the tag
     */
    public Item getStoredFoood(ItemStack stack) {
        return Item.getItemById(stack.getOrCreateTag()
                .getCompound(NBTHelper.Tags.TAG_FOOD_HEADER)
                .getInt(NBTHelper.Tags.TAG_FOOD_ID));
    }

    /**
     * @param item the origin Item witch contains the food
     */
    // TODO : the method is unused and useless remove it
    public void setStoredFood(ItemStack stack, Item item) {
        stack.getOrCreateTag()
                .getCompound(NBTHelper.Tags.TAG_FOOD_HEADER)
                .putInt(NBTHelper.Tags.TAG_FOOD_ID,
                        Item.getIdFromItem(item));
    }

    public boolean isSimple(ItemStack stack) {
        return !stack.hasTag();
    }
}
