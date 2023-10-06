package shcm.shsupercm.fabric.extraglintsapi.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.MultiPhaseParametersCloneBuilder;

@Mixin(RenderLayer.MultiPhaseParameters.class)
public class MultiPhaseParametersMixin implements MultiPhaseParametersCloneBuilder {
    @Shadow @Final private RenderPhase.TextureBase texture;
    @Shadow @Final private RenderPhase.ShaderProgram program;
    @Shadow @Final private RenderPhase.Transparency transparency;
    @Shadow @Final private RenderPhase.DepthTest depthTest;
    @Shadow @Final private RenderPhase.Cull cull;
    @Shadow @Final private RenderPhase.Lightmap lightmap;
    @Shadow @Final private RenderPhase.Overlay overlay;
    @Shadow @Final private RenderPhase.Layering layering;
    @Shadow @Final private RenderPhase.Target target;
    @Shadow @Final private RenderPhase.Texturing texturing;
    @Shadow @Final private RenderPhase.WriteMaskState writeMaskState;
    @Shadow @Final private RenderPhase.LineWidth lineWidth;
    @Shadow @Final private RenderPhase.ColorLogic colorLogic;

    @Override
    public RenderLayer.MultiPhaseParameters.Builder extraglintsapi$clone() {
        return RenderLayer.MultiPhaseParameters.builder()
                .texture(this.texture)
                .program(this.program)
                .transparency(this.transparency)
                .depthTest(this.depthTest)
                .cull(this.cull)
                .lightmap(this.lightmap)
                .overlay(this.overlay)
                .layering(this.layering)
                .target(this.target)
                .texturing(this.texturing)
                .writeMaskState(this.writeMaskState)
                .lineWidth(this.lineWidth)
                .colorLogic(this.colorLogic);
    }
}