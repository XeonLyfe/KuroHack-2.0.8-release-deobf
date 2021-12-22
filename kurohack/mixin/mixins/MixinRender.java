//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.culling.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Render.class })
public class MixinRender<T extends Entity>
{
    @Inject(method = { "shouldRender" }, at = { @At("HEAD") }, cancellable = true)
    public void shouldRender(final T livingEntity, final ICamera camera, final double camX, final double camY, final double camZ, final CallbackInfoReturnable<Boolean> info) {
        if (livingEntity == null || camera == null || livingEntity.getRenderBoundingBox() == null) {
            info.setReturnValue((Object)false);
        }
    }
}
