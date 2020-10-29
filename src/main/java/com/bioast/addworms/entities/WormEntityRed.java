package com.bioast.addworms.entities;

import com.bioast.addworms.init.ModItems;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.DefaultFarmerBehavior;
import com.bioast.addworms.utils.helpers.EntityHelper;
import com.bioast.addworms.utils.intefaces.IWorm;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import org.apache.logging.log4j.Level;

import javax.swing.text.StyledEditorKit;

public class WormEntityRed extends WormEntityBase implements IWorm {
    public WormEntityRed(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if(!world.isRemote){
//            if (this.timer % 50 == 0) {
//                for (int x = -1; x <= 1; x++) {
//                    for (int z = -1; z <= 1; z++) {
//                        BlockPos pos = new BlockPos(this.getPosX() + x, this.getPosY(), this.getPosZ() + z);
//                        BlockState state = this.world.getBlockState(pos);
//                        Block block = state.getBlock();
//                        boolean isMiddlePose = x == 0 && z == 0;
//                        if (canWormify(this.world, pos, state,block == Blocks.STONE)) {
//                            if(rand.nextFloat() > 0){
//                                world.setBlockState(pos,Blocks.REDSTONE_ORE.getDefaultState());
//                            }
//                        }
//                        Debug.log(Boolean.toString(isMiddlePose));
//                    }
//                }
//            }
            for (int x = -1; x <= 1; x++) { for (int z = -1; z <= 1; z++) { // Here we check for Blocks around ore Worm in a 3 by 3 area
                BlockPos pos = new BlockPos(this.getPosX() + x, this.getPosY(), this.getPosZ() + z); // here we get the Block we are itirating through in a 3 x 3 area
                boolean isMiddlePose = x==0 && z==0; // this is the center , usually were our worm is staying in
                if(canWormify(world,pos,world.getBlockState(pos),world.getBlockState(pos).getBlock() == Blocks.STONE && !isMiddlePose)){ // here we check if our worm can preform action on one of the blocks around it // okay now we need to make sure we don't make underneath ourself redstone
                    world.setBlockState(pos,Blocks.REDSTONE_ORE.getDefaultState());
                    Debug.log("Block has been set at: "+pos.toString());
                } else if(isMiddlePose) { // but it is the same {
                    this.setDead();
                }
            }}

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
