//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import java.util.*;
import net.minecraft.network.*;
import java.util.concurrent.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.util.*;

public class PingSpoofer extends Module
{
    private final Setting<Integer> delay;
    private final Queue<Packet<?>> packets;
    private final PhobosTimer timer;
    private boolean receive;
    
    public PingSpoofer() {
        super("PingSpoofer", "Makes it look like you have higher ping than you really do.", Category.MISC, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("DelayMS", (T)20, (T)0, (T)1000));
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
        this.timer = new PhobosTimer();
        this.receive = true;
    }
    
    @Override
    public void onUpdate() {
        this.clearQueue();
    }
    
    @Override
    public void onDisable() {
        this.clearQueue();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.receive && PingSpoofer.mc.player != null && !PingSpoofer.mc.isSingleplayer() && PingSpoofer.mc.player.isEntityAlive() && event.getStage() == 0 && event.getPacket() instanceof CPacketKeepAlive) {
            this.packets.add((Packet<?>)event.getPacket());
            event.setCanceled(true);
        }
    }
    
    public void clearQueue() {
        if (PingSpoofer.mc.player != null && !PingSpoofer.mc.isSingleplayer() && PingSpoofer.mc.player.isEntityAlive() && this.timer.passedMs(this.delay.getCurrentState())) {
            final double limit = MathUtil.getIncremental(Math.random() * 10.0, 1.0);
            this.receive = false;
            for (int i = 0; i < limit; ++i) {
                final Packet<?> packet = this.packets.poll();
                if (packet != null) {
                    PingSpoofer.mc.player.connection.sendPacket((Packet)packet);
                }
            }
            this.timer.reset();
            this.receive = true;
        }
    }
}
