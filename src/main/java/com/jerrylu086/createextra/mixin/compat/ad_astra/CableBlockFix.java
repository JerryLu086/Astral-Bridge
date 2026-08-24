package com.jerrylu086.createextra.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.pipes.CableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import team.reborn.energy.api.EnergyStorage;

import java.util.Map;

@Mixin(CableBlock.class)
public abstract class CableBlockFix extends AbstractPipeBlockFix {
    @Shadow @Final
    public static Map<Direction, BooleanProperty> DIRECTIONS;

    protected CableBlockFix(Properties properties) {
        super(properties);
    }

    @Unique @Override
    public BlockState getUpdatedShape(BlockState state, BlockAndTintGetter world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos offset = pos.relative(dir);
            boolean connect = world.getBlockState(offset).getBlock() instanceof CableBlock ||
                    (world instanceof Level level && EnergyStorage.SIDED.find(level, offset, dir) != null);
            state = state.setValue(DIRECTIONS.get(dir), connect);
        }

        return state;
    }
}
