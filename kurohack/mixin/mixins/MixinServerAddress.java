//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import kurohack.features.modules.misc.*;
import kurohack.mixin.mixins.accessors.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ServerAddress.class })
public abstract class MixinServerAddress
{
    @Redirect(method = { "fromString" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ServerAddress;getServerAddress(Ljava/lang/String;)[Ljava/lang/String;"))
    private static String[] getServerAddressHook(final String ip) {
        final ServerModule module;
        final int port;
        if (ip.equals(ServerModule.getInstance().ip.getValue()) && (port = (module = ServerModule.getInstance()).getPort()) != -1) {
            return new String[] { (String)ServerModule.getInstance().ip.getValue(), Integer.toString(port) };
        }
        return IServerAddress.getServerAddress(ip);
    }
}
