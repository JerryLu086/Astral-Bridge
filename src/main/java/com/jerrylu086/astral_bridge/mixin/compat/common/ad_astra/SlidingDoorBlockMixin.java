package com.jerrylu086.astral_bridge.mixin.compat.common.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RequiresModList("ad_astra")
@Mixin(SlidingDoorBlock.class)
public abstract class SlidingDoorBlockMixin {
    @Inject(method = "getPistonPushReaction", at = @At("HEAD"), cancellable = true)
    private void onGetPistonPushReaction(BlockState state, CallbackInfoReturnable<PushReaction> cir) {
        cir.setReturnValue(PushReaction.DESTROY);
    }
}
