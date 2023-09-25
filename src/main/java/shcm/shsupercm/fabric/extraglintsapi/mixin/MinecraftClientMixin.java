package shcm.shsupercm.fabric.extraglintsapi.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.render.BufferBuilderStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shcm.shsupercm.fabric.extraglintsapi.impl.GlintLayerRegistry;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow public abstract BufferBuilderStorage getBufferBuilders();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void extraglintsapi$registerLayers(RunArgs args, CallbackInfo ci) {
        GlintLayerRegistry.loadLayers(((BufferBuilderStorageAccessor) this.getBufferBuilders()).getEntityBuilders());
    }
}
