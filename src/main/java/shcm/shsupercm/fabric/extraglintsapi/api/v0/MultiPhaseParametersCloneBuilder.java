package shcm.shsupercm.fabric.extraglintsapi.api.v0;

import net.minecraft.client.render.RenderLayer;

/**
 * Implemented on {@link RenderLayer.MultiPhaseParameters} and allows cloning the parameters into a new builder.
 */
public interface MultiPhaseParametersCloneBuilder {
    /**
     * Implemented on {@link RenderLayer.MultiPhaseParameters}
     * @return a builder with initial parameters copied from this
     */
    RenderLayer.MultiPhaseParameters.Builder extraglintsapi$clone();
}
