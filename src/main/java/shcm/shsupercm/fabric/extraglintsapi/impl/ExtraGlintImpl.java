package shcm.shsupercm.fabric.extraglintsapi.impl;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.GlintContext;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.MultiPhaseParametersCloneBuilder;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ExtraGlintImpl implements ExtraGlint {
    private final Identifier id;
    private final boolean canBeRemoved;
    private final Identifier textureItem;
    private final Identifier textureEntity;
    private final float scaleItem, scaleEntity;
    private final boolean blur, mipmap;
    private final Consumer<GlintContext> before, after;

    protected final Map<RenderLayer, RenderLayer> layers = new IdentityHashMap<>();

    public ExtraGlintImpl(Identifier id, boolean canBeRemoved, Identifier textureItem, Identifier textureEntity, float scaleItem, float scaleEntity, boolean blur, boolean mipmap, Consumer<GlintContext> before, Consumer<GlintContext> after) {
        this.id = id;
        this.canBeRemoved = canBeRemoved;
        this.textureItem = textureItem;
        this.textureEntity = textureEntity;
        this.scaleItem = scaleItem;
        this.scaleEntity = scaleEntity;
        this.blur = blur;
        this.mipmap = mipmap;
        this.before = before;
        this.after = after;
    }

    public RenderLayer copyOfLayer(RenderLayer originalLayer) {
        if (originalLayer instanceof RenderLayer.MultiPhase multiPhase) {
            RenderLayer newLayer = RenderLayer.of(multiPhase.name + ":" + id.toString(), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, ((MultiPhaseParametersCloneBuilder) (Object) multiPhase.getPhases()).extraglintsapi$clone()

                    .build(multiPhase.isOutline()));

            this.layers.put(originalLayer, newLayer);
            return newLayer;
        } else throw new IllegalArgumentException("Can only copy multiphase layers.");
    }

    @Override
    public Identifier id() {
        return this.id;
    }

    public boolean canBeRemoved() {
        return this.canBeRemoved;
    }

    @Override
    public Identifier textureItem() {
        return this.textureItem;
    }

    @Override
    public Identifier textureEntity() {
        return this.textureEntity;
    }

    @Override
    public float scaleItem() {
        return this.scaleItem;
    }

    @Override
    public float scaleEntity() {
        return this.scaleEntity;
    }

    @Override
    public boolean blur() {
        return this.blur;
    }

    @Override
    public boolean mipmap() {
        return this.mipmap;
    }

    @Override
    public void before() {
        this.before.accept(() -> this);
    }

    @Override
    public void after() {
        this.after.accept(() -> this);
    }
}
