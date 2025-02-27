package com.st0x0ef.beyond_earth.common.data.recipes;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import com.st0x0ef.beyond_earth.common.registries.RecipeSerializersRegistry;
import com.st0x0ef.beyond_earth.common.registries.RecipeTypeRegistry;

public class GeneratingRecipe extends BeyondEarthRecipe implements Predicate<ItemStack> {

	public static final int SLOT_FUEL = 0;

	private final Ingredient input;
	private final int burnTime;

	public GeneratingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		this.input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
		this.burnTime = GsonHelper.getAsInt(json, "burnTime");
	}

	public GeneratingRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.input = Ingredient.fromNetwork(buffer);
		this.burnTime = buffer.readInt();
	}

	public GeneratingRecipe(ResourceLocation id, Ingredient ingredient, int burnTime) {
		super(id);
		this.input = ingredient;
		this.burnTime = burnTime;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		this.getInput().toNetwork(buffer);
		buffer.writeInt(this.getBurnTime());
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
	public boolean canCraftInDimensions(int var1, int var2) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess p_267052_) {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_COAL_GENERATOR.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.COAL_GENERATING.get();
	}

	public int getFuelSlot(Container container, Level level) {
		return SLOT_FUEL;
	}

	@Override
	public boolean test(ItemStack ingredient) {
		return this.input.test(ingredient);
	}

	public Ingredient getInput() {
		return this.input;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = super.getIngredients();
		list.add(this.getInput());
		return list;
	}

	public int getBurnTime() {
		return this.burnTime;
	}

}
