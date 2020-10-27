package com.bioast.addworms.client;

import com.bioast.addworms.entities.WormEntityBase;
import com.bioast.addworms.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WormRenderer extends EntityRenderer<WormEntityBase> {

    private float degrees;
    private static ItemStack stack = new ItemStack(ModItems.WORM.get());

    public static void fixItemStack() {
        stack = new ItemStack(ModItems.WORM.get());
    }

    public WormRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    protected int getBlockLight(WormEntityBase entityIn, float partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(WormEntityBase entity) {
        return null;
    }

    @Override
    public void render(WormEntityBase entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();



        matrixStackIn.translate(0.3D, 1.0D, 0.3D);
        float currentTime = entityIn.getEntityWorld().getGameTime() + partialTicks;
        matrixStackIn.translate(0D,  0.1D, 0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 2));
        renderItem(stack, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        super.render(entityIn,entityYaw, partialTicks,matrixStackIn,bufferIn,packedLightIn);
        matrixStackIn.pop();
        super.shadowOpaque = 0f;
        super.shadowSize = 0f;

    }

    @Override
    protected void renderName(WormEntityBase entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.renderName(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
    }


    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
