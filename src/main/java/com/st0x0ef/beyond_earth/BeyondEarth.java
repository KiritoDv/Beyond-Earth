package com.st0x0ef.beyond_earth;

import com.st0x0ef.beyond_earth.common.config.ClientConfig;
import com.st0x0ef.beyond_earth.common.config.Config;
import com.st0x0ef.beyond_earth.common.events.BlockRenderLayerEvent;
import com.st0x0ef.beyond_earth.common.registries.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BeyondEarth.MODID)
public class BeyondEarth {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "beyond_earth";

	public BeyondEarth() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		/** CONFIG REGISTRIES */
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "beyond_earth-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "beyond_earth-client.toml");

		/** NETWORK REGISTRIES */
		NetworkRegistry.register();

		/** DEFAULT REGISTRIES */
		ItemsRegistry.ITEMS.register(bus);
		BlockRegistry.BLOCKS.register(bus);
		FluidTypeRegistry.FLUID_TYPES.register(bus);
		FluidRegistry.FLUIDS.register(bus);
		EntityRegistry.ENTITIES.register(bus);
		BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
		PaintingRegistry.PAINTINGS.register(bus);
		SensorRegistry.SENSOR.register(bus);
		RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(bus);
		RecipeTypeRegistry.RECIPE_TYPES.register(bus);
		RocketPartsRegistry.ROCKET_PARTS.register(bus);
		SoundRegistry.SOUNDS.register(bus);
		ParticleRegistry.PARTICLES.register(bus);
		ContainerRegistry.CONTAINERS.register(bus);
		StructureRegistry.STRUCTURES.register(bus);
		FeatureRegistry.FEATURES.register(bus);
		TabsRegistry.CREATIVE_MOD_TAB.register(bus);
		MobEffectsRegistry.MOB_EFFECTS.register(bus);

		bus.addListener(BlockRenderLayerEvent::renderLayerEvent);

		MinecraftForge.EVENT_BUS.register(this);

		LOGGER.info("Beyond Earth Loaded Successfully!");
	}
}
