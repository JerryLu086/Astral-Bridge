package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import com.simibubi.create.content.contraptions.BlockMovementChecks.CheckResult;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

public class AdAstraCreateCompat {
    public static void register() {
        BlockMovementChecks.registerBrittleCheck((state) -> {
            Block block = state.getBlock();
            if (block instanceof SlidingDoorBlock) {
                return CheckResult.FAIL;
            }

            return CheckResult.PASS;
        });

        BlockMovementChecks.registerMovementNecessaryCheck((state, world, pos) -> {
            Block block = state.getBlock();
            if (block instanceof SlidingDoorBlock) {
                return CheckResult.SUCCESS;
            }

            return CheckResult.PASS;
        });

        BlockMovementChecks.registerMovementAllowedCheck((state, world, pos) -> {
            Block block = state.getBlock();
            if (block instanceof LaunchPad) {
                return CheckResult.SUCCESS;
            }
            if (block instanceof SlidingDoorBlock) {
                return CheckResult.SUCCESS;
            }

            return CheckResult.PASS;
        });

        BlockMovementChecks.registerAttachedCheck((state, world, pos, dir) -> {
            Block block = state.getBlock();
            if (block instanceof LaunchPad || block instanceof SlidingDoorBlock) {
                // Why the hell do they separate the two?
                LocationState loc = state.getOptionalValue(LaunchPad.LOCATION)
                                            .orElse(state.getValue(SlidingDoorBlock.LOCATION));
                Direction facing = state.getOptionalValue(SlidingDoorBlock.FACING).orElse(Direction.UP);

                return LocationStateUtil.connectsFrom(loc, dir, facing) ? CheckResult.SUCCESS : CheckResult.FAIL;
            }

            return CheckResult.PASS;
        });
    }
}
