package com.jerrylu086.astral_bridge.mixin.compat.common.createaddition;

import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerTileEntity;
import com.mrh0.createaddition.index.CAItems;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@RequiresModList({"create", "createaddition"})
@Mixin(LiquidBlazeBurnerTileEntity.class)
public abstract class SchematicannonStrawFix extends SmartBlockEntity {
    public SchematicannonStrawFix(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state) {
        return new ItemRequirement(ItemRequirement.ItemUseType.CONSUME, CAItems.STRAW.get());
    }
}
