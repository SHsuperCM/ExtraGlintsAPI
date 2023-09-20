package shcm.shsupercm.fabric.extraglintsapi.api.v0;

import net.minecraft.util.Identifier;

public interface ExtraGlint {
    Identifier id();

    Identifier textureItem();

    Identifier textureEntity();

    interface Builder {
        void texture(Identifier item, Identifier entity);

        default void texture(Identifier texture) {
            texture(texture, texture);
        }

        ExtraGlint register();
    }
}
