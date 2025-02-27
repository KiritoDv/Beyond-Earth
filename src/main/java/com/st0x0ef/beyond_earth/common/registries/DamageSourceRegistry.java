package com.st0x0ef.beyond_earth.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import com.st0x0ef.beyond_earth.BeyondEarth;

public class DamageSourceRegistry {
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_OXYGEN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondEarth.MODID, "oxygen"));
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_ACID_RAIN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondEarth.MODID, "venus_acid"));
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_RADIATIONS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondEarth.MODID, "radiations"));

    public static DamageSource of(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
