package com.bioast.addworms.client;

import com.bioast.addworms.AddWorms;
import com.bioast.addworms.entities.WormEntityBase;
import com.bioast.addworms.init.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;

@OnlyIn(Dist.CLIENT)
public class WormRenderer extends EntityRenderer<WormEntityBase> {

    protected float degrees;
    private static ItemStack stack = ItemStack.EMPTY;

    public void fixItemStack(ItemStack itemStack) {
        AddWorms.LOGGER.log(Level.DEBUG,"stack is fixed: "+ itemStack.toString());
        stack = itemStack;
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
        matrixStackIn.translate(0, 0.7D, 0);
        float currentTime = entityIn.getEntityWorld().getGameTime() + partialTicks;
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 10));
        overrideRender(entityIn,entityYaw,partialTicks,matrixStackIn,bufferIn,packedLightIn);
        renderItem(stack, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        
        matrixStackIn.push();
        matrixStackIn.translate(0,1.5D, 0);
        renderName(entityIn, entityIn.getDisplayName().getFormattedText(),matrixStackIn,bufferIn,packedLightIn);
        matrixStackIn.pop();
        super.render(entityIn,entityYaw, partialTicks,matrixStackIn,bufferIn,packedLightIn);
        super.shadowOpaque = 0f;
        super.shadowSize = 0f;

    }

    @Override
    protected boolean canRenderName(WormEntityBase entity) {
        return true;
    }

    protected void overrideRender(WormEntityBase entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn){

    }

    @Override
    protected void renderName(WormEntityBase entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.renderName(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
    }


    protected void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}
