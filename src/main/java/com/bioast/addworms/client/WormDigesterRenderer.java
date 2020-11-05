package com.bioast.addworms.client;

import com.bioast.addworms.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class WormDigesterRenderer extends WormRenderer {
    public WormDigesterRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        fixItemStack(new ItemStack(ModItems.WORM_DIGESTER.get()));
    }

    ItemStack itemStackRender;

    @Override
    public void fixItemStack(ItemStack itemStack) {
        itemStackRender = itemStack;
    }


    @Override
    protected void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(itemStackRender, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
