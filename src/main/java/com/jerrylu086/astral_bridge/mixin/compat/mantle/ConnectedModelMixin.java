package com.jerrylu086.astral_bridge.mixin.compat.mantle;

import com.mojang.math.Transformation;
import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import io.github.fabricators_of_create.porting_lib.model.IModelData;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slimeknights.mantle.client.model.connected.ConnectedModel;

import java.util.function.Predicate;

// Mantle connected models (e.g. Tinkers' Clear Glass blocks) for Copycat blocks
@Mixin(targets = "slimeknights/mantle/client/model/connected/ConnectedModel$Baked")
public abstract class ConnectedModelMixin {
    @Shadow
    @Final
    private ConnectedModel parent;
    @Shadow
    @Final
    private ModelState transforms;

    @Shadow
    private static byte getConnections(Predicate<Direction> predicate) {
        throw new AssertionError();
    };

    @Inject(method = "getModelData",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelState;getRotation()Lcom/mojang/math/Transformation;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void getModelDataForCopycat(BlockAndTintGetter world, BlockPos pos, BlockState state, IModelData tileData,
                                        CallbackInfoReturnable<IModelData> cir, IModelData data) {
        Transformation rotation = transforms.getRotation();
        BlockState actualState = world.getBlockState(pos);
        ConnectedModelAccessor widenedParent = (ConnectedModelAccessor) parent;

        if (actualState.getBlock() instanceof CopycatBlock ufb) {
            data.setData(ConnectedModelAccessor.canIGetAConnection(), getConnections((dir) -> {
                BlockPos relative = pos.relative(Direction.rotate(rotation.getMatrix(), dir));
                BlockState neighbor = world.getBlockState(relative);
                if (widenedParent.getSides().contains(dir)
                        && !ufb.canFaceBeOccluded(actualState, dir)
                        && (neighbor.getBlock() instanceof CopycatBlock)) {
                    return CopycatBlock.getMaterial(world, pos).getBlock() == CopycatBlock.getMaterial(world, relative).getBlock();
                }

                return false;
            }));
            cir.setReturnValue(data);
        }
    }
}
