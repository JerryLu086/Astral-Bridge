package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;

public class LocationStateUtil {
    public static LocationState rotatePad(LocationState loc, Rotation rot, Axis axis) {
        int angle = getAngleFromRotation(rot),
            x = loc.ordinal() % 3 - 1,
            z = loc.ordinal() / 3 - 1,
            xp = x,
            zp = z;

        if (axis == Axis.Y) {
            xp = (int) (x * Mth.cos(angle * Mth.DEG_TO_RAD) - z * Mth.sin(angle * Mth.DEG_TO_RAD));
            zp = (int) (x * Mth.sin(angle * Mth.DEG_TO_RAD) + z * Mth.cos(angle * Mth.DEG_TO_RAD));
        } else if (angle == 180) {
            xp *= axis == Axis.Z ? -1 : 1;
            zp *= axis == Axis.X ? -1 : 1;
        }

        return LocationState.values()[xp + 1 + (zp + 1) * 3];
    }

    public static boolean connectsFrom(LocationState loc, Direction dir) {
        return switch (loc) {
            case TOP_LEFT     -> dir == Direction.EAST || dir == Direction.SOUTH;
            case TOP          -> dir == Direction.EAST || dir == Direction.SOUTH || dir == Direction.WEST;
            case TOP_RIGHT    ->                          dir == Direction.SOUTH || dir == Direction.WEST;
            case LEFT         ->                          dir == Direction.SOUTH || dir == Direction.WEST || dir == Direction.NORTH;
            case CENTER       -> dir == Direction.EAST || dir == Direction.SOUTH || dir == Direction.WEST || dir == Direction.NORTH;
            case RIGHT        -> dir == Direction.EAST || dir == Direction.SOUTH                          || dir == Direction.NORTH;
            case BOTTOM_LEFT  -> dir == Direction.EAST                                                    || dir == Direction.NORTH;
            case BOTTOM       -> dir == Direction.EAST                           || dir == Direction.WEST || dir == Direction.NORTH;
            case BOTTOM_RIGHT ->                                                    dir == Direction.WEST || dir == Direction.NORTH;
        };
    }

    public static int getAngleFromRotation(Rotation rot) {
        return switch (rot) {
            case NONE -> 0;
            case CLOCKWISE_90 -> 90;
            case CLOCKWISE_180 -> 180;
            case COUNTERCLOCKWISE_90 -> -90;
        };
    }
}
