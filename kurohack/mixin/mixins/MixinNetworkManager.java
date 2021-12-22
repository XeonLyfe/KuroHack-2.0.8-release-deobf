//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import kurohack.event.events.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Send event = new PacketEvent.Send(0, (Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") }, cancellable = true)
    private void onSendPacketPost(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Send event = new PacketEvent.Send(1, (Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Receive event = new PacketEvent.Receive(0, (Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        final EventNetworkPacketEvent l_Event = new EventNetworkPacketEvent((Packet)p_Packet);
        MinecraftForge.EVENT_BUS.post((Event)l_Event);
        if (l_Event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelRead(final ChannelHandlerContext context, final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        final EventNetworkPacketEvent l_Event = new EventNetworkPacketEvent((Packet)p_Packet);
        MinecraftForge.EVENT_BUS.post((Event)l_Event);
        if (l_Event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
