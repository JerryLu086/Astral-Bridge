package com.jerrylu086.astral_bridge.compat.another_furniture;

import com.mojang.datafixers.util.Pair;
import com.starfish_studios.another_furniture.block.ShutterBlock;
import com.starfish_studios.another_furniture.block.properties.ShutterType;
import net.minecraft.world.level.block.state.BlockState;

public class ShutterUtils {
    public static Pair<BlockState, BlockState> updateConnection(BlockState state, BlockState newState, BlockState neighbor, boolean above) {
        if (canConnectTo(state, neighbor) != canConnectTo(newState, neighbor)) {
            newState = cycleConnection(newState, above);
            neighbor = cycleConnection(neighbor, !above);
        }

        return Pair.of(newState, neighbor);
    }

    public static BlockState cycleConnection(BlockState state, boolean top) {
        return state.setValue(ShutterBlock.TYPE, cycleConnection(state.getValue(ShutterBlock.TYPE), top));
    }

    public static ShutterType cycleConnection(ShutterType type, boolean top) {
        return ShutterType.values()[type.ordinal() ^ (top ? 1 : 3)];
    }

    public static boolean canConnectTo(BlockState state, BlockState other) {
        return other.is(state.getBlock())
                       //&& other.getValue(VERTICAL) == state.getValue(VERTICAL)
                       && other.getValue(ShutterBlock.FACING) == state.getValue(ShutterBlock.FACING)
                       && other.getValue(ShutterBlock.OPEN) == state.getValue(ShutterBlock.OPEN)
                       && other.getValue(ShutterBlock.LEFT) == state.getValue(ShutterBlock.LEFT);
    }
}
