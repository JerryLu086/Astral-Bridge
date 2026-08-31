package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import com.simibubi.create.content.contraptions.BlockMovementChecks.CheckResult;
import net.minecraft.world.level.block.Block;

public class AdAstraCreateCompat {
    public static void register() {
        BlockMovementChecks.registerAttachedCheck((state, world, pos, dir) -> {
            Block block = state.getBlock();
            if (block instanceof LaunchPad) {
                LocationState loc = state.getValue(LaunchPad.LOCATION);
                return LocationStateUtil.connectsFrom(loc, dir) ? CheckResult.SUCCESS : CheckResult.PASS;
            }
            /*if (block instanceof SlidingDoorBlock) {
                LocationState loc = state.getValue(LaunchPad.LOCATION);
                return LocationStateUtil.connectsFrom(loc, dir) ? CheckResult.SUCCESS : CheckResult.PASS;
            }*/

            return CheckResult.PASS;
        });
    }
}
