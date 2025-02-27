package com.st0x0ef.beyond_earth.client.events.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderHandItemEvent extends Event {
    private final LivingEntity livingEntity;
    private final ItemStack itemStack;
    private final ItemDisplayContext transformType;
    private final HumanoidArm handSide;
    private final PoseStack poseStack;
    private final MultiBufferSource renderTypeBuffer;
    private final int light;

    public RenderHandItemEvent(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
        this.livingEntity = livingEntity;
        this.itemStack = itemStack;
        this.transformType = transformType;
        this.handSide = handSide;
        this.poseStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.light = light;
    }

    public LivingEntity getLivingEntity() {
        return this.livingEntity;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemDisplayContext getTransformType() {
        return this.transformType;
    }

    public HumanoidArm getHandSide() {
        return this.handSide;
    }

    public PoseStack getMatrixStack() {
        return this.poseStack;
    }

    public MultiBufferSource getRenderTypeBuffer() {
        return this.renderTypeBuffer;
    }

    public int getLight() {
        return this.light;
    }

    @Cancelable
    public static class Pre extends RenderHandItemEvent {
        public Pre(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }

    public static class Post extends RenderHandItemEvent {
        public Post(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }
}
