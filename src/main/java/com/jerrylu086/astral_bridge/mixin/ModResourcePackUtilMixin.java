package com.jerrylu086.astral_bridge.mixin;

import net.fabricmc.fabric.api.resource.ModResourcePack;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModResourcePackUtil.class)
public abstract class ModResourcePackUtilMixin {
    @Inject(method = "appendModResourcePacks", at = @At("TAIL"))
    private static void onAppendModResourcePacks(List<ModResourcePack> packs, PackType type, @Nullable String subPath, CallbackInfo ci) {

    }
}
