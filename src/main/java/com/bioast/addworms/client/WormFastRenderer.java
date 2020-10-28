package com.bioast.addworms.client;

import com.bioast.addworms.entities.WormEntityBase;
import com.bioast.addworms.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class WormFastRenderer extends WormRenderer {
    public WormFastRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        fixItemStack(new ItemStack(ModItems.WORM_FAST.get()));
    }

    ItemStack itemStackRender;

    @Override
    protected void overrideRender(WormEntityBase entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 0.5f));
        super.overrideRender(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public void fixItemStack(ItemStack itemStack) {
        itemStackRender = itemStack;
    }

    @Override
    protected void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(itemStackRender, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
