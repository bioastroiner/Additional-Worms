package com.bioast.addworms.items.misc;

import com.bioast.addworms.items.ModItem;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LauncherStick extends ModItem {

    public static final String TAG_USE = "isBeingUsed";
    public static final String TAG_ACTIVE = "isActive";

    public LauncherStick(Properties properties) {
        super(properties.maxDamage(64).group(ItemGroup.TOOLS));
    }

    protected static boolean hasActiveTag(ItemStack stack) {

        return stack.getOrCreateTag().contains(TAG_ACTIVE);
    }

    protected static void setActive(ItemStack stack, LivingEntity entity) {

        stack.getOrCreateTag().putLong(TAG_ACTIVE, entity.world.getGameTime() + 20);
    }

    protected static boolean isActive(ItemStack stack) {

        return stack.getOrCreateTag().getLong(TAG_ACTIVE) > 0;
    }

    public static boolean isBeingUsed(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(TAG_USE, isActive(stack));
        return stack.getOrCreateTag().getBoolean(TAG_USE);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);

        setActive(itemstack, player);

        if (worldIn.isRemote()) {
            Vector3d v = player.getLookVec();
            v.inverse();
            Vector3d vec3d = player.getMotion();
            player.setMotion(vec3d.x + v.x, v.y + 0.1, vec3d.z + v.z);
            player.setMotion(player.getMotion().add(-MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)) * 0.2F, 0.0D,
                    MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)) * 0.2F));
            player.getCooldownTracker().setCooldown(this, 10);
        }
        itemstack.damageItem(2, player, p -> p.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return ActionResult.resultSuccess(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        NBTHelper.addDataToItemStack(stack, TAG_USE, false);
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!hasActiveTag(stack)) {
            return;
        }
        long activeTime = stack.getOrCreateTag().getLong(TAG_ACTIVE);

        if (entityIn.world.getGameTime() > activeTime) {
            stack.getOrCreateTag().remove(TAG_ACTIVE);
        }
    }

}
