//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Spinner extends Module
{
    private Setting<Float> speed;
    
    public Spinner() {
        super("Spinner", "Go burrrr", Module.Category.PLAYER, true, false, false);
        this.speed = (Setting<Float>)this.register(new Setting("Rotate Speed", (T)10.0f, (T)1.0f, (T)30.0f));
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer;
            final CPacketPlayer p = cPacketPlayer = (CPacketPlayer)event.getPacket();
            cPacketPlayer.yaw += this.speed.getValue();
        }
    }
}
