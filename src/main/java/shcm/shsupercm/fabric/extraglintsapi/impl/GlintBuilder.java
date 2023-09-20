package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;

import java.util.function.Consumer;

public class GlintBuilder implements ExtraGlint.Builder {
    private final Identifier id;
    private final boolean canBeRemoved;
    private final Consumer<ExtraGlint> registeredCallback;

    private Identifier textureItem, textureEntity;
    private float scaleItem, scaleEntity;
    private boolean blur, mipmap;
    private Consumer<ExtraGlint> before, after;

    public GlintBuilder(Identifier id, boolean canBeRemoved, Consumer<ExtraGlint> registeredCallback) {
        this.id = id;
        this.canBeRemoved = canBeRemoved;
        this.registeredCallback = registeredCallback;
    }

    @Override
    public GlintBuilder texture(Identifier item, Identifier entity) {
        this.textureItem = item;
        this.textureEntity = entity;
        return this;
    }

    @Override
    public GlintBuilder scale(float item, float entity) {
        this.scaleItem = item;
        this.scaleEntity = entity;
        return this;
    }

    @Override
    public GlintBuilder blur(boolean blur) {
        this.blur = blur;
        return this;
    }

    @Override
    public GlintBuilder mipmap(boolean mipmap) {
        this.mipmap = mipmap;
        return this;
    }

    @Override
    public GlintBuilder runs(Consumer<ExtraGlint> before, Consumer<ExtraGlint> after) {
        this.before = before;
        this.after = after;
        return this;
    }

    @Override
    public ExtraGlint register() {
        ExtraGlintImpl glint = new ExtraGlintImpl(this.id, this.canBeRemoved, this.textureItem, this.textureEntity, this.scaleItem, this.scaleEntity, this.blur, this.mipmap, this.before, this.after);

        registeredCallback.accept(glint);
        return glint;
    }
}
