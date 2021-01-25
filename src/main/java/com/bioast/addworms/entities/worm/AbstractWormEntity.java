package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.EntityHelper;
import com.bioast.addworms.utils.helpers.MathHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author BioAstroiner
 * <p>
 * this class consists of all attributes that is shared by all worm entities
 * and their basic behaviours.
 * ass well as majority of worms shared code
 * <p>
 * all worm entities should inherite this class.
 * <p>
 * No worm Entities is allowed onAir blocks
 * <p>
 * can cause issues in future too
 */
public abstract class AbstractWormEntity extends Entity {

    /**
     * this supplier simply wraps an ItemStack of
     * item form of worm into worm entity
     */
    protected final NonNullSupplier<ItemStack> wormItemSupplier;
    /**
     * worm property witch holds information about our worm's basic artibutes
     */
    final IWormProperty wormProperty;
    /**
     * simple ticker to determine a worm's age
     * also needs to be saved in server world
     */
    protected int timer = 0;
    /**
     * r = 1 -> 3 by 3
     * r = 2 -> 5 by 5
     */
    protected int range;
    /**
     * use this very basic method to save items into memory mainly for make the worm
     * drop its original items as if it was stored in it out
     * it also saves those item into NBT to let the world save it
     * NOTE: wormItem itself should not be saved in this List
     * FIXME : use Capability as i see this may not fit our future expectations
     */
    protected NonNullList<ItemStack> simpleItemStorage;

    /**
     * @param initialItemStorageContents List of items to initially be stored in
     * @param wormItem                   Item Registry Entry an instance of {@link GeneralWormItem}
     */
    public AbstractWormEntity(EntityType<?> entityTypeIn, World worldIn,
                              @Nullable NonNullList<ItemStack> initialItemStorageContents,
                              final Item wormItem, final IWormProperty wormProperty) {
        super(entityTypeIn, worldIn);
        this.setBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        simpleItemStorage = initialItemStorageContents;
        if (simpleItemStorage == null) simpleItemStorage = NonNullList.create();
        this.wormProperty = wormProperty;
        range = wormProperty.getDefaultBaseRange();
        wormItemSupplier = () -> new ItemStack(wormItem);
    }

    /**
     * @return unwraps item worm
     */
    public ItemStack getWormItemStack() {
        return wormItemSupplier.get();
    }

    @Override
    public void tick() {
        baseWormTick();
        if (!world.isRemote()) {
            if (wormProperty.isHostile()) {
                damageMobsAround(3, 5);
            }
        }
    }

    private void baseWormTick() {
        if (!this.world.isRemote) {
            this.timer++;
        }
        if (world.isAirBlock(getPosition()) || !world.isAirBlock(getPosition().up())) {
            Debug.log(getPosition(), null);//FIXME
            Debug.logClearless(world.getBlockState(getPosition()).toString());//FIXME
            this.kill();
        }
        if (wormProperty.willDie() && wormProperty.getDieTime() == this.timer) {
            this.kill(true);
        }
    }

    public int getRange() {
        return range;
    }

    /**
     * FIXME remove
     * implement this method in other
     * worms to get the placing conditions
     * such as dose this worm disAllows other worms to be placed around it or not. you can also enter
     * a lambda in the item class to tell it what to do
     *
     * @return true by default if there isn't any other worms in same spot
     * override it to change it remember to call the super to check for worms in same spot!
     * @implNote this method gets some input parameters all {@link Nullable} by default except the BlockPos parms
     */
    public boolean getPlacingCriteria() {
        AtomicBoolean isInSameSpace = new AtomicBoolean(false);
        getOtherWormsInArea(1, null, e -> {
            isInSameSpace.set(e.getPosition() == getPosition());
        });
        return isInSameSpace.get();
    }

    /**
     * a safer way than {@link Entity#remove()} to remove the worm entity's instance
     */
    public void kill(boolean overrideNotDrop) {
        onKill();
        if (!overrideNotDrop)
            dropOnKill();
        super.remove();
    }

    public void kill() {
        kill(false);
    }

    /**
     * this method is called right before {@link Entity#remove()}
     */
    protected void onKill() {

    }

    private void dropOnKill() {
        dropItems(wormProperty.doesDropWhenRemoved());
        if (wormProperty.doesDropWhenRemoved())
            EntityHelper.dropItem(getPosition(), getWormItemStack(), world);
    }

    /**
     * overrides the {@link Entity#remove()} with {@link AbstractWormEntity#kill()}
     * <p>
     * use {@link Entity#remove(boolean keepData)} instead to skip it.
     */
    @Override
    public void remove() {
        kill();
    }

    /**
     * use this method to remove entity
     * without triggiring {@link AbstractWormEntity#onKill()} event
     */
    @Override
    public void remove(boolean keepData) {
        super.remove(keepData);
    }

    /**
     * @param items use this method to save Items in the wormEntity {@link AbstractWormEntity#simpleItemStorage}.
     */
    public void addItems(@Nonnull ItemStack... items) {
        for (ItemStack item : items) simpleItemStorage.add(item);
    }

    /**
     * @return {@link AbstractWormEntity#simpleItemStorage}.
     */
    public NonNullList<ItemStack> getDropStackList() {
        return simpleItemStorage;
    }

