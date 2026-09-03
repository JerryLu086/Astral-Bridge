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
    private static void onAppend(List<ModResourcePack> packs, PackType type, @Nullable String subPath, CallbackInfo ci) {
        int target = -1;
        for (int i = 0; i < packs.toArray().length; i++) {
            ModResourcePack pack = packs.get(i);
            String id = pack.getFabricModMetadata().getId();
            if (id.equals("ad_astra")) {
                if (target == -1)
                    break;

                packs.remove(pack);
                packs.add(i, pack);
            }
            if (id.equals("astral_bridge")) {
                target = i;
            }
        }
    }
}
