package com.st0x0ef.beyond_earth.common.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class GeneratingRecipeSerializer extends BeyondEarthRecipeSerializer<GeneratingRecipe> {

	@Override
	public GeneratingRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new GeneratingRecipe(id, json);
	}

	@Override
	public GeneratingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new GeneratingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, GeneratingRecipe recipe) {
		recipe.write(buffer);
	}

}
