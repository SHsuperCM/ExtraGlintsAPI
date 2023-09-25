package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtraGlintsAPIImpl implements ExtraGlintsAPI {
    private final ExtraGlint vanillaGlint = new GlintLayerRegistry.VanillaGlint();

    private final Map<Identifier, ExtraGlint> registeredGlints = new HashMap<>();
    private ExtraGlint[] activeGlints;
    private boolean notVanilla;

    {
        registeredGlints.put(vanillaGlint.id(), vanillaGlint);
        activeGlints = new ExtraGlint[] { vanillaGlint };
        notVanilla = false;
    }

    @Override
    public ExtraGlint.Builder newGlint(Identifier id) {
        return new GlintBuilder(id, false, newGlint -> registeredGlints.put(newGlint.id(), newGlint));
    }

    @Override
    public ExtraGlint.Builder newTemporaryGlint(Identifier id) {
        return new GlintBuilder(id, true, newGlint -> registeredGlints.put(newGlint.id(), newGlint));
    }

    @Override
    public ExtraGlint getGlint(Identifier id) {
        return this.registeredGlints.get(id);
    }

    @Override
    public void removeGlint(Identifier id) {
        ExtraGlint removed = this.registeredGlints.remove(id);
        if (removed != null && !((ExtraGlintImpl) removed).canBeRemoved())
            throw new RuntimeException("Tried to remove a non-temporary extra glint!");
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

        }

        return vertexConsumer;
    }
}
