package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.jerrylu086.astral_bridge.compat.ad_astra.LocationStateUtil;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@RequiresModList("ad_astra")
@Mixin(LaunchPad.class)
public abstract class LaunchPadFix extends Block {
    @Shadow @Final
    public static BooleanProperty WATERLOGGED;
    @Shadow @Final
    public static EnumProperty<LocationState> LOCATION;

    protected LaunchPadFix(Properties properties) {
        super(properties);
    }

    @Inject(method = "setPlacedBy", at = @At(value = "HEAD"), cancellable = true)
    public void onSetPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide && state.getValue(LOCATION) == LocationState.CENTER) {
            for (int x = 0; x < 3 ; x++) {
                for (int z = 0; z < 3; z++) {
                    BlockPos offset = pos.offset(x - 1, 0, z - 1);
                    if (offset.equals(pos))
                        continue;

                    level.setBlock(offset, state.setValue(LOCATION, LocationState.values()[x + z * 3])
                                                   .setValue(WATERLOGGED, level.getFluidState(offset).getType().equals(Fluids.WATER)), 3);
                }
            }
        }

        ci.cancel();
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void onCanSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canSurvive(state, level, pos));
    }

    @WrapOperation(method = "breakPad", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V", ordinal = 0))
    private void breakOnlyPad(List<BlockPos> instance, Consumer<BlockPos> action, Operation<Void> original, @Local Level world) {
        original.call(instance, (Consumer<BlockPos>) (blockPos) -> {
            if (world.getBlockState(blockPos).getBlock() instanceof LaunchPad)
                world.destroyBlock(blockPos, false);
        });
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).getType().equals(Fluids.WATER));
        BlockPos pos = context.getClickedPos();

        for (int x = 0; x < 3 ; x++) {
            for (int z = 0; z < 3; z++) {
                BlockPos offset = pos.offset(x - 1, 0, z - 1);
                if (offset.equals(pos))
                    continue;

                BlockState neighbor = context.getLevel().getBlockState(offset);
                if (!neighbor.getMaterial().isReplaceable())
                    return null;
            }
        }

        return state;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(LOCATION, LocationStateUtil.rotatePad(state.getValue(LOCATION), rotation, Axis.Y));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.setValue(LOCATION, LocationStateUtil.mirror(state.getValue(LOCATION),
                mirror == Mirror.LEFT_RIGHT ? Axis.Z : Axis.Y));
    }
}
