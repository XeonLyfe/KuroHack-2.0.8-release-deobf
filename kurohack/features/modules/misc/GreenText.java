//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class GreenText extends Module
{
    private final String suffix = ">";
    
    public GreenText() {
        super("GreenText", "Green text bypass", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage)event.getPacket()).getMessage();
            if (s.startsWith("/") || s.startsWith(">")) {
                return;
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            event.setCanceled(true);
            final CPacketChatMessage newpacket = new CPacketChatMessage(">" + s);
            GreenText.mc.player.connection.sendPacket((Packet)newpacket);
        }
    }
}
