package com.jerrylu086.astral_bridge;

import com.jerrylu086.astral_bridge.compat.another_furniture.AnotherFurnitureCreateCompat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstralBridge implements ModInitializer {
	public static final String MOD_ID = "astral_bridge";
    public static final String MOD_NAME = "Astral Bridge";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		if (Util.checkLoaded("create", "another_furniture"))
			AnotherFurnitureCreateCompat.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
