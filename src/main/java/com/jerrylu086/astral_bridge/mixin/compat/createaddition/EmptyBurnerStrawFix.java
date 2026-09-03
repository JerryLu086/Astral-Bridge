package com.jerrylu086.astral_bridge.mixin.compat.createaddition;

import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mrh0.createaddition.event.GameEvents;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@RequiresModList({"create", "createaddition"})
@Mixin(GameEvents.class)
public abstract class EmptyBurnerStrawFix {
    @Inject(method = "interact",
            at = @At(value = "FIELD",
                     target = "Lcom/mrh0/createaddition/index/CABlocks;LIQUID_BLAZE_BURNER:Lcom/tterrag/registrate/util/entry/BlockEntry;",
                     opcode = Opcodes.GETSTATIC,
                     ordinal = 0),
            cancellable = true)
    private static void checkIfEmpty(Player player, Level world, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir, @Local BlockState state) {
        if (state.getValue(BlazeBurnerBlock.HEAT_LEVEL) == HeatLevel.NONE)
            cir.setReturnValue(InteractionResult.PASS);
    }

    @WrapOperation(method = "interact",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V",
                     ordinal = 0))
    private static void checkIfCreative(ItemStack instance, int decrement, Operation<Void> original, @Local Player player) {
        if (!player.isCreative())
            original.call(instance, decrement);
    }
}
