package com.jerrylu086.astral_bridge.compat.ad_astra;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LaunchPadEntity extends Entity {
    public LaunchPadEntity(EntityType<LaunchPadEntity> entityType, Level level) {
        super(entityType, level);
    }

    public LaunchPadEntity(Level level, BlockPos pos) {
        super(AdAstraCompatEntityTypes.LAUNCH_PAD, level);
        noPhysics = true;
    }

    @Override
    public void tick() {
        if (level.isClientSide)
            return;
        BlockState state = level.getBlockState(blockPosition());
        boolean isCenter = state.getBlock() instanceof LaunchPad
                        && state.hasProperty(LaunchPad.LOCATION)
                        && state.getValue(LaunchPad.LOCATION) == LocationState.CENTER;
        if (isVehicle() && isCenter)
            return;
        this.discard();
    }

    @Override
    protected boolean canRide(Entity entity) {
        return entity instanceof VehicleEntity;
    }

    public static class Render extends EntityRenderer<LaunchPadEntity> {
        public Render(Context context) {
            super(context);
        }

        @Override
        public boolean shouldRender(LaunchPadEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
            return false;
        }

        @Override
        public ResourceLocation getTextureLocation(LaunchPadEntity entity) {
            return null;
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    public Packet<?> getAddEntityPacket()  {
        return new ClientboundAddEntityPacket(this);
    }
}
