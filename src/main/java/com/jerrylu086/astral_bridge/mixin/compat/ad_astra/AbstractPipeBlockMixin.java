package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.pipes.AbstractPipeBlock;
import com.jerrylu086.astral_bridge.compat.ad_astra.AbstractPipeBlockExtension;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@RequiresModList("ad_astra")
@Mixin(AbstractPipeBlock.class)
public abstract class AbstractPipeBlockMixin extends BaseEntityBlock implements AbstractPipeBlockExtension {
    protected AbstractPipeBlockMixin(Properties properties) {
        super(properties);
    }

    @WrapWithCondition(method = "setPlacedBy",
                   at = @At(value = "INVOKE",
                            target = "Lcom/github/alexnijjar/ad_astra/blocks/pipes/AbstractPipeBlock;updateShape(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private boolean onSetPlacedBy(AbstractPipeBlock instance, Level world, BlockPos pos, BlockState state) {
        return false;
    }

    @ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
    private BlockState onGetStateForPlacement(BlockState original, @Local BlockPlaceContext context) {
        return getUpdatedShape(original, context.getLevel(), context.getClickedPos());
    }

    @WrapWithCondition(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
            at = @At(value = "INVOKE",
                     target = "Lcom/github/alexnijjar/ad_astra/blocks/pipes/AbstractPipeBlock;updateShape(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)V"))
    private boolean onUpdateShape(AbstractPipeBlock instance, Level level, BlockPos blockPos, BlockState state, Direction direction) {
        return false;
    }
}
