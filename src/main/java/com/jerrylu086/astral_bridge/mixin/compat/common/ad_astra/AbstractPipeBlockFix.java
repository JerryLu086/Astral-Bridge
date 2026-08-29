package com.jerrylu086.astral_bridge.mixin.compat.common.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.pipes.AbstractPipeBlock;
import com.jerrylu086.astral_bridge.compat.ad_astra.AbstractPipeBlockExtension;
import com.jerrylu086.astral_bridge.mixin.AstralBridgePlugin.RequiredMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RequiredMod("ad_astra")
@Mixin(AbstractPipeBlock.class)
public abstract class AbstractPipeBlockFix extends BaseEntityBlock implements AbstractPipeBlockExtension {
    @Shadow @Final
    public static BooleanProperty WATERLOGGED;

    protected AbstractPipeBlockFix(Properties properties) {
        super(properties);
    }

    @Inject(method = "setPlacedBy", at = @At("HEAD"), cancellable = true)
    private void onSetPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        super.setPlacedBy(level, pos, state, placer, stack);
        ci.cancel();
    }

    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    private void onGetStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(getUpdatedShape(defaultBlockState(), context.getLevel(), context.getClickedPos())
                           .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType().equals(Fluids.WATER)));
    }

    @Inject(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
            at = @At(value = "INVOKE", target = "Lcom/github/alexnijjar/ad_astra/blocks/pipes/AbstractPipeBlock;updateShape(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)V"),
            cancellable = true)
    private void onUpdateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(getUpdatedShape(state, world, currentPos));
    }
}