    protected void dropItems(Boolean dropWorm) {
        if (simpleItemStorage == null || simpleItemStorage.isEmpty()) return;
        for (ItemStack item : simpleItemStorage) EntityHelper.dropItem(getPosition(), item, world);
    }

    /**
     * @param Radius half the radius of sqeuar that appears around
     *               and contains the entities in it
     *               r = 1 -> 3 by 3
     *               r = 2 -> 5 by 5
     *               etc.
     * @return all entities that are in this radius
     * <p>
     * be careful not use this method unprepared as it would
     * return all present entities within that area in the chunk.
     * including all item entities
     */
    public List<Entity> getEntitiesAround(int Radius) {
        return getEntitiesAround(Radius, Entity.class);
    }

    /**
     * default override of {@link AbstractWormEntity#getEntitiesAround(int Radius)}
     *
     * @param c the class of specific extension of {@link net.minecraft.entity.Entity} to track
     *          can be null, in that case the {@link net.minecraft.entity.Entity} class would be pass as normal
     */
    public List<Entity> getEntitiesAround(int Radius, @Nullable Class<? extends Entity> c) {
        Class<? extends Entity> cl = c;
        if (c == null) c = Entity.class;
        return getEntitiesAround(Radius, cl, MathHelper.getBoxAxisAlignedBB(Radius, getPosition()));
    }

    protected List<Entity> getEntitiesAround(int Radius, Class<? extends Entity> c, AxisAlignedBB aabb) {
        return world.getEntitiesWithinAABB(c, aabb);
    }

    /**
     * @param Radius
     * @return {@link AbstractWormEntity#getTileEntitiesAround(int Radius)#Pair<>(world.getBlockState(pos),world
     * .getTileEntity(pos)}.
     */
    public Map<BlockPos, Pair<BlockState, TileEntity>> getTileEntitiesPairAround(int Radius) {
        Map<BlockPos, Pair<BlockState, TileEntity>> tilemap = new HashMap<>();
        for (int i = 0; i <= Radius; i++) {
            for (int j = 0; j <= Radius; j++) {
                BlockPos pos = new BlockPos(i, this.getPosY(), j);
                Pair<BlockState, TileEntity> pair;
                if (world.getTileEntity(pos) != null) {
                    tilemap.put(pos, new Pair<>(world.getBlockState(pos), world.getTileEntity(pos)));
                }
            }
        }
        return tilemap;
    }

    public Map<BlockPos, TileEntity> getTileEntitiesAround(int Radius) {
        Map<BlockPos, TileEntity> ret = new HashMap<>();
        getTileEntitiesPairAround(Radius).forEach((bp, bstep) -> ret.put(bp, bstep.getSecond()));
        return ret;
    }

    /**
     * @param Radius  n -> 2n+1 x 2n+1
     * @param yOffset gets an y value for height
     *                note that it would act as dimensions
     *                and not an actually offset fixed point.
     *                only enter positive values (bec negative values would
     *                deSpawn the worm itself)
     * @return list of blockstates excluding AirBlocks
     */
    public List<Pair<BlockPos, BlockState>> getBlockStatesAround(int Radius, int yOffset) {
        List<Pair<BlockPos, BlockState>> bs = new ArrayList<>();
        for (int i = 0; i <= Radius; i++)
            for (int j = 0; j <= Radius; j++)
                for (int k = 0; k <= Math.abs(yOffset); k++) {
                    BlockPos pos = new BlockPos(getPosition().getX() + i, getPosition().getY() + k,
                            getPosition().getZ() + j);
                    if (world.isAirBlock(pos)) {
                        bs.add(new Pair(pos, world.getBlockState(pos)));
                    }
                }
        return bs;
    }

    public void breakBlocksAround(int Radius, int yOffset, boolean withDrops) {
        getBlockStatesAround(Radius, yOffset).forEach(p -> world.destroyBlock(p.getFirst(), withDrops));
    }

    public void damageMobsAround(int Radius, float Damage) {
        getEntitiesAround(Radius).forEach(e -> {
            if (EntityHelper.isEntityKillable(e)) {
                e.attackEntityFrom(DamageSource.GENERIC,
                        new Random().nextInt(1) * Damage * new Random().nextFloat());
            }
        });
    }

    public void gatherEntitiesToPoint(int Radius, BlockPos posTo, Class<? extends Entity> c) {
        getEntitiesAround(Radius, c).forEach(e -> {
            if (!(e instanceof AbstractWormEntity))
                e.setPosition(posTo.getX(), posTo.getY(), posTo.getZ());
        });
    }

    public void gatherEntitiesToSelf(int Radius, Class<? extends Entity> c) {
        gatherEntitiesToPoint(Radius, getPosition().up(), c);
    }

    public List<Entity> getOtherWormsInArea(int Radius, @Nullable Class<? extends AbstractWormEntity> c,
                                            @Nullable Consumer<?
                                                    extends Entity> consumer) {
        if (c == null) c = AbstractWormEntity.class;
        List<Entity> list = getEntitiesAround(Radius, c);
        if (consumer != null) list.forEach((Consumer<? super Entity>) consumer); //FIXME
        return list;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.timer = compound.getInt("Timer");
        ItemStackHelper.loadAllItems(compound, simpleItemStorage);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
        ItemStackHelper.saveAllItems(compound, getDropStackList());
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

    @Override
    protected void registerData() {

    }
}
