package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

import java.util.Set;

public class ExtraGlintsImpl implements ExtraGlintsAPI {
    @Override
    public ExtraGlint.Builder newGlint(Identifier id) {
        return null;
    }

    @Override
    public ExtraGlint.Builder newTemporaryGlint(Identifier id) {
        return null;
    }

    @Override
    public ExtraGlint getGlint(Identifier id) {
        return null;
    }

    @Override
    public void removeGlint(Identifier id) {

    }

    @Override
    public Set<Identifier> registeredGlints() {
        return null;
    }

    @Override
    public ExtraGlint vanillaGlint() {
        return null;
    }

    @Override
    public void apply(ExtraGlint... glints) {

    }
}
