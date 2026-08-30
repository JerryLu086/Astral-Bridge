package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.jerrylu086.astral_bridge.AstralBridge;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AdAstraCompatEntityTypes {
    public static final EntityType<LaunchPadEntity> LAUNCH_PAD = Registry.register(Registry.ENTITY_TYPE, AstralBridge.id("launch_pad"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, LaunchPadEntity::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).build());
}
