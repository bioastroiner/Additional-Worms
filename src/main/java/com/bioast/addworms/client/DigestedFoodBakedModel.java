package com.bioast.addworms.client;

import com.bioast.addworms.utils.helpers.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DigestedFoodBakedModel implements IBakedModel {

    private final IBakedModel baseModel;

    private final CustomeOverrideList overrides;

    DigestedFoodBakedModel( IBakedModel baseModel)
    {
        this.baseModel = baseModel;
        this.overrides = new CustomeOverrideList();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return null;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean func_230044_c_() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

    public class CustomeOverrideList extends ItemOverrideList{
        CustomeOverrideList(){
            super();
        }

        @Nullable
        @Override
        public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
            return Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(Items.APPLE );
            //stack.getTag().getCompound(NBTHelper.Tags.TAG_FOOD_HEADER).getString(NBTHelper.Tags.TAG_FOOD_NAME)
        }
    }
}
