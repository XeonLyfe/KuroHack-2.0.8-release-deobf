//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ModelPlayer.class })
public class MixinModelPlayer
{
    @Redirect(method = { "renderCape" }, at = @At("HEAD"))
    public void renderCape(final float scale) {
    }
}
