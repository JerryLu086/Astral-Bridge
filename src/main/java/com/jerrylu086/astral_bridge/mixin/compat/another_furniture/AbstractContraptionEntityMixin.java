package com.jerrylu086.astral_bridge.mixin.compat.another_furniture;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.entity.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContraptionEntity.class)
public abstract class AbstractContraptionEntityMixin {
    @Shadow
    protected Contraption contraption;

    // Specifying both named and intermediary because we don't want refmap, as it fixes this but breaks others, and Tiny Remapper has some trouble finding this.
    @WrapOperation(method = { "positionRider", "method_24201" },
                   at = @At(value = "INVOKE",
                            target = "Lcom/simibubi/create/content/contraptions/actors/seat/SeatEntity;getCustomEntitySeatOffset(Lnet/minecraft/world/entity/Entity;)D"))
    private double onPositionRider(Entity entity, Operation<Double> original) {
        BlockPos localPos = this.contraption.getSeatOf(entity.getUUID());
        if (this.contraption != null && this.contraption.getBlocks().containsKey(localPos)) {
            BlockState sittingBlock = this.contraption.getBlocks().get(localPos).state;
            if (sittingBlock.getBlock() instanceof SeatBlock seatBlock) {
                return seatBlock.seatHeight() - 0.225;
            }
        }
        return original.call(entity);
    }

    @Inject(method = "canCollideWith", at = @At("HEAD"), cancellable = true)
    private void onCanCollideWith(Entity e, CallbackInfoReturnable<Boolean> cir) {
        if (e instanceof SeatEntity)
            cir.setReturnValue(false);
    }
}