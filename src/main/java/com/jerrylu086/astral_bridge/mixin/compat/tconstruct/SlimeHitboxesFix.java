package com.jerrylu086.astral_bridge.mixin.compat.tconstruct;

import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.world.TinkerWorld;

@RequiresModList({"tconstruct"})
@Mixin(EntityType.class)
public abstract class SlimeHitboxesFix {
    @Unique
    private final EntityDimensions scaledDim = EntityDimensions.scalable(2.04f, 2.04f);

    @Inject(method = "getDimensions", at = @At(value = "TAIL"), cancellable = true)
    private void dimFix(CallbackInfoReturnable<EntityDimensions> cir) {
        EntityType self = (EntityType)(Object) this;
        if (self == TinkerWorld.earthSlimeEntity.get() ||
            self == TinkerWorld.skySlimeEntity.get()   ||
            self == TinkerWorld.enderSlimeEntity.get() ||
            self == TinkerWorld.terracubeEntity.get()) {
            cir.setReturnValue(this.scaledDim);
        }
    }

    @Inject(method = "getWidth", at = @At(value = "TAIL"), cancellable = true)
    private void widthFix(CallbackInfoReturnable<Float> cir) {
        EntityType self = (EntityType)(Object) this;
        if (self == TinkerWorld.earthSlimeEntity.get() ||
            self == TinkerWorld.skySlimeEntity.get()   ||
            self == TinkerWorld.enderSlimeEntity.get() ||
            self == TinkerWorld.terracubeEntity.get()) {
            cir.setReturnValue(this.scaledDim.width);
        }
    }

    @Inject(method = "getHeight", at = @At(value = "TAIL"), cancellable = true)
    private void heightFix(CallbackInfoReturnable<Float> cir) {
        EntityType self = (EntityType)(Object) this;
        if (self == TinkerWorld.earthSlimeEntity.get() ||
            self == TinkerWorld.skySlimeEntity.get()   ||
            self == TinkerWorld.enderSlimeEntity.get() ||
            self == TinkerWorld.terracubeEntity.get()) {
            cir.setReturnValue(this.scaledDim.height);
        }
    }
}
