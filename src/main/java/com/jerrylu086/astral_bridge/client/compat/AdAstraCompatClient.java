package com.jerrylu086.astral_bridge.client.compat;

import com.jerrylu086.astral_bridge.compat.ad_astra.AdAstraCompatEntityTypes;
import com.jerrylu086.astral_bridge.compat.ad_astra.LaunchPadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AdAstraCompatClient {
    public static void register() {
        EntityRendererRegistry.register(AdAstraCompatEntityTypes.LAUNCH_PAD, LaunchPadEntity.Render::new);
    }
}
