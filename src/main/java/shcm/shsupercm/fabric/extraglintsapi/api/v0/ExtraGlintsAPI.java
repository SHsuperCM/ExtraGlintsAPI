package shcm.shsupercm.fabric.extraglintsapi.api.v0;

import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.impl.ExtraGlintsImpl;

import java.util.Set;

public interface ExtraGlintsAPI {
    static ExtraGlintsAPI getInstance() {
        class Static {
            public static final ExtraGlintsAPI INSTANCE = new ExtraGlintsImpl();
        }
        return Static.INSTANCE;
    }

    /**
     * Starts the setup for a new glint.
     * The built glint will be a constant part of the game.
     * @see #newTemporaryGlint(Identifier)
     * @param id identifier for the glint to reference it later
     * @return a new glint builder
     */
    ExtraGlint.Builder newGlint(Identifier id);

    /**
     * Starts the setup for a new temporary glint.
     * This builder should be used for glints that are created dynamically during runtime.
     * @see #newGlint(Identifier)
     * @param id identifier for the glint to reference it later
     * @return a new glint builder
     */
    ExtraGlint.Builder newTemporaryGlint(Identifier id);

    /**
     * @return a registered glint with the associated identifier
     */
    ExtraGlint getGlint(Identifier id);

    /**
     * Removes the registered glint with the associated identifier
     */
    void removeGlint(Identifier id);

    /**
     * @return a list of all currently registered glint identifiers
     */
    Set<Identifier> registeredGlints();

    /**
     * @return the glint in vanilla as an {@link ExtraGlint}
     */
    ExtraGlint vanillaGlint();

    /**
     * Applies the given glints.
     * @see #wrap(Runnable, ExtraGlint...)
     * @param glints glints to apply. If empty, will clear all glints entirely.
     */
    void apply(ExtraGlint... glints);

    /**
     * Same as {@link #apply(ExtraGlint...)} but safely wraps a render task.
     * @see #apply(ExtraGlint...)
     * @param render a function to run with the given glints applied
     * @param glints glints to apply. If empty, will clear all glints entirely.
     */
    default void wrap(Runnable render, ExtraGlint... glints) {
        apply(glints);
        try {
            render.run();
        } finally {
            apply(vanillaGlint());
        }
    }
}
