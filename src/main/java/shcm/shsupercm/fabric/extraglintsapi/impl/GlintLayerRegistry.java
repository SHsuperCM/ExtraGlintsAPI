package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class GlintLayerRegistry {
    private static Map<RenderLayer, BufferBuilder> registeredLayers;
    private static Map<BufferBuilder, RenderLayer> vanillaLayersByBuilders = null;

    private static List<Runnable> early = new ArrayList<>();

    public static synchronized void loadLayers(Map<RenderLayer, BufferBuilder> vanillaLayers) {
        registeredLayers = vanillaLayers;

        vanillaLayersByBuilders = new IdentityHashMap<>();
        for (Map.Entry<RenderLayer, BufferBuilder> entry : vanillaLayers.entrySet())
            if (entry.getKey().name.contains("glint")) {
                vanillaLayersByBuilders.put(entry.getValue(), entry.getKey());
                ((ExtraGlintImpl) ExtraGlintsAPI.getInstance().vanillaGlint()).layers.put(entry.getKey(), entry.getKey());
            }

        early.forEach(Runnable::run);
        early = null;
    }

    public static synchronized void register(ExtraGlintImpl extraGlint) {
        if (vanillaLayersByBuilders == null) {
            early.add(() -> register(extraGlint));
            return;
        }

        for (RenderLayer vanillaLayer : vanillaLayersByBuilders.values()) {
            RenderLayer newLayer = extraGlint.copyOfLayer(vanillaLayer);
            registeredLayers.put(newLayer, new BufferBuilder(newLayer.getExpectedBufferSize()));
        }
    }

    public static synchronized void unregister(ExtraGlintImpl extraGlint) {
        for (RenderLayer layer : extraGlint.layers.values())
            registeredLayers.remove(layer);
    }

    public static RenderLayer getVanillaLayer(VertexConsumer vertexConsumer) {
        //noinspection SuspiciousMethodCalls
        return vanillaLayersByBuilders.get(vertexConsumer);
    }

    public static VertexConsumer getVertexConsumer(RenderLayer renderLayer) {
        return registeredLayers.get(renderLayer);
    }
}
