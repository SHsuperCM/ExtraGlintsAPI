package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.mixin.RenderPhaseAccessor;

import java.util.IdentityHashMap;
import java.util.Map;

public class GlintLayerRegistry {
    private static final Map<BufferBuilder, RenderLayer> vanillaLayersByBuilders = new IdentityHashMap<>();

    public static void loadLayers(Map<RenderLayer, BufferBuilder> vanillaLayers) {
        for (Map.Entry<RenderLayer, BufferBuilder> entry : vanillaLayers.entrySet()) {
            if (((RenderPhaseAccessor) entry.getKey()).getName().contains("glint")) {
                vanillaLayersByBuilders.put(entry.getValue(), entry.getKey());
            }
        }


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
        }
    }
}
