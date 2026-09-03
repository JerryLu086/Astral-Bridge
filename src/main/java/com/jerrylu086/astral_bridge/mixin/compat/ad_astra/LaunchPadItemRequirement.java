package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement.ItemUseType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@RequiresModList({"ad_astra", "create"})
@Mixin(LaunchPad.class)
public abstract class LaunchPadItemRequirement extends Block implements ISpecialBlockItemRequirement {
    @Shadow
    @Final
    public static EnumProperty<LocationState> LOCATION;

    public LaunchPadItemRequirement(Properties properties) {
        super(properties);
    }

    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        if (state.getValue(LOCATION) == LocationState.CENTER)
            return new ItemRequirement(ItemUseType.CONSUME, this.asItem());

        return ItemRequirement.NONE;
    }
}
