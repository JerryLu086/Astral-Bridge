package com.jerrylu086.astral_bridge.mixin;

import com.jerrylu086.astral_bridge.AstralBridge;
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
        ModResourcePack target = null;
        for (int i = 0; i < packs.toArray().length; i++) {
            ModResourcePack pack = packs.get(i);
            String id = pack.getFabricModMetadata().getId();
            if (id.equals("ad_astra")) {
                if (target != null) {
                    AstralBridge.LOGGER.info("Found AA pack at: {}, now removing and re-adding AB.", i);
                    packs.remove(pack);
                    packs.add(i + 1, pack);
                } else {
                    AstralBridge.LOGGER.info("Found AA pack at: {} before AB, now breaking the loop.", i);
                }
                break;
            }
            if (id.equals("astral_bridge")) {
                AstralBridge.LOGGER.info("Found AB pack at: {}, now targeted.", i);
                target = pack;
            }
        }
    }
}
