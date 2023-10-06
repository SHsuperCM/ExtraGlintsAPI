package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtraGlintsAPIImpl implements ExtraGlintsAPI {
    private final ExtraGlintImpl vanillaGlint = new GlintLayerRegistry.VanillaGlint();

    private final Map<Identifier, ExtraGlintImpl> registeredGlints = new HashMap<>();
    private ExtraGlint[] activeGlints;
    private boolean notVanilla;

    {
        registeredGlints.put(vanillaGlint.id(), vanillaGlint);
        activeGlints = new ExtraGlint[] { vanillaGlint };
        notVanilla = false;
    }

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
        if (notVanilla) {
            if (activeGlints.length == 0)
                return EmptyVertexConsumer.INSTANCE;

            RenderLayer vanillaLayer = GlintLayerRegistry.getVanillaLayer(vertexConsumer);

            if (activeGlints.length == 1)
                return GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[0]).layers.get(vanillaLayer));

            VertexConsumer[] consumers = new VertexConsumer[activeGlints.length];

            for (int i = 0; i < activeGlints.length; i++)
                consumers[i] = GlintLayerRegistry.getVertexConsumer(((ExtraGlintImpl) activeGlints[i]).layers.get(vanillaLayer));

            return consumers.length == 2 ? VertexConsumers.union(consumers[0], consumers[1]) : VertexConsumers.union(consumers);
        }

        return vertexConsumer;
    }
}
