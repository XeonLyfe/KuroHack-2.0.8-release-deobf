//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.*;
import net.minecraft.util.text.*;
import net.minecraft.network.*;
import kurohack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import kurohack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoLog extends Module
{
    private static AutoLog INSTANCE;
    private final Setting<Float> health;
    private final Setting<Boolean> bed;
    private final Setting<Float> range;
    private final Setting<Boolean> logout;
    
    public AutoLog() {
        super("AutoLog", "Logs when in danger.", Category.MISC, false, false, false);
        this.health = (Setting<Float>)this.register(new Setting("Health", (T)16.0f, (T)0.1f, (T)36.0f));
        this.bed = (Setting<Boolean>)this.register(new Setting("Speed", "Beds", 0.0, 0.0, (T)true, 0));
        this.range = (Setting<Float>)this.register(new Setting("BedRange", (T)6.0f, (T)0.1f, (T)36.0f, v -> this.bed.getValue()));
        this.logout = (Setting<Boolean>)this.register(new Setting("Speed", "LogoutOff", 0.0, 0.0, (T)true, 0));
        this.setInstance();
    }
    
    public static AutoLog getInstance() {
        if (AutoLog.INSTANCE == null) {
            AutoLog.INSTANCE = new AutoLog();
        }
        return AutoLog.INSTANCE;
    }
    
    private void setInstance() {
        AutoLog.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (!nullCheck() && AutoLog.mc.player.getHealth() <= this.health.getValue()) {
            KuroHack.moduleManager.disableModule("AutoReconnect");
            AutoLog.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
            if (this.logout.getValue()) {
                this.disable();
            }
        }
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        final SPacketBlockChange packet;
        if (event.getPacket() instanceof SPacketBlockChange && this.bed.getValue() && (packet = (SPacketBlockChange)event.getPacket()).getBlockState().getBlock() == Blocks.BED && AutoLog.mc.player.getDistanceSqToCenter(packet.getBlockPosition()) <= MathUtil.square(this.range.getValue())) {
            KuroHack.moduleManager.disableModule("AutoReconnect");
            AutoLog.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
            if (this.logout.getValue()) {
                this.disable();
            }
        }
    }
    
    static {
        AutoLog.INSTANCE = new AutoLog();
    }
}
