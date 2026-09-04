package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.jerrylu086.astral_bridge.compat.ad_astra.LocationStateUtil;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@RequiresModList("ad_astra")
@Mixin(SlidingDoorBlock.class)
public abstract class SlidingDoorBlockFix extends BaseEntityBlock {
    @Shadow @Final
    public static DirectionProperty FACING;
    @Shadow @Final
    public static EnumProperty<LocationState> LOCATION;

    protected SlidingDoorBlockFix(Properties properties) {
        super(properties);
    }

    @WrapOperation(method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/properties/EnumProperty;create(Ljava/lang/String;Ljava/lang/Class;)Lnet/minecraft/world/level/block/state/properties/EnumProperty;",
                    ordinal = 0))
    private static EnumProperty<LocationState> assignUniversalProperty(String name, Class<?> clazz, Operation<EnumProperty<?>> original) {
        return LocationStateUtil.LOCATION;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        state = state.rotate(mirror.getRotation(state.getValue(FACING)));
        return mirror == Mirror.NONE ? state : state.setValue(LOCATION,
                LocationStateUtil.mirror(state.getValue(LOCATION), Axis.Y));
    }
}
