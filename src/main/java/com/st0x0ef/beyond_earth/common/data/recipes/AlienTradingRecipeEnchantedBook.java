package com.st0x0ef.beyond_earth.common.data.recipes;

import java.util.List;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import com.st0x0ef.beyond_earth.common.registries.RecipeSerializersRegistry;
import com.st0x0ef.beyond_earth.common.registries.RecipeTypeRegistry;

public class AlienTradingRecipeEnchantedBook extends AlienTradingRecipeItemStackBase {

	private int costBaseLevelMultiplier = 3;
	private int costRangeBase = 5;
	private int costRangeLevelMultiplier = 10;
	private int costTreasureOnlyMultiplier = 2;

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.costBaseLevelMultiplier = GsonHelper.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = GsonHelper.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = GsonHelper.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);
		this.costTreasureOnlyMultiplier = GsonHelper.getAsInt(json, "costTreasureOnlyMultiplier", this.costTreasureOnlyMultiplier);
	}

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.costBaseLevelMultiplier = buffer.readInt();
		this.costRangeBase = buffer.readInt();
		this.costRangeLevelMultiplier = buffer.readInt();
		this.costTreasureOnlyMultiplier = buffer.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeInt(this.costBaseLevelMultiplier);
		buffer.writeInt(this.costRangeBase);
		buffer.writeInt(this.costRangeLevelMultiplier);
		buffer.writeInt(this.costTreasureOnlyMultiplier);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, RandomSource rand) {
		List<Enchantment> list = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(Enchantment::isTradeable).toList();
		Enchantment enchantment = list.get(rand.nextInt(list.size()));
		int level = Mth.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());

		ItemStack costA = this.getCostA();
		ItemStack costB = this.getCostB();
		int cost = costA.getCount() + (level * this.getCostBaseLevelMultiplier());
		int bound = this.getCostRangeBase() + (level * this.getCostRangeLevelMultiplier());

		if (bound > 0) {
			cost += rand.nextInt(bound);
		}

		if (enchantment.isTreasureOnly()) {
			cost *= this.getCostTreasureOnlyMultiplier();
		}

		costA.setCount(Math.min(cost, costA.getMaxStackSize()));
		ItemStack itemstack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
		return Triple.of(costA, costB, itemstack);
	}

	public int getCostBaseLevelMultiplier() {
		return this.costBaseLevelMultiplier;
	}

	public int getCostRangeBase() {
		return this.costRangeBase;
	}

	public int getCostRangeLevelMultiplier() {
		return this.costRangeLevelMultiplier;
	}

	public int getCostTreasureOnlyMultiplier() {
		return this.costTreasureOnlyMultiplier;
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
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTED_BOOK.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.ALIEN_TRADING_ENCHANTED_BOOK.get();
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipeEnchantedBook> {
		@Override
		public AlienTradingRecipeEnchantedBook fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipeEnchantedBook(id, json);
		}

		@Override
		public AlienTradingRecipeEnchantedBook fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipeEnchantedBook(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipeEnchantedBook recipe) {
			recipe.write(buffer);
		}

	}

}
