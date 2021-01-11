package com.bioast.addworms.client.models.food;

import com.bioast.addworms.client.models.DelegateBakedModel;
import com.bioast.addworms.items.misc.DigestedFood;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.PerspectiveMapWrapper;

import javax.annotation.Nullable;

/**
 * this code is being extracted form AE2's pattern
 * script
 *
 * @author credits goes to appencha AE2's author.
 */
public class DigestedFoodBakedModel extends DelegateBakedModel {

    private final CustomOverrideList overrides;

    public DigestedFoodBakedModel(IBakedModel baseModel) {
        super(baseModel);
        this.overrides = new CustomOverrideList();
    }


    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }

    @Override
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return getBaseModel().handlePerspective(cameraTransformType, mat);
    }

    /**
     * Since the ItemOverrideList handling comes before handling the perspective
     * awareness (which is the first place where we know how we are being rendered)
     * we need to remember the model of the crafting output, and make the decision
     * on which to render later on. Sadly, Forge is pretty inconsistent when it will
     * call the handlePerspective method, so some methods are called even on this
     * interim-model. Usually those methods only matter for rendering on the ground
     * and other cases, where we wouldn't render the crafting output model anyway,
     * so in those cases we delegate to the model of the encoded pattern.
     */
    private static class ShiftHoldingModelWrapper extends DelegateBakedModel {

        private final IBakedModel outputModel;

        private ShiftHoldingModelWrapper(IBakedModel patternModel, IBakedModel outputModel) {
            super(patternModel);
            this.outputModel = outputModel;
        }

        @Override
        public boolean doesHandlePerspectives() {
            return true;
        }

        @Override
        public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
            // No need to re-check for shift being held since this model is only handed out
            // in that case
            if (cameraTransformType == ItemCameraTransforms.TransformType.GUI) {
                ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transforms =
                        PerspectiveMapWrapper
                                .getTransforms(outputModel.getItemCameraTransforms());
                return PerspectiveMapWrapper.handlePerspective(this.outputModel, transforms, cameraTransformType, mat);
            } else {
                return getBaseModel().handlePerspective(cameraTransformType, mat);
            }
        }

        // This determines diffuse lighting in the UI, and since we want to render
        // the outputModel in the UI, we need to use it's setting here
        @Override
        public boolean isSideLit() {
            return outputModel.isSideLit();
        }
    }

    /**
     * Item Override Lists are the only point during item rendering where we can
     * access the item stack that is being rendered. So this is the point where we
     * actually check if shift is being held, and if so, determine the crafting
     * output model.
     */
    private class CustomOverrideList extends ItemOverrideList {

        @Nullable
        @Override
        public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld world,
                                            @Nullable LivingEntity livingEntity) {
            boolean shiftHeld = Screen.hasShiftDown();
            DigestedFood iep = (DigestedFood) stack.getItem();

            if (shiftHeld && !iep.isSimple(stack)) {
                ResourceLocation itemID;
                ItemStack digested = new ItemStack(iep.getID(stack));

                if (!digested.isEmpty()) {
                    IBakedModel realModel = Minecraft.getInstance().getItemRenderer().getItemModelMesher()
                            .getItemModel(digested);

                    // Give the item model a chance to handle the overrides as well
                    realModel = realModel.getOverrides().getOverrideModel(realModel, digested, world, livingEntity);
                    return new ShiftHoldingModelWrapper(getBaseModel(), realModel);
                }
            }

            return getBaseModel().getOverrides().getOverrideModel(model, stack, world, livingEntity);
        }
    }
}
