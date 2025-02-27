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
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.FuelRefineryBlockEntity;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeValueHelper;
import com.st0x0ef.beyond_earth.common.menus.FuelRefineryMenu;
import com.st0x0ef.beyond_earth.common.util.Rectangle2d;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class FuelRefineryScreen extends AbstractContainerScreen<FuelRefineryMenu.GuiContainer> {

    public static final ResourceLocation texture = new ResourceLocation(BeyondEarth.MODID,
            "textures/gui/fuel_refinery.png");

    public static final int INPUT_TANK_LEFT = 43;
    public static final int INPUT_TANK_TOP = 22;

    public static final int OUTPUT_TANK_LEFT = 100;
    public static final int OUTPUT_TANK_TOP = 22;

    public static final int ENERGY_LEFT = 150;
    public static final int ENERGY_TOP = 22;

    public static final int ARROW_LEFT = 48;
    public static final int ARROW_TOP = 36;

    public FuelRefineryScreen(FuelRefineryMenu.GuiContainer container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 177;
        this.imageHeight = 184;
        this.inventoryLabelY = this.imageHeight - 92;
    }
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);

        FuelRefineryBlockEntity blockEntity = this.getMenu().getBlockEntity();

        if (GuiHelper.isHover(this.getInputTankBounds(), mouseX, mouseY)) {
            this.setTooltipForNextRenderPass(GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getInputTank())).build());
            this.renderTooltip(graphics,
                    mouseX, mouseY);
        } else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
            this.setTooltipForNextRenderPass(GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getOutputTank())).build());
            this.renderTooltip(graphics,
                    mouseX, mouseY);
        } else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
            this.setTooltipForNextRenderPass(GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(blockEntity)).build());
            this.renderTooltip(graphics,
                    mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        graphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth,
                this.imageHeight);

        FuelRefineryBlockEntity blockEntity = this.getMenu().getBlockEntity();
        GuiHelper.drawEnergy(graphics, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP,
                Objects.requireNonNull(blockEntity.getPrimaryEnergyStorage()));
        GuiHelper.drawFluidTank(graphics, this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP,
                blockEntity.getInputTank());
        GuiHelper.drawFluidTank(graphics, this.leftPos + OUTPUT_TANK_LEFT, this.topPos + OUTPUT_TANK_TOP,
                blockEntity.getOutputTank());
    }

    public Rectangle2d getInputTankBounds() {
        return GuiHelper.getFluidTankBounds(this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP);
    }

    public Rectangle2d getOutputTankBounds() {
        return GuiHelper.getFluidTankBounds(this.leftPos + OUTPUT_TANK_LEFT, this.topPos + OUTPUT_TANK_TOP);
    }

    public Rectangle2d getEnergyBounds() {
        return GuiHelper.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }
}