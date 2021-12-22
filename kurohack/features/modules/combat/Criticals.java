//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.util.*;
import kurohack.event.events.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Criticals extends Module
{
    public Setting<Boolean> noDesync;
    public Setting<Boolean> cancelFirst;
    public Setting<Integer> delay32k;
    private final Setting<Mode> mode;
    private final Setting<Integer> packets;
    private final Setting<Integer> desyncDelay;
    private final Timer timer;
    private final Timer timer32k;
    private boolean firstCanceled;
    private boolean resetTimer;
    
    public Criticals() {
        super("Criticals", "Scores criticals for you", Category.COMBAT, true, false, false);
        this.noDesync = (Setting<Boolean>)this.register(new Setting("Speed", "NoDesync", 0.0, 0.0, (T)true, 0));
        this.cancelFirst = (Setting<Boolean>)this.register(new Setting("Speed", "CancelFirst32k", 0.0, 0.0, (T)true, 0));
        this.delay32k = (Setting<Integer>)this.register(new Setting("32kDelay", (T)25, (T)0, (T)500, v -> this.cancelFirst.getValue()));
        this.mode = (Setting<Mode>)this.register(new Setting("Speed", "Mode", 0.0, 0.0, (T)Mode.PACKET, 0));
        this.packets = (Setting<Integer>)this.register(new Setting("Packets", (T)2, (T)1, (T)5, v -> this.mode.getValue() == Mode.PACKET, "Amount of packets you want to send."));
        this.desyncDelay = (Setting<Integer>)this.register(new Setting("DesyncDelay", (T)10, (T)0, (T)500, v -> this.mode.getValue() == Mode.PACKET, "Amount of packets you want to send."));
        this.timer = new Timer();
        this.timer32k = new Timer();
        this.firstCanceled = false;
        this.resetTimer = false;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            if (this.firstCanceled) {
                this.timer32k.reset();
                this.resetTimer = true;
                this.timer.setMs(this.desyncDelay.getValue() + 1);
                this.firstCanceled = false;
                return;
            }
            if (this.resetTimer && !this.timer32k.passedMs(this.delay32k.getValue())) {
                return;
            }
            if (this.resetTimer && this.timer32k.passedMs(this.delay32k.getValue())) {
                this.resetTimer = false;
            }
            if (!this.timer.passedMs(this.desyncDelay.getValue())) {
                return;
            }
            if (Criticals.mc.player.onGround && !Criticals.mc.gameSettings.keyBindJump.isKeyDown() && (packet.getEntityFromWorld((World)Criticals.mc.world) instanceof EntityLivingBase || !this.noDesync.getValue()) && !Criticals.mc.player.isInWater() && !Criticals.mc.player.isInLava()) {
                if (this.mode.getValue() == Mode.PACKET) {
                    switch (this.packets.getValue()) {
                        case 1: {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.10000000149011612, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            break;
                        }
                        case 2: {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625101, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 1.1E-5, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            break;
                        }
                        case 3: {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625101, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0125, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            break;
                        }
                        case 4: {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.05, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.03, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            break;
                        }
                        case 5: {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1625, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 4.0E-6, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 1.0E-6, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
                            Criticals.mc.player.onCriticalHit((Entity)Objects.requireNonNull(packet.getEntityFromWorld((World)Criticals.mc.world)));
                            break;
                        }
                    }
                }
                else if (this.mode.getValue() == Mode.BYPASS) {
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.11, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1100013579, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1100013579, Criticals.mc.player.posZ, false));
                }
                else {
                    Criticals.mc.player.jump();
                    if (this.mode.getValue() == Mode.MINIJUMP) {
                        final EntityPlayerSP player = Criticals.mc.player;
                        player.motionY /= 2.0;
                    }
                }
                this.timer.reset();
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    public enum Mode
    {
        JUMP, 
        MINIJUMP, 
        PACKET, 
        BYPASS;
    }
}
