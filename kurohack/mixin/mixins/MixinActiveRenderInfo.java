//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import kurohack.util.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ActiveRenderInfo.class })
public class MixinActiveRenderInfo
{
    @Inject(method = { "updateRenderInfo(Lnet/minecraft/entity/Entity;Z)V" }, at = { @At("HEAD") }, remap = false)
    private static void updateRenderInfo(final Entity entity, final boolean wtf, final CallbackInfo ci) {
        RenderUtil.updateModelViewProjectionMatrix();
    }
}
