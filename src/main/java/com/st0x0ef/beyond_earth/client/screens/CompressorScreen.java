package com.st0x0ef.beyond_earth.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.st0x0ef.beyond_earth.BeyondEarth;
import com.st0x0ef.beyond_earth.client.util.GuiHelper;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.CompressorBlockEntity;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeValueHelper;
import com.st0x0ef.beyond_earth.common.menus.CompressorMenu;
import com.st0x0ef.beyond_earth.common.util.Rectangle2d;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class CompressorScreen extends AbstractContainerScreen<CompressorMenu.GuiContainer> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondEarth.MODID,
            "textures/gui/compressor.png");

    public static final int ENERGY_LEFT = 147;
    public static final int ENERGY_TOP = 30;
    public static final int ARROW_LEFT = 65;
    public static final int ARROW_TOP = 61;

    public CompressorScreen(CompressorMenu.GuiContainer container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 177;
        this.imageHeight = 196;
        this.inventoryLabelY = this.imageHeight - 92;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);

        if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
            this.setTooltipForNextRenderPass(GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(this.getMenu().getBlockEntity())).build());
            this.renderTooltip(graphics, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth,
                this.imageHeight);

        CompressorBlockEntity blockEntity = this.getMenu().getBlockEntity();
        GuiHelper.drawEnergy(graphics, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP,
                Objects.requireNonNull(blockEntity.getPrimaryEnergyStorage()));
        GuiHelper.drawHammer(graphics, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, blockEntity.getTimerRatio());
    }

    public Rectangle2d getEnergyBounds() {
        return GuiHelper.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }
}
