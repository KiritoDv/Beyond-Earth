package com.st0x0ef.beyond_earth.client.renderers.entities.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.st0x0ef.beyond_earth.common.blocks.entities.GlobeTileEntity;

@OnlyIn(Dist.CLIENT)
public class GlobeItemRenderer<T extends GlobeTileEntity> extends BlockEntityWithoutLevelRenderer {

    private ResourceLocation texture;
    private GlobeModel<?> model;

    public GlobeItemRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    public GlobeItemRenderer<?> setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_108831_, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight, int p_108835_) {
        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5D, 1.5D, 0.5D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;

        if (this.model == null) {
            this.model = new GlobeModel<>(mc.getEntityModels().bakeLayer(GlobeModel.LAYER_LOCATION));
        }

        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(texture));

        /** Animation */
        if (level != null) {
            if (!mc.isPaused()) {
                model.globe.getChild("planet").yRot = (level.getGameTime() + mc.getFrameTime()) / -20;
            }
        }

        this.model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();
    }
}