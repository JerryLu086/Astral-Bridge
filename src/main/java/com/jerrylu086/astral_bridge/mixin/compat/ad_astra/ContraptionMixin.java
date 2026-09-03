package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.StructureTransform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@RequiresModList({"ad_astra", "create"})
@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @WrapOperation(method = "addBlocksToWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F",
                    ordinal = 0))
    private float isLaunchPadFlipped(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Operation<Float> original,
                                     @Local StructureTransform transform, @Local(name = "state") BlockState state) {
        return (state.getBlock() instanceof LaunchPad || state.getBlock() instanceof SlidingDoorBlock)
                       && transform.rotationAxis != Axis.Y && transform.rotation != Rotation.NONE ? -1 : original.call(instance, blockGetter, blockPos);
    }
}
