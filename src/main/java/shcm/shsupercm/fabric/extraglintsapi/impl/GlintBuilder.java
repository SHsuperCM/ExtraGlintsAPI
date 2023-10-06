package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.GlintContext;

import java.util.function.Consumer;

public class GlintBuilder implements ExtraGlint.Builder {
    private final Identifier id;
    private final boolean temporary;
    private final Consumer<ExtraGlintImpl> registeredCallback;

    private Identifier textureItem, textureEntity;
    private float scaleItem, scaleEntity;
    private boolean blur, mipmap;
    private Consumer<GlintContext> before, after;

    public GlintBuilder(Identifier id, boolean temporary, Consumer<ExtraGlintImpl> registeredCallback) {
        this.id = id;
        this.temporary = temporary;
        this.registeredCallback = registeredCallback;
        this.scale(1f)
            .blur(true)
            .mipmap(false)
            .texture(null)
            .runs(ctx -> {}, ctx -> {});
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
    public GlintBuilder runs(Consumer<GlintContext> before, Consumer<GlintContext> after) {
        this.before = before;
        this.after = after;
        return this;
    }

    @Override
    public ExtraGlint register() {
        class TemporaryCounter {
            public static long tempNumber = 0;
        }
        ExtraGlintImpl glint = new ExtraGlintImpl(this.temporary ? this.id.withSuffixedPath("-" + TemporaryCounter.tempNumber++) : this.id, this.temporary, this.textureItem, this.textureEntity, this.scaleItem, this.scaleEntity, this.blur, this.mipmap, this.before, this.after);
        registeredCallback.accept(glint);
        return glint;
    }
}
