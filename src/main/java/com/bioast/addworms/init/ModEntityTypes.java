package com.bioast.addworms.init;

import com.bioast.addworms.entities.WormEntityDigester;
import com.bioast.addworms.entities.WormEntityFast;
import com.bioast.addworms.entities.WormEntityRed;
import com.bioast.addworms.entities.worm.FarmerWormEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.InitRegister.ENTITY_TYPES;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntityTypes {
    //Worms
    public static final RegistryObject<EntityType<FarmerWormEntity>> WORM_ENTITY = ENTITY_TYPES.register("worm_entity"
            ,
            () -> EntityType.Builder.create(FarmerWormEntity::new, EntityClassification.MISC).size(0.1f, 0.1f).build(new ResourceLocation(MODID, "worm_entity").toString()));
    public static final RegistryObject<EntityType<WormEntityRed>> WORM_ENTITY_RED = ENTITY_TYPES.register(
            "worm_entity_red",
            () -> EntityType.Builder.create(WormEntityRed::new, EntityClassification.MISC).size(0.1f, 0.1f).build(new ResourceLocation(MODID, "worm_entity_red").toString()));
    public static final RegistryObject<EntityType<WormEntityFast>> WORM_ENTITY_FAST = ENTITY_TYPES.register(
            "worm_entity_fast",
            () -> EntityType.Builder.create(WormEntityFast::new, EntityClassification.MISC).size(0.1f, 0.1f).build(new ResourceLocation(MODID, "worm_entity_fast").toString()));
    public static final RegistryObject<EntityType<WormEntityDigester>> WORM_ENTITY_DIGESTER = ENTITY_TYPES.register(
            "worm_entity_digester", () -> EntityType.Builder.create(WormEntityDigester::new,
                    EntityClassification.MISC).size(0.1f, 0.1f).build(new ResourceLocation(MODID,
                    "worm_entity_digester").toString()));
}
