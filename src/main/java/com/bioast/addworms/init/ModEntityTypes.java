package com.bioast.addworms.init;

import com.bioast.addworms.entities.BasicWormEntity;
import com.bioast.addworms.entities.WormEntityFast;
import com.bioast.addworms.entities.WormEntityRed;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.bioast.addworms.AddWorms.MODID;
import static com.bioast.addworms.init.InitRegister.ENTITY_TYPES;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final RegistryObject<EntityType<BasicWormEntity>> WORM_ENTITY = ENTITY_TYPES.register("worm_entity",()->EntityType.Builder.<BasicWormEntity>create(BasicWormEntity::new, EntityClassification.MISC).size(0.1f,0.1f).build(new ResourceLocation(MODID,"worm_entity").toString()));
    public static final RegistryObject<EntityType<WormEntityRed>> WORM_ENTITY_RED = ENTITY_TYPES.register("worm_entity_red",()->EntityType.Builder.<WormEntityRed>create(WormEntityRed::new, EntityClassification.MISC).size(0.1f,0.1f).build(new ResourceLocation(MODID,"worm_entity_red").toString()));
    public static final RegistryObject<EntityType<WormEntityFast>> WORM_ENTITY_FAST = ENTITY_TYPES.register("worm_entity_fast",()->EntityType.Builder.<WormEntityFast>create(WormEntityFast::new, EntityClassification.MISC).size(0.1f,0.1f).build(new ResourceLocation(MODID,"worm_entity_fast").toString()));

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        ENTITY_TYPES.getEntries().stream().forEach(entity ->{
            event.getRegistry().register(entity.get());
        });
    }
}
