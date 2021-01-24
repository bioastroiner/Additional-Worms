package com.bioast.addworms.client.render.entities.worm;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractWormRenderer<T extends AbstractWormEntity> extends EntityRenderer<T> {

    protected float degrees;
    protected ItemStack stack = ItemStack.EMPTY;

    public AbstractWormRenderer(EntityRendererManager renderManager, Item itemWorm) {
        super(renderManager);
        fixItemStack(new ItemStack(itemWorm));
    }

    public void fixItemStack(ItemStack itemStack) {
        stack = itemStack;
    }

    @Override
    protected int getBlockLight(T entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                       IRenderTypeBuffer bufferIn, int packedLightIn) {

        matrixStackIn.push();

        matrixStackIn.translate(0, 0.7D, 0);
        float currentTime = entityIn.getEntityWorld().getGameTime() + partialTicks;
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 10));

        overrideRender(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        renderItem(stack, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.pop();
        /////////////////////
        matrixStackIn.push();
        matrixStackIn.translate(0, 1.5D, 0);
        matrixStackIn.pop();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        super.shadowOpaque = 0f;
        super.shadowSize = 0f;

    }

    @Override
    protected boolean canRenderName(T entity) {
        return true;
    }

    protected void overrideRender(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                                  IRenderTypeBuffer bufferIn, int packedLightIn) {
    }

    protected void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn,
                              IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED,
                combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
