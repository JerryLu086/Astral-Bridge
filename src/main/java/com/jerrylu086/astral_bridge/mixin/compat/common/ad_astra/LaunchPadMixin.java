package com.jerrylu086.astral_bridge.mixin.compat.common.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.jerrylu086.astral_bridge.compat.ad_astra.LocationStateUtil;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.content.contraptions.ITransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RequiresModList({"ad_astra", "create"})
@Mixin(LaunchPad.class)
public abstract class LaunchPadMixin implements ITransformableBlock {
    @Shadow @Final
    public static EnumProperty<LocationState> LOCATION;

    @Inject(method = "getPistonPushReaction", at = @At("HEAD"), cancellable = true)
    private void onGetPistonPushReaction(BlockState state, CallbackInfoReturnable<PushReaction> cir) {
        cir.setReturnValue(PushReaction.DESTROY);
    }

    @Override
    public BlockState transform(BlockState state, StructureTransform transform) {
        return state.setValue(LOCATION, LocationStateUtil.rotatePad(state.getValue(LOCATION), transform.rotation, transform.rotationAxis));
    }
}
