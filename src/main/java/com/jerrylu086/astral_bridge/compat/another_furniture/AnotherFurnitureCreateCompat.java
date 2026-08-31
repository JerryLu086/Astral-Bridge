package com.jerrylu086.astral_bridge.compat.another_furniture;

import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import com.simibubi.create.content.contraptions.BlockMovementChecks.CheckResult;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.block.ShutterBlock;
import com.starfish_studios.another_furniture.block.properties.ShutterType;
import com.starfish_studios.another_furniture.registry.AFBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

// Mostly copied directly from the newer vesions of AF.
public class AnotherFurnitureCreateCompat {
    public static void register() {
        BlockMovementChecks.registerAttachedCheck((state, world, pos, dir) -> {
            Block block = state.getBlock();
            if (block instanceof ShutterBlock) {
                ShutterType type = state.getValue(ShutterBlock.TYPE);

                if (type == ShutterType.MIDDLE && dir.getAxis().isVertical()
                 || type == ShutterType.TOP    && dir == Direction.DOWN
                 || type == ShutterType.BOTTOM && dir == Direction.UP)
                    return CheckResult.SUCCESS;
            }
            return CheckResult.PASS;
        });

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

        CompatSeatMovementBehaviour seatMovementBehaviour = new CompatSeatMovementBehaviour();
        AllMovementBehaviours.registerBehaviourProvider(state -> {
            if (state.getBlock() instanceof SeatBlock) {
                return seatMovementBehaviour;
            }
            return null;
        });
    }
}
