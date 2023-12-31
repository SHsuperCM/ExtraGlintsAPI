package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtraGlintsAPIImpl implements ExtraGlintsAPI {
    private final ExtraGlintImpl vanillaGlint = new ExtraGlintImpl(new Identifier("minecraft", "glint"),
            false,
            ItemRenderer.ITEM_ENCHANTMENT_GLINT,
            ItemRenderer.ENTITY_ENCHANTMENT_GLINT,
            8f,
            0.16f,
            true,
            false,
            ctx -> {},
            ctx -> {});

    private final Map<Identifier, ExtraGlintImpl> registeredGlints = new HashMap<>(Map.of(vanillaGlint.id(), vanillaGlint));
    private ExtraGlint[] activeGlints = new ExtraGlint[] { vanillaGlint };
    private boolean notVanilla = false;

    @Override
    public ExtraGlint.Builder newGlint(Identifier id) {
        return new GlintBuilder(id, false, newGlint -> {
            GlintLayerRegistry.register(newGlint);
            registeredGlints.put(newGlint.id(), newGlint);
        });
    }

    @Override
    public ExtraGlint.Builder newTemporaryGlint(Identifier id) {
        return new GlintBuilder(id, true, newGlint -> {
            GlintLayerRegistry.register(newGlint);
            registeredGlints.put(newGlint.id(), newGlint);
        });
    }

    @Override
    public ExtraGlint getGlint(Identifier id) {
        return this.registeredGlints.get(id);
    }

    @Override
    public void removeGlint(Identifier id) {
        ExtraGlintImpl removed = this.registeredGlints.remove(id);
        if (removed != null) {
            if (!removed.canBeRemoved())
                throw new RuntimeException("Tried to remove a non-temporary extra glint!");

            GlintLayerRegistry.unregister(removed);
            removed.layers.clear();
        }
    }

    @Override
    public Set<Identifier> registeredGlints() {
        return this.registeredGlints.keySet();
    }

    @Override
    public ExtraGlint vanillaGlint() {
        return this.vanillaGlint;
    }

    @Override
    public void apply(ExtraGlint... glints) {
        this.activeGlints = glints;
        this.notVanilla = glints.length != 1 || glints[0] != this.vanillaGlint;
    }

    @Override
    public VertexConsumer redirect(VertexConsumer vertexConsumer) {
        RenderLayer vanillaLayer = GlintLayerRegistry.getVanillaLayer(vertexConsumer);
        if (notVanilla && vanillaLayer != null)
            return switch (activeGlints.length) {
                case 0 -> EmptyVertexConsumer.INSTANCE;
                case 1 -> GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[0]).layers.get(vanillaLayer));
                case 2 -> VertexConsumers.union(
                        GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[0]).layers.get(vanillaLayer)),
                        GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[1]).layers.get(vanillaLayer))
                );
                default -> {
                    VertexConsumer[] consumers = new VertexConsumer[activeGlints.length];
                    for (int i = 0; i < activeGlints.length; i++)
                        consumers[i] = GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[i]).layers.get(vanillaLayer));
                    yield VertexConsumers.union(consumers);
                }
            };

        return vertexConsumer;
    }
}
