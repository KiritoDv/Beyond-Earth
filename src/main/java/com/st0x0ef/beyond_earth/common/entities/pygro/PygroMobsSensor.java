package com.st0x0ef.beyond_earth.common.entities.pygro;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;

public class PygroMobsSensor extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_REPELLENT);
    }

    @Override
    protected void doTick(ServerLevel worldIn, LivingEntity entityIn) {
        Brain<?> brain = entityIn.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent(worldIn, entityIn));
        Optional<Mob> optional = Optional.empty();
        Optional<Hoglin> optional1 = Optional.empty();
        Optional<Hoglin> optional2 = Optional.empty();
        Optional<Piglin> optional3 = Optional.empty();
        Optional<LivingEntity> optional4 = Optional.empty();
        Optional<Player> optional5 = Optional.empty();
        Optional<Player> optional6 = Optional.empty();
        int i = 0;
        List<AbstractPiglin> list = Lists.newArrayList();
        List<AbstractPiglin> list1 = Lists.newArrayList();
        NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());

        for(LivingEntity livingentity : nearestvisiblelivingentities.findAll((p_186157_) -> true)) {
            if (livingentity instanceof Hoglin hoglinentity) {
                if (hoglinentity.isBaby() && optional2.isEmpty()) {
                    optional2 = Optional.of(hoglinentity);
                } else if (hoglinentity.isAdult()) {
                    ++i;
                    if (optional1.isEmpty() && hoglinentity.canBeHunted()) {
                        optional1 = Optional.of(hoglinentity);
                    }
                }
            } else if (livingentity instanceof PiglinBrute) {
                list.add((PiglinBrute)livingentity);
            } else if (livingentity instanceof Piglin piglinentity) {
                if (piglinentity.isBaby() && optional3.isEmpty()) {
                    optional3 = Optional.of(piglinentity);
                } else if (piglinentity.isAdult()) {
                    list.add(piglinentity);
                }
            } else if (livingentity instanceof Player playerentity) {
                if (optional6.isEmpty() && !playerentity.isSpectator() && PiglinAi.isPlayerHoldingLovedItem(playerentity)) {
                    optional6 = Optional.of(playerentity);
                }
            } else if (optional.isPresent() || !(livingentity instanceof WitherSkeleton) && !(livingentity instanceof WitherBoss)) {
                if (optional4.isEmpty() && PiglinAi.isZombified(livingentity.getType())) {
                    optional4 = Optional.of(livingentity);
                }
            } else {
                optional = Optional.of((Mob)livingentity);
            }
        }

        for(LivingEntity livingentity1 : brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of())) {
            if (livingentity1 instanceof AbstractPiglin && ((AbstractPiglin)livingentity1).isAdult()) {
                list1.add((AbstractPiglin)livingentity1);
            }
        }

        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, optional1);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, optional2);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional4);
        brain.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, optional5);
        brain.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, optional6);
        brain.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, list1);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, list);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, list.size());
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, i);
    }

    private static Optional<BlockPos> findNearestRepellent(ServerLevel world, LivingEntity livingEntity) {
        return BlockPos.findClosestMatch(new BlockPos((int)livingEntity.getX(), (int)livingEntity.getY(), (int)livingEntity.getZ()), 8, 4, (pos) -> isRepellent(world, pos));
    }

    private static boolean isRepellent(ServerLevel world, BlockPos pos) {
        return false;
    }
}