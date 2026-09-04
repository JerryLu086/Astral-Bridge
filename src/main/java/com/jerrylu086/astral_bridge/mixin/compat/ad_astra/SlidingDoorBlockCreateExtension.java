package com.jerrylu086.astral_bridge.mixin.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.simibubi.create.content.schematics.requirement.ISpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement.ItemUseType;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@RequiresModList({"ad_astra", "create"})
@Mixin(SlidingDoorBlock.class)
public abstract class SlidingDoorBlockCreateExtension extends BaseEntityBlock implements ISpecialBlockItemRequirement {
    @Shadow @Final
    public static DirectionProperty FACING;
    @Shadow @Final
    public static EnumProperty<LocationState> LOCATION;

    protected SlidingDoorBlockCreateExtension(Properties properties) {
        super(properties);
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity blockEntity) {
        if (state.getValue(LOCATION) == LocationState.BOTTOM)
            return new ItemRequirement(ItemUseType.CONSUME, this.asItem());

        return ItemRequirement.INVALID;
    }
}
