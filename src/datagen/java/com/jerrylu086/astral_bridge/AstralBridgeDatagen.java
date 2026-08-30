package com.jerrylu086.astral_bridge;

import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModTags;
import com.simibubi.create.AllTags.AllItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.EntityTypeTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import slimeknights.tconstruct.world.TinkerWorld;

@SuppressWarnings("unused")
public class AstralBridgeDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModItemTagProvider::new);
        gen.addProvider(ModEntityTypeTagProvider::new);
    }

    static class ModItemTagProvider extends ItemTagProvider {
        public ModItemTagProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(AllItemTags.PRESSURIZED_AIR_SOURCES.tag)
                    .addOptional(ModItems.SPACE_SUIT.getRegistryName())
                    .addOptional(ModItems.NETHERITE_SPACE_SUIT.getRegistryName())
                    .addOptional(ModItems.JET_SUIT.getRegistryName());
        }
    }

    static class ModEntityTypeTagProvider extends EntityTypeTagProvider {
        public ModEntityTypeTagProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(ModTags.LIVES_WITHOUT_OXYGEN)
                    .addOptional(TinkerWorld.skySlimeEntity.getId());
        }
    }
}
