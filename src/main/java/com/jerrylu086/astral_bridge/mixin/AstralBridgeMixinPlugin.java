package com.jerrylu086.astral_bridge.mixin;

import com.jerrylu086.astral_bridge.Util;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class AstralBridgeMixinPlugin implements IMixinConfigPlugin {
    private static final String COMPAT_PACKAGE = AstralBridgeMixinPlugin.class.getPackageName() + ".compat.";
    public static final Logger LOGGER = LoggerFactory.getLogger("Astral Bridge Mixin Logger");

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // This took me ages to put together and still managed to look god-awful, huh?
        if (mixinClassName.startsWith(COMPAT_PACKAGE)) {
            ClassNode clazz;
            try {
                clazz = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName);
                if (clazz.invisibleAnnotations != null) {
                    for (AnnotationNode ann : clazz.invisibleAnnotations) {
                        List<Object> members = ann.values;
                        if (ann.desc.equals(Type.getDescriptor(RequiresModList.class)) && members != null) {
                            for (int i = 0; i < members.size(); i += 2) {
                                if (members.get(i).equals("value") && members.get(i + 1) instanceof List<?> list && !list.isEmpty()) {
                                    // Nah don't pretend like you're not a list of Strings!
                                    return Util.checkLoaded((List<String>) list);
                                }
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                LOGGER.info("Skipping mixin that could not be loaded: {}", mixinClassName);
                return false;
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    // I can't believe I'm really doing this.
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.TYPE)
    public @interface RequiresModList {
        String[] value();
    }
}
