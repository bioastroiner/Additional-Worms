package com.bioast.addworms.client.render.entities.worm;

import com.bioast.addworms.entities.worm.AbstractWormEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.Item;

public class GeneralWormRenderer<T extends AbstractWormEntity> extends AbstractWormRenderer<T> {

    public GeneralWormRenderer(EntityRendererManager renderManager, Item wormItem) {
        super(renderManager, wormItem);
    }

    @Override
    protected void overrideRender(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                                  IRenderTypeBuffer bufferIn, int packedLightIn) {
    }
}
