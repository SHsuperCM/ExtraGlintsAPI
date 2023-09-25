package shcm.shsupercm.fabric.extraglintsapi.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyExpressionValue(method = "/^get.+GlintConsumer$/", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"))
    private static VertexConsumer extraglintsapi$replaceConsumer(VertexConsumer original) {
        return ExtraGlintsAPI.getInstance().redirect(original);
    }
}
