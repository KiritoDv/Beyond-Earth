package com.st0x0ef.beyond_earth.common.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class AlienTradingRecipeItemStackBase extends AlienTradingRecipe {

	private final ItemStack costA;
	private final ItemStack costB;

	public AlienTradingRecipeItemStackBase(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.costA = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "costA"), true);
		this.costB = json.has("costB") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "costB"), true) : ItemStack.EMPTY;
	}

	public AlienTradingRecipeItemStackBase(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.costA = buffer.readItem();
		this.costB = buffer.readItem();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeItem(this.costA);
		buffer.writeItem(this.costB);
	}

	public ItemStack getCostA() {
		return this.costA.copy();
	}

	public ItemStack getCostB() {
		return this.costB.copy();
	}

}
