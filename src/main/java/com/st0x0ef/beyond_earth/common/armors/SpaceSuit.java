package com.st0x0ef.beyond_earth.common.armors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import com.st0x0ef.beyond_earth.BeyondEarth;
import com.st0x0ef.beyond_earth.client.renderers.armors.SpaceSuitModel;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class SpaceSuit {

	public static class Helmet extends ISpaceArmor.Helmet {
		public Helmet(ArmorMaterial armorMaterial, Type equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of("head", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).head,

							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

                    return new SpaceSuitModel.SpaceSuitP1<>(modelPart, livingEntity, itemStack);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/space_suit.png";
		}
	}

	public static class Suit extends ISpaceArmor.Chestplate {

		public Suit(ArmorMaterial armorMaterial, Type equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"body", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).body,
							"right_arm", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).rightArm,
							"left_arm", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).leftArm,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

                    return new SpaceSuitModel.SpaceSuitP1<>(modelPart, livingEntity, itemStack);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/space_suit.png";
		}

		@Override
		public int getOxygenCapacity() {
			return 36000;
		}
	}

	public static class Pants extends ISpaceArmor.Leggings {
		public Pants(ArmorMaterial armorMaterial, Type equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SpaceSuitP2<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP2.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SpaceSuitP2<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP2.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

                    return new SpaceSuitModel.SpaceSuitP2<>(modelPart, livingEntity, itemStack);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/space_pants.png";
		}

		@Override
		public int getFuelCapacity() {
			return 500;
		}
	}

	public static class Boots extends ISpaceArmor.Boots {
		public Boots(ArmorMaterial armorMaterial, Type equipmentSlot, Properties properties) {
			super(armorMaterial, equipmentSlot, properties);
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {

				@Override
				public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SpaceSuitP1<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SpaceSuitP1.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

                    return new SpaceSuitModel.SpaceSuitP1<>(modelPart, livingEntity, itemStack);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarth.MODID + ":textures/armor/space_suit.png";
		}
	}
}