package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;

import java.util.IdentityHashMap;
import java.util.Map;

public class GlintLayerRegistry {
    private static Map<RenderLayer, BufferBuilder> registeredLayers;
    private static final Map<BufferBuilder, RenderLayer> vanillaLayersByBuilders = new IdentityHashMap<>();

    public static void loadLayers(Map<RenderLayer, BufferBuilder> vanillaLayers) {
        registeredLayers = vanillaLayers;
        for (Map.Entry<RenderLayer, BufferBuilder> entry : vanillaLayers.entrySet()) {
            if (entry.getKey().name.contains("glint")) {
                vanillaLayersByBuilders.put(entry.getValue(), entry.getKey());
            }
        }
    }

    public static ExtraGlintImpl register(ExtraGlintImpl extraGlint) {
        for (RenderLayer vanillaLayer : vanillaLayersByBuilders.values()) {
            RenderLayer newLayer = extraGlint.copyOfLayer(vanillaLayer);
            registeredLayers.put(newLayer, new BufferBuilder(newLayer.getExpectedBufferSize()));
        }
        return extraGlint;
    }

    public static void unregister(ExtraGlintImpl extraGlint) {
        for (RenderLayer layer : extraGlint.layers.values())
            registeredLayers.remove(layer);
    }

    public static class VanillaGlint extends ExtraGlintImpl {
        protected VanillaGlint() {
            super(new Identifier("minecraft", "glint"),
                  false,
                  ItemRenderer.ITEM_ENCHANTMENT_GLINT,
                  ItemRenderer.ENTITY_ENCHANTMENT_GLINT,
                  8f,
                  0.16f,
                  true,
                  false,
                  ctx -> {},
                  ctx -> {});

            for (RenderLayer layer : vanillaLayersByBuilders.values())
                this.layers.put(layer, layer);
        }
    }
}
