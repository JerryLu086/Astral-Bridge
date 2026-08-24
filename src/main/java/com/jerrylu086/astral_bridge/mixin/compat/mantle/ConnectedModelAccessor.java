package com.jerrylu086.astral_bridge.mixin.compat.mantle;

import io.github.fabricators_of_create.porting_lib.model.ModelProperty;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import slimeknights.mantle.client.model.connected.ConnectedModel;

import java.util.Set;
import java.util.function.BiPredicate;

@Mixin(ConnectedModel.class)
public interface ConnectedModelAccessor {
    @Accessor("CONNECTIONS")
    static ModelProperty<Byte> canIGetAConnection() {
        throw new AssertionError();
    }

    @Accessor
    BiPredicate<BlockState,BlockState> getConnectionPredicate();

    @Accessor
    Set<Direction> getSides();
}
