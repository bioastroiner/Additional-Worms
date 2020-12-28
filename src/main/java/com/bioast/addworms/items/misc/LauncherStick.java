package com.bioast.addworms.items.misc;

import com.bioast.addworms.items.ModItem;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.bioast.addworms.AddWorms.MODID;

public class LauncherStick extends ModItem {

    public static final String TAG_USE = "isBeingUsed";
    public static final String TAG_ACTIVE = "isActive";

    public LauncherStick(Properties properties) {
        super(properties.maxDamage(64).group(ItemGroup.TOOLS));
        this.addPropertyOverride(new ResourceLocation(MODID, "use"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
                if(isBeingUsed(stack)){
                    return 1;
                }
                return 0;
            }
        });
    }

    protected boolean hasActiveTag(ItemStack stack) {

        return stack.getOrCreateTag().contains(TAG_ACTIVE);
    }

    protected void setActive(ItemStack stack, LivingEntity entity) {

        stack.getOrCreateTag().putLong(TAG_ACTIVE, entity.world.getGameTime() + 20);
    }

    protected boolean isActive(ItemStack stack) {

        return stack.getOrCreateTag().getLong(TAG_ACTIVE) > 0;
    }


    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);

        setActive(itemstack,player);

        if(worldIn.isRemote()){
            Vec3d v = player.getLookVec();
            v.inverse();
            Vec3d vec3d = player.getMotion();
            player.setMotion(vec3d.x + v.x, v.y + 0.1, vec3d.z + v.z);
            player.setMotion(player.getMotion().add((double)(-MathHelper.sin(player.rotationYaw * ((float)Math.PI / 180F)) * 0.2F), 0.0D,
                    (double)(MathHelper.cos(player.rotationYaw * ((float)Math.PI / 180F)) * 0.2F)));
            player.getCooldownTracker().setCooldown(this, 10);
            Debug.log(itemstack.getTag().toString());
        }
        itemstack.damageItem(2,player,p->{p.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
        return ActionResult.resultSuccess(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        NBTHelper.addDataToItemStack(stack,TAG_USE,false);
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

    public boolean isBeingUsed(ItemStack stack){
        stack.getOrCreateTag().putBoolean(TAG_USE,isActive(stack));
        return stack.getOrCreateTag().getBoolean(TAG_USE);
    }

}
