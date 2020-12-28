package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.EntityHelper;
import com.bioast.addworms.utils.interfaces.IWorm;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/**
 * TODO: this code is bound to be moved
 */
public class WormEntityRed extends WormEntityBase implements IWorm {
    public WormEntityRed(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if(!world.isRemote){
            if(this.timer % 50 == 0){
                for (int x = -1; x <= 1; x++) { for (int z = -1; z <= 1; z++) { // Here we check for Blocks around ore Worm in a 3 by 3 area
                    BlockPos pos = new BlockPos(this.getPosX() + x, this.getPosY(), this.getPosZ() + z); // here we get the Block we are itirating through in a 3 x 3 area
                    boolean isMiddlePose = x==0 && z==0; // this is the center , usually were our worm is staying in
                    if(canWormify(world,pos, world.getBlockState(pos))){ // here we check if our worm can preform action on one of the blocks around it // okay now we need to make sure we don't make underneath ourself redstone
                        if(!isMiddlePose)world.setBlockState(pos,Blocks.REDSTONE_ORE.getDefaultState());
                        Debug.log("Block has been set at: "+pos.toString());
                    }else if(isMiddlePose){
                        this.setDead();
                        break;
                        // yay it works now
                    }
                }}
            }



// todo: set the configs later

            //int dieTime = ConfigIntValues.WORMS_DIE_TIME.getValue();
            boolean noDieTime = true;
            int dieTime = (!noDieTime)?1000:0;
            if (dieTime > 0 && this.timer >= dieTime) {
                this.setDead();
            }
        }

    }

    public static boolean canWormify(World world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.STONE;
    }

    @Override
    public void setDead() {
        EntityHelper.dropItem(getPosition(),getItemDrop(),getEntityWorld());
        Debug.log("",getItemDrop());
        super.setDead();
    }


    public ItemStack getItemDrop() {
        return new ItemStack(ModItems.WORM_RED.get(),1);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }
}
