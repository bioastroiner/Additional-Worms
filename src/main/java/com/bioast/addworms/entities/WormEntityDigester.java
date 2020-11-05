package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.DigestHelper;
import com.bioast.addworms.utils.helpers.EntityHelper;
import com.bioast.addworms.utils.helpers.ParticleHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
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
        if(world.isRemote()) return;
        BlockPos particlePos = getPosition();
        ParticleHelper.spawnParticles(world,particlePos,10, ParticleTypes.EFFECT);
//        Debug.log(Integer.toString(EntityHelper.getItemEntitiesInArea(
//                new BlockPos(getPosX() - 1,getPosY() - 1,getPosZ() - 1),
//                new BlockPos(getPosX() + 1,getPosY() + 1,getPosZ() + 1),world).size()));
        if(true){
            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class,
                    new AxisAlignedBB(getPosX() - 1, getPosY(), getPosZ() - 1, getPosX() + 2,
                    getPosY() + 1, getPosZ() + 2));
            if(items.size() > 0){
                for(ItemEntity item: items){
//                Debug.log("",item.getItem());
                    if(item != null){
                        if(item.getItem().getItem().isFood()){
                            // here do stuff related to Digesting my food :)
                            EntityHelper.dropItem(item.getPosition(),
                                    DigestHelper.DigestFood(item.getItem().getItem().getFood(),2),world);
                            particlePos = item.getPosition();
                            item.remove();
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
