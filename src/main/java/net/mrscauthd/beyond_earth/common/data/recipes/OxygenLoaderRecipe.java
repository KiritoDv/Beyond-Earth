package net.mrscauthd.beyond_earth.common.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.common.registries.RecipeSerializersRegistry;
import net.mrscauthd.beyond_earth.common.registries.RecipeTypeRegistry;

public class OxygenLoaderRecipe extends OxygenMakingRecipeAbstract {

	public OxygenLoaderRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public OxygenLoaderRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
	}

	public OxygenLoaderRecipe(ResourceLocation id, FluidIngredient ingredient, int oxygen) {
		super(id, ingredient, oxygen);
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
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_OXYGEN_LOADER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.OXYGEN_LOADING.get();
	}

}
