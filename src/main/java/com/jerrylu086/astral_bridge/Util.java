package com.jerrylu086.astral_bridge;

import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class Util {
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
