package com.jerrylu086.astral_bridge.mixin.compat.common.another_furniture;

import com.jerrylu086.astral_bridge.compat.another_furniture.AnotherFurnitureCreateCompat;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RequiresModList({"another_furniture", "create"})
@Mixin(value = BlockMovementChecks.class, remap = false)
public abstract class BlockMovementChecksMixin {
    @Inject(method = "isBlockAttachedTowardsFallback", at = @At(value = "HEAD"), cancellable = true)
    private static void onAllowStickyConnections(BlockState state, Level world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (AnotherFurnitureCreateCompat.canStickToContraption(state, direction)) cir.setReturnValue(true);
    }
}