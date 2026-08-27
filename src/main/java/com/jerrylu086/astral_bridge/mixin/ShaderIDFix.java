package com.jerrylu086.astral_bridge.mixin;

import com.jerrylu086.astral_bridge.AstralBridge;
import com.mojang.blaze3d.shaders.Program;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(value = ShaderInstance.class, priority = 5000)
public abstract class ShaderIDFix {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V", ordinal = 0))
    private String fixId(String path) {
        String separator = String.valueOf(ResourceLocation.NAMESPACE_SEPARATOR);
        if (path.contains(separator)) {
            String[] separated = path.split(separator);
            int length = separated.length;

            if (length > 2) {
                String result = separated[length - 2] + separator + separated[length - 1];
                AstralBridge.LOGGER.info("Shader ID Fixer (<init>): Successfully modified shader ID \"" + path + "\" into \"" + result + "\"");
                return result;
            }
        }

        return path;
    }


    @ModifyVariable(method = "getOrCreate", at = @At("STORE"), ordinal = 1)
    private static String fixPath(String path) {
        String separator = String.valueOf(ResourceLocation.NAMESPACE_SEPARATOR);
        if (path.contains(separator)) {
            String[] separated = path.split(separator);
            int length = separated.length;

            if (length > 2) {
                String result = separated[length - 2] + separator + separated[length - 1];
                AstralBridge.LOGGER.info("Shader ID Fixer (getOrCreate): Successfully modified shader ID \"" + path + "\" into \"" + result + "\"");
                return result;
            }
        }

        return path;
    }
}