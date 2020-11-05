package com.bioast.addworms.entities;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WormEntityDigester extends WormEntityBase {

    public WormEntityDigester(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public ItemStack getItemDrop() {
        return null;
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

        if(timer % 20 == 0){
            for(ItemEntity entity: EntityHelper.getItemEntitiesInArea(
                    new BlockPos(getPosX() - 1,getPosY(),getPosZ() - 1),
                    new BlockPos(getPosX() + 1,getPosY(),getPosZ() + 1),world)){
                if(entity.getItem().getItem().isFood()){
                    // here do stuff related to Digesting my food :)
                    EntityHelper.dropItem(entity.getPosition(),
                            DigestHelper.DigestFood(entity.getItem().getItem().getFood()),world);
                    particlePos = entity.getPosition();
                }
            }
        }
        super.tick();
    }


}
