package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class LocationStateUtil {
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.create("location", LocationState.class);

    public static LocationState rotatePad(LocationState location, Rotation rotation, Axis axis) {
        // I always wondered why this works despite it being clockwise, until I realized that I also got a flipped Y axis.
        int angle = rotation.ordinal(),
            x = location.ordinal() % 3 - 1,
            z = location.ordinal() / 3 - 1,
            xp = x,
            zp = z;

        if (axis == Axis.Y) {
            int sin = (angle & 1) * (1 - (angle & 2));
            int cos = ((angle + 1) & 1) * (1 - ((angle + 1) & 2));
            xp = x * cos - z * sin;
            zp = x * sin + z * cos;
        } else if (rotation == Rotation.CLOCKWISE_180) {
            xp *= axis == Axis.Z ? -1 : 1;
            zp *= axis == Axis.X ? -1 : 1;
        }

        return LocationState.values()[xp + 1 + (zp + 1) * 3];
    }

    public static LocationState mirror(LocationState location, Axis axis) {
        boolean horizontal = axis == Axis.Y;
        int x = location.ordinal() % 3,
            y = location.ordinal() / 3,
            xp = Math.floorMod(x + x - 1, 3),
            yp = Math.floorMod(y + y - 1, 3);

        return LocationState.values()[horizontal ? xp + y * 3 : x + yp * 3];
    }

    public static boolean connectsFrom(LocationState location, Direction direction, Direction facing) {
        if (facing.getAxis() == Axis.Y) {
            direction = direction.getCounterClockWise((Axis.X));
        } else {
            if (direction.getAxis() != Axis.Y)
                direction = Direction.from2DDataValue(Math.floorMod(direction.get2DDataValue() - facing.get2DDataValue(), 4));
        }

        int x = location.ordinal() % 3,
            y = location.ordinal() / 3;

        return switch (direction) {
            case UP   -> y > 0;
            case DOWN -> y < 2;
            case WEST -> x > 0;
            case EAST -> x < 2;
            default   -> false;
        };
    }
}
