package com.jerrylu086.astral_bridge.mixin.compat.create.client;

import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.compat.trinkets.GoggleTrinketRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Made this because the goggles look quite off when upside down with Created & Updated resource pack.
@RequiresModList("create")
@Mixin(GoggleTrinketRenderer.class)
public abstract class GoggleTrinketRendererMixin {
    @ModifyConstant(method = "render", constant = @Constant(floatValue = 180.0f, ordinal = 1))
    private float noFlip(float original) {
        return 0.0f;
    }

    @ModifyConstant(method = "render", constant = @Constant(doubleValue = -0.25, ordinal = 0))
    private double goLower(double original) {
        return -0.3125d;
    }
}
