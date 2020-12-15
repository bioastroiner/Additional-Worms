package com.bioast.addworms.client.models;

import com.bioast.addworms.client.DigestedFoodBakedModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class DigestedFoodModel implements IModelGeometry<DigestedFoodModel> {

    private final ResourceLocation baseModel;

    public DigestedFoodModel(ResourceLocation baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery,
                            Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform,
                            ItemOverrideList overrides, ResourceLocation modelLocation) {
        IBakedModel baseModel = bakery.getBakedModel(this.baseModel, modelTransform, spriteGetter);
        return new DigestedFoodBakedModel(baseModel);
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        return modelGetter.apply(baseModel).getTextures(modelGetter, missingTextureErrors);
    }
}
