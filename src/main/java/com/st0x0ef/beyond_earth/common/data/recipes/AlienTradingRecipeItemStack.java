package com.st0x0ef.beyond_earth.common.data.recipes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import com.st0x0ef.beyond_earth.common.registries.RecipeSerializersRegistry;
import com.st0x0ef.beyond_earth.common.registries.RecipeTypeRegistry;

public class AlienTradingRecipeItemStack extends AlienTradingRecipeItemStackBase {

	private final ItemStack result;

	public AlienTradingRecipeItemStack(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
	}

	public AlienTradingRecipeItemStack(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.result = buffer.readItem();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeItem(this.result);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, RandomSource rand) {
		return Triple.of(this.getCostA(), this.getCostB(), this.getResult(trader, rand));
	}

	public ItemStack getResult(Entity trader, RandomSource rand) {
		return this.result.copy();
	}

	@Override
	public boolean matches(Container p_44002_, Level p_44003_) {
		return false;
	}

	@Override
	public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
		return null;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess p_267052_) {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_ALIEN_TRADING_ITEMSTACK.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.ALIEN_TRADING_ITEMSTACK.get();
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipeItemStack> {
		@Override
		public AlienTradingRecipeItemStack fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipeItemStack(id, json);
		}

		@Override
		public AlienTradingRecipeItemStack fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipeItemStack(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipeItemStack recipe) {
			recipe.write(buffer);
		}

	}

}
