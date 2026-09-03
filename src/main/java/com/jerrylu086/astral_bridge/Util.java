package com.jerrylu086.astral_bridge;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.block.Rotation;

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

    public static int getAngleFromRotation(Rotation rot) {
        return switch (rot) {
            case NONE -> 0;
            case CLOCKWISE_90 -> 90;
            case CLOCKWISE_180 -> 180;
            case COUNTERCLOCKWISE_90 -> 270;
        };
    }
}
