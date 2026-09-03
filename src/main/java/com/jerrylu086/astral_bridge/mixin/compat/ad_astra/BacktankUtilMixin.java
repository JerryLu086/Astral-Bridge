package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@RequiresModList({"ad_astra", "create"})
@Mixin(BacktankUtil.class)
public abstract class BacktankUtilMixin {
    @Inject(method = "getAir", at = @At("HEAD"), cancellable = true)
    private static void onGetAir(ItemStack backtank, CallbackInfoReturnable<Float> cir) {
        if (backtank.getItem() instanceof SpaceSuit spaceSuit) {
            cir.setReturnValue((float) (Math.min(spaceSuit.getAmount(backtank), spaceSuit.getTankSize()) / 81));
        }
    }

    @Inject(method = "consumeAir", at = @At("HEAD"), cancellable = true)
    private static void onConsumeAir(LivingEntity entity, ItemStack backtank, float i, CallbackInfo ci) {
        if (backtank.getItem() instanceof SpaceSuit spaceSuit) {
            SpaceSuit.consumeSpaceSuitOxygen(entity, (long) Math.ceil(i) * 81);
            ci.cancel();
        }
    }

    @Inject(method = "getBarWidth", at = @At(value = "RETURN", ordinal = 3), cancellable = true)
    private static void onGetBarWidth(ItemStack stack, int usesPerTank, CallbackInfoReturnable<Integer> cir, @Local List<ItemStack> backtanks) {
        if (backtanks.get(0).getItem() instanceof SpaceSuit spaceSuit) {
            cir.setReturnValue(Math.round(13.0F * Mth.clamp((float) spaceSuit.getAmount(backtanks.get(0)) / spaceSuit.getTankSize(), 0, 1)));
        }
    }

    @Inject(method = "getBarColor", at = @At(value = "RETURN", ordinal = 3), cancellable = true)
    private static void onGetBarColor(ItemStack stack, int usesPerTank, CallbackInfoReturnable<Integer> cir, @Local List<ItemStack> backtanks) {
        if (backtanks.get(0).getItem() instanceof SpaceSuit spaceSuit) {
            cir.setReturnValue(0x7FD4FF);
        }
    }
}
