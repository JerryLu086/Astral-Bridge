package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.jerrylu086.astral_bridge.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class LocationStateUtil {
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.create("location", LocationState.class);

    public static LocationState rotatePad(LocationState loc, Rotation rot, Axis axis) {
        int angle = Util.getAngleFromRotation(rot),
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

    public static LocationState mirror(LocationState loc, Axis axis) {
        boolean horizontal = axis == Axis.Y;
        int x = loc.ordinal() % 3,
            y = loc.ordinal() / 3,
            xp = Math.floorMod(x + x - 1, 3),
            yp = Math.floorMod(y + y - 1, 3);

        return LocationState.values()[horizontal ? xp + y * 3 : x + yp * 3];
    }

    public static boolean connectsFrom(LocationState loc, Direction dir, Direction facing) {
        if (facing.getAxis() == Axis.Y) {
            dir = dir.getCounterClockWise((Axis.X));
        } else {
            if (dir.getAxis() != Axis.Y)
                dir = Direction.from2DDataValue(Math.floorMod(dir.get2DDataValue() + (Direction.SOUTH.get2DDataValue() - facing.get2DDataValue()), 4));
        }
        return switch (loc) {
            case TOP_LEFT     -> switch (dir) { case     DOWN, EAST       -> true; default -> false; };
            case TOP          -> switch (dir) { case     DOWN, EAST, WEST -> true; default -> false; };
            case TOP_RIGHT    -> switch (dir) { case     DOWN,       WEST -> true; default -> false; };
            case LEFT         -> switch (dir) { case UP, DOWN, EAST       -> true; default -> false; };
            case CENTER       -> switch (dir) { case UP, DOWN, EAST, WEST -> true; default -> false; };
            case RIGHT        -> switch (dir) { case UP, DOWN,       WEST -> true; default -> false; };
            case BOTTOM_LEFT  -> switch (dir) { case UP, EAST             -> true; default -> false; };
            case BOTTOM       -> switch (dir) { case UP, EAST,       WEST -> true; default -> false; };
            case BOTTOM_RIGHT -> switch (dir) { case UP,             WEST -> true; default -> false; };
        };
    }
}
