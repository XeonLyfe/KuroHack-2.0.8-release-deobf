//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import kurohack.features.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ VisGraph.class })
public class MixinVisGraph
{
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCubeHook(final BlockPos pos, final CallbackInfo info) {
        try {
            if (XRay.getInstance().isOn()) {
                info.cancel();
            }
        }
        catch (Exception ex) {}
    }
}
