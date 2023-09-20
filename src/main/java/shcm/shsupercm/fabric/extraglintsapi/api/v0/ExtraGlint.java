package shcm.shsupercm.fabric.extraglintsapi.api.v0;

import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public interface ExtraGlint {
    Identifier id();

    Identifier textureItem();

    Identifier textureEntity();

    float scaleItem();

    float scaleEntity();

    boolean blur();

    boolean mipmap();

    void before();

    void after();

    interface Builder {
        Builder texture(Identifier item, Identifier entity);

        default Builder texture(Identifier texture) {
            texture(texture, texture);
            return this;
        }

        Builder scale(float item, float entity);

        default Builder scale(float scale) {
            scale(8.0f * scale, 0.16f * scale);
            return this;
        }

        Builder blur(boolean blur);

        Builder mipmap(boolean mipmap);

        Builder runs(Consumer<ExtraGlint> before, Consumer<ExtraGlint> after);

        ExtraGlint register();
    }
}
