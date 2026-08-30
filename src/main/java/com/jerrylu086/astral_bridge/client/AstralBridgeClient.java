package com.jerrylu086.astral_bridge.client;

import com.jerrylu086.astral_bridge.Util;
import com.jerrylu086.astral_bridge.client.compat.AdAstraCompatClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AstralBridgeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (Util.checkLoaded("ad_astra"))
            AdAstraCompatClient.register();
    }
}
