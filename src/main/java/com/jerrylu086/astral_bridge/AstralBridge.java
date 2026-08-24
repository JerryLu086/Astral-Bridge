package com.jerrylu086.astral_bridge;

import com.jerrylu086.astral_bridge.compat.another_furniture.AnotherFurnitureCompat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstralBridge implements ModInitializer {
	public static final String MOD_ID = "astral_bridge";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isModLoaded("another_furniture"))
			AnotherFurnitureCompat.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
