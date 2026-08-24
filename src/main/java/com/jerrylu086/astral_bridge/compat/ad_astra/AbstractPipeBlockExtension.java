package com.jerrylu086.astral_bridge.compat.ad_astra;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface AbstractPipeBlockExtension {
    BlockState getUpdatedShape(BlockState state, BlockAndTintGetter world, BlockPos pos);
}
