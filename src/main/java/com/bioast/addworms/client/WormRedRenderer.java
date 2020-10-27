package com.bioast.addworms.client;

import com.bioast.addworms.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class WormRedRenderer extends WormRenderer {
    public WormRedRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        fixItemStack(new ItemStack(ModItems.WORM_RED.get()));
    }

    @Override
    protected void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.WORM_RED.get()), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
