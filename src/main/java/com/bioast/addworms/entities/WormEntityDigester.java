package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.items.DigestedFood;
import com.bioast.addworms.utils.helpers.*;
import com.bioast.addworms.utils.interfaces.IFoodable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
//        Debug.log(Integer.toString(EntityHelper.getItemEntitiesInArea(
//                new BlockPos(getPosX() - 1,getPosY() - 1,getPosZ() - 1),
//                new BlockPos(getPosX() + 1,getPosY() + 1,getPosZ() + 1),world).size()));
        if(timer % 20 == 0){
            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class,
                    new AxisAlignedBB(getPosX() - 1, getPosY(), getPosZ() - 1, getPosX() + 2,
                    getPosY() + 1, getPosZ() + 2));
            if(items.size() > 0){
                for(ItemEntity item: items){
//                Debug.log("",item.getItem());
                    if(item != null){
                        if(item.getItem().getItem().isFood()){
                            // here do stuff related to Digesting my food :)
                            //first check if the item isn't already digested
                            if(!(item.getItem().getItem() instanceof DigestedFood)){
                                ItemStack drop = new ItemStack(ModItems.DIGESTED_FOOD.get(),
                                        item.getItem().getCount());
                                CompoundNBT nbt = drop.serializeNBT();
                                Food food = item.getItem().getItem().getFood();
                                FoodHelper.writeFoodData(food,nbt,2);
                                drop.deserializeNBT(drop.serializeNBT().merge(nbt));

                                ///temp
                                Food godFood = new Food.Builder()
                                        .hunger(food.getHealing())
                                        .saturation(food.getSaturation())
                                        .setAlwaysEdible()
                                        .fastToEat()
                                        .effect(()->new EffectInstance(Effects.REGENERATION,500,1),0.5f)
                                        .build();
                                ((IFoodable)(drop.getItem())).addFood(godFood);


                                ///temp

                                EntityHelper.dropItem(item.getPosition(),drop,
                                        world);
                                particlePos = item.getPosition();
                                item.remove();
                            }


                        }
                    }
            }

//            for(ItemEntity entity: EntityHelper.getItemEntitiesInArea(
//                    new BlockPos(getPosX() - 1,getPosY() - 1,getPosZ() - 1),
//                    new BlockPos(getPosX() + 1,getPosY() + 1,getPosZ() + 1),world)){
//
//                }
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
