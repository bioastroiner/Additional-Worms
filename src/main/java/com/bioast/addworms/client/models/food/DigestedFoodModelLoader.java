package com.bioast.addworms.client.models.food;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelLoader;

public class DigestedFoodModelLoader implements IModelLoader<DigestedFoodModel> {

    public static final DigestedFoodModelLoader INSTANCE = new DigestedFoodModelLoader();

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

    @Override
    public DigestedFoodModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        modelContents.remove("loader"); // Avoid recursion
        ResourceLocation baseModel = new ResourceLocation(JSONUtils.getString(
                modelContents, "baseModel"));
        return new DigestedFoodModel(baseModel);
    }

}
