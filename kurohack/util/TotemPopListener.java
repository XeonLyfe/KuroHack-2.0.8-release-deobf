//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.util;

import kurohack.util.element.*;
import net.minecraftforge.common.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.event.events.*;
import kurohack.features.modules.player.*;
import java.util.*;

public class TotemPopListener implements IUtil
{
    public final Map<String, Integer> popMap;
    
    public TotemPopListener() {
        this.popMap = new HashMap<String, Integer>();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35) {
                final Entity entity = packet.getEntity((World)TotemPopListener.mc.world);
                if (entity instanceof EntityPlayer) {
                    if (entity.equals((Object)TotemPopListener.mc.player)) {
                        return;
                    }
                    final EntityPlayer player = (EntityPlayer)entity;
                    this.handlePop(player);
                }
            }
        }
    }
    
    public void handlePop(final EntityPlayer player) {
        if (!this.popMap.containsKey(player.getName())) {
            MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player, player.entityId));
            this.popMap.put(player.getName(), 1);
        }
        else {
            this.popMap.put(player.getName(), this.popMap.get(player.getName()) + 1);
            MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player, player.entityId));
        }
    }
    
    @SubscribeEvent
    public void onTick(final PlayerLivingUpdateEvent event) {
        for (final EntityPlayer player : TotemPopListener.mc.world.playerEntities) {
            if (player == FakePlayer.getInstance().fakePlayer) {
                continue;
            }
            if (player == TotemPopListener.mc.player || !this.popMap.containsKey(player.getName()) || (!player.isDead && player.isEntityAlive() && player.getHealth() > 0.0f)) {
                continue;
            }
            this.popMap.remove(player.getName());
        }
    }
}
