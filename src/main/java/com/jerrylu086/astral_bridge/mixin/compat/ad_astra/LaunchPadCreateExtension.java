package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.jerrylu086.astral_bridge.compat.ad_astra.LocationStateUtil;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.content.contraptions.ITransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
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
public abstract class LaunchPadCreateExtension extends Block implements ISpecialBlockItemRequirement, ITransformableBlock {
    @Shadow
    @Final
    public static EnumProperty<LocationState> LOCATION;

    public LaunchPadCreateExtension(Properties properties) {
        super(properties);
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        if (state.getValue(LOCATION) == LocationState.CENTER)
            return new ItemRequirement(ItemUseType.CONSUME, this.asItem());

        return ItemRequirement.INVALID;
    }

    @Override
    public BlockState transform(BlockState state, StructureTransform transform) {
        if (transform.mirror != null) {
            state.mirror(transform.mirror);
        }

        return state.setValue(LOCATION, LocationStateUtil.rotatePad(state.getValue(LOCATION), transform.rotation, transform.rotationAxis));
    }
}
