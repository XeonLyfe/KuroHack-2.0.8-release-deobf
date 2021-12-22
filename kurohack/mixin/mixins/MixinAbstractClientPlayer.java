//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import kurohack.features.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer
{
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationSkin(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if ((boolean)Chams.getInstance().textured.getValue() && Chams.getInstance().isEnabled()) {
            callbackInfoReturnable.setReturnValue((Object)new ResourceLocation("textures/shinechams3.png"));
        }
    }
}
