package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.pipes.AbstractPipeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This interface exists because we not only have to apply the updated state when calling {@link AbstractPipeBlock#updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos)}, but also {@link AbstractPipeBlock#getStateForPlacement(BlockPlaceContext)}.
 * And yes, I did try to delete this once before I remembered why I even created this at the first place, so I'm leaving this (mainly because docs are supa :cool:).
 */
public interface AbstractPipeBlockExtension {
    BlockState getUpdatedShape(BlockState state, BlockAndTintGetter world, BlockPos pos);
}
