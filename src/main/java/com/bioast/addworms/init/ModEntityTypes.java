package com.bioast.addworms.init;

import com.bioast.addworms.entities.worm.FarmerWormEntity;
import com.bioast.addworms.entities.worm.MinerWormEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.Registration.ENTITY_TYPES;

public final class ModEntityTypes {
    //Worms
    public static final RegistryObject<EntityType<Entity>> WORM_ENTITY_FARMER = ENTITY_TYPES.register(
            "worm_entity_farmer",
            () -> EntityType.Builder.create((entityType, world) -> new FarmerWormEntity(entityType, world,
                    ModItems.WORM_FARMER.get()), EntityClassification.MISC)
                    .size(0.1f, 0.1f)
                    .build(new ResourceLocation(MODID, "worm_entity_farmer").toString()));
    public static final RegistryObject<EntityType<Entity>> WORM_ENTITY_MINER = ENTITY_TYPES.register(
            "worm_entity_miner",
            () -> EntityType.Builder.create((entityType, world) -> new MinerWormEntity(entityType, world,
                    ModItems.WORM_MINER.get()), EntityClassification.MISC)
                    .size(1f, 1f)
                    .build(new ResourceLocation(MODID, "worm_entity_miner").toString()));
//    public static final RegistryObject<EntityType<WormEntityRed>> WORM_ENTITY_RED = ENTITY_TYPES.register(
//            "worm_entity_red",
//            () -> EntityType.Builder.create(WormEntityRed::new, EntityClassification.MISC)
//                    .size(0.1f, 0.1f)
//                    .build(new ResourceLocation(MODID, "worm_entity_red").toString()));
//    public static final RegistryObject<EntityType<WormEntityFast>> WORM_ENTITY_FAST = ENTITY_TYPES.register(
//            "worm_entity_fast",
//            () -> EntityType.Builder.create(WormEntityFast::new, EntityClassification.MISC)
//                    .size(0.1f, 0.1f)
//                    .build(new ResourceLocation(MODID, "worm_entity_fast").toString()));
//    public static final RegistryObject<EntityType<WormEntityDigester>> WORM_ENTITY_DIGESTER = ENTITY_TYPES.register(
//            "worm_entity_digester",
//            () -> EntityType.Builder.create(WormEntityDigester::new, EntityClassification.MISC)
//                    .size(0.1f, 0.1f)
//                    .build(new ResourceLocation(MODID, "worm_entity_digester").toString()));

    static void register() {
    }
}
