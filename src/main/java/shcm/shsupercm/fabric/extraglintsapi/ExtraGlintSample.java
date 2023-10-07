package shcm.shsupercm.fabric.extraglintsapi;

import io.shcm.shsupercm.fabric.fletchingtable.api.Entrypoint;
import net.minecraft.util.Identifier;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlint;
import shcm.shsupercm.fabric.extraglintsapi.api.v0.ExtraGlintsAPI;

public class ExtraGlintSample {
    public static ExtraGlint myGlint;

    @Entrypoint(Entrypoint.CLIENT)
    public static void clientInit() {
        myGlint = ExtraGlintsAPI.getInstance().newGlint(new Identifier("extraglintsapi", "sample"))
                .texture(new Identifier("extraglintsapi", "textures/misc/sample_glint.png"))
                .register();
    }
}
