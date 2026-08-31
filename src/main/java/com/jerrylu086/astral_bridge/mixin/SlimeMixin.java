package com.jerrylu086.astral_bridge.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {
    protected SlimeMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract int getSize();

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isLeashed() && getSize() <= 1;
    }
}