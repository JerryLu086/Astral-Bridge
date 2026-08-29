package com.jerrylu086.astral_bridge;

import com.jerrylu086.astral_bridge.compat.another_furniture.AnotherFurnitureCreateCompat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AstralBridge implements ModInitializer {
	public static final String MOD_ID = "astral_bridge";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		if (checkLoaded("create", "another_furniture"))
			AnotherFurnitureCreateCompat.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

    public static boolean checkLoaded(List<String> mods) {
        return checkLoaded(mods.toArray(String[]::new));
    }

    public static boolean checkLoaded(String... mods) {
        for (String mod : mods) {
            if (!FabricLoader.getInstance().isModLoaded(mod))
                return false;
        }
        return true;
    }
}
