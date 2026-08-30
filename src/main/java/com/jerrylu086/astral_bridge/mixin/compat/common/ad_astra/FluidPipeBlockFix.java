package com.jerrylu086.astral_bridge.mixin.compat.common.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.pipes.FluidPipeBlock;
import com.github.alexnijjar.ad_astra.blocks.pipes.PipeState;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@RequiresModList("ad_astra")
@Mixin(FluidPipeBlock.class)
public abstract class FluidPipeBlockFix extends AbstractPipeBlockFix {
    @Shadow
    @Final
    public static Map<Direction, EnumProperty<PipeState>> DIRECTIONS;

    protected FluidPipeBlockFix(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Unique @Override
    public BlockState getUpdatedShape(BlockState state, BlockAndTintGetter world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos offset = pos.relative(dir);
            boolean connect = world.getBlockState(offset).getBlock() instanceof FluidPipeBlock ||
                    (world instanceof Level level && FluidStorage.SIDED.find(level, offset, dir) != null);
                state.setValue(DIRECTIONS.get(dir), PipeState.NORMAL);
            if (connect) {
                if (state.getValue(DIRECTIONS.get(dir)).equals(PipeState.NONE)) {
                    state = state.setValue(DIRECTIONS.get(dir), PipeState.NORMAL);
                }
            } else {
                if (state.getValue(DIRECTIONS.get(dir)).equals(PipeState.NORMAL)) {
                    state = state.setValue(DIRECTIONS.get(dir), PipeState.NONE);
                }
            }
        }

        return state;
    }
}
