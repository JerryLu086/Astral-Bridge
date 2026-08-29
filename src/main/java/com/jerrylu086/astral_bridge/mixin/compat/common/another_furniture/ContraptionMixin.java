package com.jerrylu086.astral_bridge.mixin.compat.common.another_furniture;

import com.jerrylu086.astral_bridge.mixin.AstralBridgePlugin.RequiredMod;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.*;

@RequiredMod({"another_furniture", "create"})
@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @Shadow
    private Map<BlockPos, Entity> initialPassengers;
    @Shadow
    abstract BlockPos toLocalPos(BlockPos globalPos);
    @Shadow
    abstract List<BlockPos> getSeats();

    // Passengers stay on seats when contraption is disassembled
    @Inject(
            method = "addPassengersToWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onAddPassengersToWorld(Level world, StructureTransform transform, List<Entity> seatedEntities, CallbackInfo ci,
                                        Iterator<Entity> entities, Entity seatedEntity, Integer seatIndex, BlockPos seatPos) {
        if (world.getBlockState(seatPos).getBlock() instanceof SeatBlock seatBlock &&
                !com.simibubi.create.content.contraptions.actors.seat.SeatBlock.isSeatOccupied(world, seatPos)) {
            SeatEntity seat = new SeatEntity(world, seatPos, seatBlock.seatHeight());
            world.addFreshEntity(seat);
            seatedEntity.startRiding(seat);
            if (seatedEntity instanceof TamableAnimal ta)
                ta.setInSittingPose(true);
        }
    }

    // Sadly, we can't access this. So if we want to add seats normally upon assembling, we have to do this ;-;
    @Inject(
            method = "moveBlock",
            // #L367 / before "if (state.getBlock() instanceof SeatBlock)"
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 16
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onMoveBlock(Level world, @Nullable Direction forcedDirection, Queue<BlockPos> frontier,
                             Set<BlockPos> visited, CallbackInfoReturnable<Boolean> cir, BlockPos pos,
                             BlockState state) throws AssemblyException {
        if (state.getBlock() instanceof SeatBlock)
            moveCompatSeat(world, pos);
    }

    @Unique
    private void moveCompatSeat(Level world, BlockPos pos) {
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
