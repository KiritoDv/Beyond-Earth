package com.st0x0ef.beyond_earth.common.registries;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.st0x0ef.beyond_earth.BeyondEarth;
import com.st0x0ef.beyond_earth.common.effects.RadiationEffect;

public class MobEffectsRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BeyondEarth.MODID);

    public static final RegistryObject<MobEffect> RADIATION = MOB_EFFECTS.register("radiation_effect",
            () -> new RadiationEffect(MobEffectCategory.HARMFUL, 11184640));

}
