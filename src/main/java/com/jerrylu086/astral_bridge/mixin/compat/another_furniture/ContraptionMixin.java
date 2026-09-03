package com.jerrylu086.astral_bridge.mixin.compat.another_furniture;

import com.jerrylu086.astral_bridge.mixin.AstralBridgeMixinPlugin.RequiresModList;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.entity.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.*;

@RequiresModList({"another_furniture", "create"})
@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @Shadow
    private Map<BlockPos, Entity> initialPassengers;
    @Shadow
    abstract BlockPos toLocalPos(BlockPos globalPos);
    @Shadow
    abstract List<BlockPos> getSeats();

    @Inject(method = "moveBlock", at = @At(value = "JUMP", opcode = Opcodes.IFEQ, ordinal = 16))
    private void onMoveBlock(Level world, @Nullable Direction forcedDirection, Queue<BlockPos> frontier,
                             Set<BlockPos> visited, CallbackInfoReturnable<Boolean> cir, @Local BlockPos pos,
                             @Local BlockState state) throws AssemblyException {
        if (state.getBlock() instanceof SeatBlock)
            moveAFSeat(world, pos);
    }

    @Inject(method = "addPassengersToWorld", at = @At(value = "JUMP", opcode = Opcodes.IFNE, ordinal = 0))
    private void onAddPassengersToWorld(Level world, StructureTransform transform, List<Entity> seatedEntities, CallbackInfo ci,
                                      @Local Entity seatedEntity, @Local BlockPos seatPos) {
        if (!(world.isClientSide) && world.getBlockState(seatPos).getBlock() instanceof SeatBlock seatBlock &&
                !com.simibubi.create.content.contraptions.actors.seat.SeatBlock.isSeatOccupied(world, seatPos)) {

            SeatEntity seat = new SeatEntity(world, seatPos, seatBlock.seatHeight());
            world.addFreshEntity(seat);
            seatedEntity.startRiding(seat);

            if (seatedEntity instanceof TamableAnimal ta)
                ta.setInSittingPose(true);
        }
    }

    @Unique
    private void moveAFSeat(Level world, BlockPos pos) {
        BlockPos local = toLocalPos(pos);
        getSeats().add(local);
        List<SeatEntity> seatsEntities = world.getEntitiesOfClass(SeatEntity.class, new AABB(pos));
        if (!seatsEntities.isEmpty()) {
            SeatEntity seat = seatsEntities.get(0);
            List<Entity> passengers = seat.getPassengers();
            if (!passengers.isEmpty())
                initialPassengers.put(local, passengers.get(0));
        }
    }
}
