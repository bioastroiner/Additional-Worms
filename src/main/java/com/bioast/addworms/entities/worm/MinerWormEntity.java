package com.bioast.addworms.entities.worm;

import com.bioast.addworms.items.worms.GeneralWormItem;
import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MinerWormEntity extends AbstractWormEntity {

    BlockPos currentPos;
    //Miner Upgrades START
    boolean doseVoid = false;
    boolean silk_upg = false;
    int speed_upg;
    int height_upg;
    Map<String, ItemStack> up_map = new HashMap<>();
    //Miner Upgrades END

    public MinerWormEntity(EntityType<?> entityType, World worldIn, Item wormItem) {
        super(entityType, worldIn, null, wormItem, ((GeneralWormItem) wormItem).wormProperty);
    }

    // start region STATIC

    public static CompoundNBT getMinerUpgradesInStack(ItemStack itemstack) {
        if (itemstack.hasTag())
            return itemstack.getTag().getCompound(NBTHelper.Tags.TAG_MINER_HEADER);
        return null;
    }

    public static ITextComponent FormatOutUpgrades(ItemStack itemstack) {
        CompoundNBT compound = getMinerUpgradesInStack(itemstack);
        if (compound != null) {
            String ret =
                    "Current Levels: " +
                            "\n --void :" + compound.getBoolean(NBTHelper.Tags.TAG_MINER_VOID) +
                            "\n --silk :" + compound.getBoolean(NBTHelper.Tags.TAG_MINER_SILK) +
                            "\n --speed:" + compound.getInt(NBTHelper.Tags.TAG_MINER_SPEED) +
                            "\n --height:" + compound.getInt(NBTHelper.Tags.TAG_MINER_HEIGHT) +
                            "\n [WIP]" +
                            "\n --- --- ---";
            return new StringTextComponent(ret).setStyle(Style.EMPTY.setFormatting(TextFormatting.RED));
        }
        return StringTextComponent.EMPTY;
    }

    // end region STATIC

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (getWormItemStack().getOrCreateTag().contains(NBTHelper.Tags.TAG_MINER_HEADER)) {
            CompoundNBT compound =
                    getDataInWormItemStack(NBTHelper.Tags.TAG_MINER_HEADER);
            doseVoid = compound.getBoolean(NBTHelper.Tags.TAG_MINER_VOID);
            silk_upg = compound.getBoolean(NBTHelper.Tags.TAG_MINER_SILK);
            speed_upg = compound.getInt(NBTHelper.Tags.TAG_MINER_SPEED);
            height_upg = compound.getInt(NBTHelper.Tags.TAG_MINER_HEIGHT);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (timer % getMiningRate() == 0) {
            for (int i = 0; i < new Random().nextInt(Math.min(getMiningRate(), 5)); i++) {
                mine();
            }
        }
    }

    private int getMiningRate() {
        int rate = 10 - (int) getSpeed();
        if (doseVoid && rate > 1) --rate;
        if (rate < 1) rate = 1;
        return rate;
    }

    @Override
    public float getSpeed() {
        return super.getSpeed() + speed_upg;
    }

    private int getMiningRange() {
        return getRange();
    }

    private int getVerRange() {
        return -height_upg;
    }

    private void mine() {
        int size = getMiningRange();
        BlockPos.getAllInBox(
                new BlockPos(getPosition().getX() - size, getPosition().getY(), getPosition().getZ() - size),
                new BlockPos(getPosition().getX() + size, getPosition().getY() - getVerRange(),
                        getPosition().getZ() + size))
                .filter(b -> !world.isAirBlock(b))
                .filter(b -> world.getBlockState(b).isSolid())
                .filter(b -> world.getBlockState(b).getBlockHardness(world, b) > 0)
                .filter(b -> !getPosition().equals(b.toImmutable()))
                .findAny()
                .ifPresent(b -> currentPos = b.toImmutable());
        if (!world.isRemote) {
            if (!world.isAirBlock(currentPos))
                world.destroyBlock(currentPos, !doseVoid);//TODO add fortune support later
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        loadMinerUpgrades(compound);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        saveMinerUpgrades(compound);
    }

    private void saveMinerUpgrades(CompoundNBT compound) {
        compound.putBoolean(NBTHelper.Tags.TAG_MINER_VOID, doseVoid);
        compound.putBoolean(NBTHelper.Tags.TAG_MINER_SILK, silk_upg);
        compound.putInt(NBTHelper.Tags.TAG_MINER_SPEED, speed_upg);
        compound.putInt(NBTHelper.Tags.TAG_MINER_HEIGHT, height_upg);
    }

    private void loadMinerUpgrades(CompoundNBT compound) {
        doseVoid = compound.getBoolean(NBTHelper.Tags.TAG_MINER_VOID);
        silk_upg = compound.getBoolean(NBTHelper.Tags.TAG_MINER_SILK);
        speed_upg = compound.getInt(NBTHelper.Tags.TAG_MINER_SPEED);
        height_upg = compound.getInt(NBTHelper.Tags.TAG_MINER_HEIGHT);
    }
}
