package net.mrscauthd.beyond_earth.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.mrscauthd.beyond_earth.common.registries.MobEffectsRegistry;
import net.mrscauthd.beyond_earth.common.util.Methods;

public class UraniumBlockItem extends BlockItem {
    public UraniumBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            if(!Methods.isLivingInJetSuit(player)) {
                player.addEffect(new MobEffectInstance(MobEffectsRegistry.RADIATION.get(), 50), player);
            }
        }
    }

}
