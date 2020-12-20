package com.bioast.addworms.entities;

import com.bioast.addworms.utils.helpers.EntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author BioAstroiner
 *
 * this class consists of all attributes that is shared by all worm entities
 * and their basic behaviours.
 *
 * all worm entities should inherite this class.
 *
 * No worm Entities is allowed onAir blocks
 *
 */
public abstract class AbstractWormEntity extends Entity {

    /**
     * simple ticker to determain a worm's age
     * also needs to be saved in server world
     */
    protected int timer = 0;
    /**
     * use this very basic method to save items into memory mainly for make the worm
     * drop its original items as if it was stored in it out
     * it also saves those item into NBT to let the world save it
     */
    protected NonNullList<ItemStack> simpleItemStorage = NonNullList.create();

    public AbstractWormEntity(EntityType<?> entityTypeIn, World worldIn, @Nullable NonNullList<ItemStack> dropList) {
        super(entityTypeIn, worldIn);
        this.setBoundingBox(null);
    }

    @Override
    public void tick() {
        if(!this.world.isRemote){
            this.timer++;
        }
        if(!world.isAirBlock(getPosition()) || world.isAirBlock(getPosition().down())){
            this.kill();
        }
    }

    /**
     * a safer way than {@link Entity#remove()} to remove the worm entity's instance
     */
    public void kill(){
        onKill();
        this.remove();
    }

    /**
     * this method is called right before {@link Entity#remove()}
     */
    protected void onKill(){
        dropItems();
    }

    /**
     * ovverides the {@link Entity#remove()} with {@link AbstractWormEntity#kill()}
     *
     * use {@link Entity#remove(boolean keepData)} instead to skeep it.
     */
    @Override
    public void remove() {
        kill();
    }

    @Override
    public void remove(boolean keepData) {
        super.remove(keepData);
    }

    /**
     * @param items
     *
     * use this method to save Items in the wormEntity {@link AbstractWormEntity#simpleItemStorage}.
     */
    public void addItems(@Nonnull ItemStack... items){
        for(ItemStack item:items) simpleItemStorage.add(item);
    }

    /**
     * @return {@link AbstractWormEntity#simpleItemStorage}.
     */
    public NonNullList<ItemStack> getDropStackList() {
        return simpleItemStorage;
    }

    protected void dropItems(){
        for(ItemStack item: simpleItemStorage) EntityHelper.dropItem(getPosition(),item,world);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.timer = compound.getInt("Timer");
        ItemStackHelper.loadAllItems(compound,simpleItemStorage);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
        ItemStackHelper.saveAllItems(compound,getDropStackList());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }
}
