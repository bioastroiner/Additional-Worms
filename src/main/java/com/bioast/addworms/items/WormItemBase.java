package com.bioast.addworms.items;

import com.bioast.addworms.entities.WormEntityBase;
import com.bioast.addworms.init.ModEntityTypes;
import com.bioast.addworms.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.bioast.addworms.AddWorms.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WormItemBase extends Item {

    boolean wormsEnabled = true;
    public EntityType entityType;

    public WormItemBase(Properties properties,EntityType<? extends WormEntityBase> entityType) {
        super(properties);
        if(this.entityType == null) setEntityType(entityType);
        this.addPropertyOverride(new ResourceLocation(MODID, "worm"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
                return "worm mail".equalsIgnoreCase(String.valueOf(stack.getDisplayName())) ? 1f : 0f;
            }
        });
    }

    public void setEntityType(EntityType entityType){
        this.entityType = entityType;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        ItemStack stack = player.getHeldItem(hand);
        BlockState state = world.getBlockState(pos);
        if (WormEntityBase.canWormify(world, pos, state)) {
            if (placeWorm(player, pos, world, stack)) return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    protected boolean placeWorm(PlayerEntity player, BlockPos pos, World world, ItemStack stack) {
        // overide this with

//        // Prevents worms from being placed next to each other
//        List<WormEntityBase> worms = world.getEntitiesWithinAABB(WormEntityBase.class, new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2));
//        if (worms == null || worms.isEmpty()) {
//            if (!world.isRemote) {
//                WormEntityBase worm = new WormEntityBase(entityType,world);
//                worm.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
//                worm.setCustomName(stack.getDisplayName());
//                //LOGGER.log(Level.DEBUG,"i used It");
//                world.addEntity(worm);
//                if (!player.isCreative()) stack.shrink(1);
//            }
//            //LOGGER.log(Level.DEBUG,"SUCCESS");
//            return true;
//
//        }
//        return false;
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("===a worm==="));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @SubscribeEvent
    public void onHoe(UseHoeEvent event){
        if (wormsEnabled && event.getResult() != Event.Result.DENY) {
            World world = event.getContext().getWorld();
            if (!world.isRemote) {
                BlockPos pos = event.getContext().getPos();
                if (world.isAirBlock(pos.up())) {
                    BlockState state = world.getBlockState(pos);
                    if ((state.getBlock() instanceof GrassBlock || state == Blocks.GRASS_BLOCK.getDefaultState() || state == Blocks.DIRT.getDefaultState()) && world.rand.nextFloat() >= 0.20F) {
                        ItemStack stack = new ItemStack(ModItems.WORM.get(), world.rand.nextInt(2) + 1);
                        ItemEntity item = new ItemEntity(event.getContext().getWorld(), pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
                        world.addEntity(item);
                    }
                }
            }
        }
    }
}
