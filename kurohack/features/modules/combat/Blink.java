//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import java.util.*;
import net.minecraft.network.*;
import kurohack.features.setting.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import java.util.concurrent.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import kurohack.util.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Blink extends Module
{
    private static Blink INSTANCE;
    private final PhobosTimer timer;
    private final Queue<Packet<?>> packets;
    public Setting<Boolean> cPacketPlayer;
    public Setting<Mode> autoOff;
    public Setting<Integer> timeLimit;
    public Setting<Integer> packetLimit;
    public Setting<Float> distance;
    private EntityOtherPlayerMP entity;
    private int packetsCanceled;
    private BlockPos startPos;
    
    public Blink() {
        super("Blink", "Fakelag.", Category.COMBAT, true, false, false);
        this.timer = new PhobosTimer();
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
        this.cPacketPlayer = (Setting<Boolean>)this.register(new Setting("CPacketPlayer", (T)true));
        this.autoOff = (Setting<Mode>)this.register(new Setting("AutoOff", (T)Mode.MANUAL));
        this.timeLimit = (Setting<Integer>)this.register(new Setting("Time", (T)20, (T)1, (T)500, v -> this.autoOff.getValue() == Mode.TIME));
        this.packetLimit = (Setting<Integer>)this.register(new Setting("Packets", (T)20, (T)1, (T)500, v -> this.autoOff.getValue() == Mode.PACKETS));
        this.distance = (Setting<Float>)this.register(new Setting("Distance", (T)10.0f, (T)1.0f, (T)100.0f, v -> this.autoOff.getValue() == Mode.DISTANCE));
        this.setInstance();
    }
    
    public static Blink getInstance() {
        if (Blink.INSTANCE == null) {
            Blink.INSTANCE = new Blink();
        }
        return Blink.INSTANCE;
    }
    
    private void setInstance() {
        Blink.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (!fullNullCheck()) {
            (this.entity = new EntityOtherPlayerMP((World)Blink.mc.world, Blink.mc.session.getProfile())).copyLocationAndAnglesFrom((Entity)Blink.mc.player);
            this.entity.rotationYaw = Blink.mc.player.rotationYaw;
            this.entity.rotationYawHead = Blink.mc.player.rotationYawHead;
            this.entity.inventory.copyInventory(Blink.mc.player.inventory);
            Blink.mc.world.addEntityToWorld(6942069, (Entity)this.entity);
            this.startPos = Blink.mc.player.getPosition();
        }
        else {
            this.disable();
        }
        this.packetsCanceled = 0;
        this.timer.reset();
    }
    
    @Override
    public void onUpdate() {
        if (nullCheck() || (this.autoOff.getValue() == Mode.TIME && this.timer.passedS(this.timeLimit.getValue())) || (this.autoOff.getValue() == Mode.DISTANCE && this.startPos != null && Blink.mc.player.getDistanceSq(this.startPos) >= MathUtil.square(this.distance.getValue())) || (this.autoOff.getValue() == Mode.PACKETS && this.packetsCanceled >= this.packetLimit.getValue())) {
            this.disable();
        }
    }
    
    @Override
    public void onLogout() {
        if (this.isOn()) {
            this.disable();
        }
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getStage() == 0 && Blink.mc.world != null && !Blink.mc.isSingleplayer()) {
            final Object packet = event.getPacket();
            if (this.cPacketPlayer.getValue() && packet instanceof CPacketPlayer) {
                event.setCanceled(true);
                this.packets.add((Packet<?>)packet);
                ++this.packetsCanceled;
            }
            if (!this.cPacketPlayer.getValue()) {
                if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus) {
                    return;
                }
                this.packets.add((Packet<?>)packet);
                event.setCanceled(true);
                ++this.packetsCanceled;
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (!fullNullCheck()) {
            Blink.mc.world.removeEntity((Entity)this.entity);
            while (!this.packets.isEmpty()) {
                Blink.mc.player.connection.sendPacket((Packet)this.packets.poll());
            }
        }
        this.startPos = null;
    }
    
    static {
        Blink.INSTANCE = new Blink();
    }
    
    public enum Mode
    {
        MANUAL, 
        TIME, 
        DISTANCE, 
        PACKETS;
    }
}
