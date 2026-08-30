package com.jerrylu086.astral_bridge.compat.another_furniture;

import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.block.ShutterBlock;
import com.starfish_studios.another_furniture.block.properties.ShutterType;
import com.starfish_studios.another_furniture.registry.AFBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

// Mostly copied directly from the newer vesions of AF.
public class AnotherFurnitureCreateCompat {
    public static void register() {
        SeatInteractionBehaviour seatInteractionBehaviour = new SeatInteractionBehaviour();
        AllInteractionBehaviours.registerBehaviourProvider(state -> {
            if (state.getBlock() instanceof SeatBlock) {
                return seatInteractionBehaviour;
            }
            return null;
        });

        ShutterMovingInteraction shutterMovingInteraction = new ShutterMovingInteraction();
        AllInteractionBehaviours.registerBehaviourProvider(state -> {
            if (state.is(AFBlockTags.SHUTTERS)) {
                return shutterMovingInteraction;
            }
            return null;
        });

        ShutterMovingBehaviour shutterMovingBehaviour = new ShutterMovingBehaviour();
        AllMovementBehaviours.registerBehaviourProvider(state -> {
            if (state.is(AFBlockTags.SHUTTERS)) {
                return shutterMovingBehaviour;
            }
            return null;
        });

        CompatSeatMovementBehaviour seatMovementBehaviour = new CompatSeatMovementBehaviour();
        AllMovementBehaviours.registerBehaviourProvider(state -> {
            if (state.getBlock() instanceof SeatBlock) {
                return seatMovementBehaviour;
            }
            return null;
        });
    }

    public static boolean canStickToContraption(BlockState state, Direction direction) {
        if (state.getBlock() instanceof ShutterBlock) {
            ShutterType type = state.getValue(ShutterBlock.TYPE);
            if (type == ShutterType.MIDDLE && direction.getAxis().isVertical()) return true;
            if (type == ShutterType.TOP && direction == Direction.DOWN) return true;
            if (type == ShutterType.BOTTOM && direction == Direction.UP) return true;
        }
        return false;
    }
}
