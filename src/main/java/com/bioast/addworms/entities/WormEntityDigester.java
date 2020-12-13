package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.items.DigestedFood;
import com.bioast.addworms.utils.helpers.*;
import com.bioast.addworms.utils.interfaces.IFoodable;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class WormEntityDigester extends WormEntityBase {

    public WormEntityDigester(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public ItemStack getItemDrop() {
        return new ItemStack(ModItems.WORM_DIGESTER.get());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    @Override
    public void tick() {
        BlockPos particlePos = getPosition();
        ParticleHelper.spawnParticles(world,particlePos,10, ParticleTypes.EFFECT);
        if(world.isRemote()) return;

        if(timer % 20 == 0){
            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class,
                    new AxisAlignedBB(getPosX() - 1, getPosY(), getPosZ() - 1, getPosX() + 2,
                    getPosY() + 1, getPosZ() + 2));

            if(items.size() > 0){
                for(ItemEntity item: items){
                    if(item != null){
                        if(item.getItem().getItem().isFood()){
                            if(!(item.getItem().getItem() instanceof DigestedFood)){

                                Item digestedFood = ModItems.DIGESTED_FOOD.get();
                                Debug.log(String.format("Res Loc:%s",ModItems.DIGESTED_FOOD.getId()));

                                Food ItemOnGroundFood = item.getItem().getItem().getFood();

                                ItemStack drop = new ItemStack(digestedFood,item.getItem().getCount());
                                CompoundNBT tag = drop.serializeNBT();
                                //drop.deserializeNBT(tag);
                                drop.setTagInfo(NBTHelper.Tags.TAG_FOOD_HEADER,
                                        NBTHelper.writeFoodToNBT(new Food.Builder().hunger(ItemOnGroundFood.getHealing()).saturation(ItemOnGroundFood.getSaturation()).build(),tag));
                                EntityHelper.dropItem(item.getPosition(),drop, world);

                                particlePos = item.getPosition();
                                item.remove();
                            }
                        }
                    }
            }
            }
        }
        if(world.isAirBlock(getPosition())){
            setDead();
        }
        super.tick();
    }

    @Override
    public void setDead() {
        EntityHelper.dropItem(getPosition(),getItemDrop(),getEntityWorld());
        super.setDead();
    }


}
