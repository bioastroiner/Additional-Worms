package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.Debug;
import com.bioast.addworms.utils.helpers.EntityHelper;
import com.bioast.addworms.utils.helpers.MathHelper;
import com.bioast.addworms.utils.helpers.NBTHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
     * worm property witch holds information about our worm's basic (and Unchangeable) artibutes
     */
    final IWormProperty wormProperty;
    /**
     * r = 1 -> 3 by 3
     * r = 2 -> 5 by 5
     */
    private final int rangeDefault;
    /**
     * determines our worm's level
     * unlike {@link AbstractWormEntity#wormProperty} it holds data related to levels
     * witch may change
     * and also writes itself into wormItemStack nbtTag
     */
    private ETiers tier;
    /**
     * simple ticker to determine a worm's age
     * also needs to be saved in server world
     * FIXME uh {@link Entity#ticksExisted} is a thing?!
     */
    protected int timer = 0;
    /**
     * use this very basic method to save items into memory mainly for make the worm
     * drop its original items as if it was stored in it out
     * it also saves those item into NBT to let the world save it
     * NOTE: wormItem itself should not be saved in this List
     * FIXME : use Capability as i see this may not fit our future expectations
     */
    protected NonNullList<ItemStack> simpleItemStorage;
    private @Nonnull
    ItemStack wormItemStack;

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
        rangeDefault = wormProperty.getDefaultBaseRange();
        wormItemStack = new ItemStack(wormItem);
        tier = ETiers.Wild;
    }

    /**
     * @return unwraps item worm
     */
    public ItemStack getWormItemStack() {
        return wormItemStack;
    }

    public void setWormItemStack(ItemStack itemStackIn) {
        wormItemStack = itemStackIn;
    }

    public ETiers getTier() {
        assert getWormItemStack() != null && tier.level > 1;
        tier = NBTHelper.readWormTierFromStack(getWormItemStack());
        return tier;
    }

    public int getLevel() {
        return getTier().level;
    }

    public int getRange() {
        return Math.max(rangeDefault, getTier().range);
    }

    public float getDamage() {
        return getTier().damage;
    }

    public float getSpeed() {
        return getTier().speed;
    }

    public void saveDataInWormItemStack(String key, INBT nbtIn) {
        getWormItemStack().getOrCreateTag().put(key, nbtIn);
    }

    public CompoundNBT getDataInWormItemStack(String key) {
        return (CompoundNBT)
                getWormItemStack().getOrCreateTag().get(key);
    }

    public void saveTiers() {
        getTier().setTier(wormItemStack);
    }

    @Override
    public void tick() {
        baseWormTick();
    }

    private void baseWormTick() {
        if (!this.world.isRemote) {
            this.timer++;
            saveTiers();
            if (wormProperty.isHostile()) {
                damageMobsAround(getRange(), getDamage());
            }
        }
        if (world.isAirBlock(getPosition()) || !world.isAirBlock(getPosition().up())) {
            Debug.log(getPosition(), null);//FIXME
            Debug.logClearless(world.getBlockState(getPosition()).toString());//FIXME
            this.kill();
        }
        if (wormProperty.willDie() && wormProperty.getDieTime() == this.timer && tier.level < 1) {
            // worm will not die out whatsoever if it  level 2 or moreis
            this.kill(true);
        }
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
     * @param radius  n -> 2n+1 x 2n+1
     * @param yOffset gets an y value for height offset
     * @return map of blockstates excluding AirBlocks
     */
    public Map<BlockPos, BlockState> getBlockStatesAround(int radius, @Nonnegative int yOffset,
                                                          @Nullable Predicate<Block> predicate) {
        Map<BlockPos, BlockState> map = new HashMap<>();
        int n = radius;
        int r = 2 * n + 1;
        for (int x = 0; x < r; x++)
            for (int z = 0; z < r; z++) {
                BlockPos pos = new BlockPos(
                        x + getPosX() - n,
                        yOffset + getPosY(),
                        z + getPosZ() - n);
                assert predicate != null;
                if (predicate.test(world.getBlockState(pos).getBlock()))
                    map.put(pos, world.getBlockState(pos));
            }
        return map;
    }

    /**
     * @param yOffset  enter yOffset = 0 if you want break blocks as the same level below worm visibility
     *                 it check to not break block below it to not de spawn the worm
     *                 y = 1 as if it was beside worm's model
     * @param toolType break blocks only harvest able with tool
     *                 enter null to allow all tool breaks
     */
    public void breakBlocksAround(int radius, int yOffset, int withHarvestLevel, @Nullable ToolType toolType,
                                  @Nullable Predicate<Block> predicate) {
        PlayerEntity player = FakePlayerFactory.getMinecraft((ServerWorld) world);
        getBlockStatesAround(radius, yOffset, predicate)
                .forEach((p, b) -> {
                    if (b.getBlock().getHarvestLevel(b.getBlockState()) <= withHarvestLevel) {
                        if (toolType == null || b.getBlock().getHarvestTool(b.getBlockState()) == toolType) {
                            b.getBlock().harvestBlock(world, player, p, b.getBlockState(), //FIXME
                                    world.getTileEntity(p), player.getHeldItem(player.getActiveHand()));
                        }
                    }
                });
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
        this.setWormItemStack(
                NBTHelper.loadSingularItemStack(compound));
        ItemStackHelper.loadAllItems(compound, simpleItemStorage);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
        NBTHelper.saveSingularItemStack(getWormItemStack(), compound);
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
