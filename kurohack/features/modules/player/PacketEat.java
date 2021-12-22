//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import kurohack.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PacketEat extends Module
{
    private final Setting<Mode> mode;
    private final Setting<Double> health;
    private final Setting<Double> packetSize;
    
    public PacketEat() {
        super("PacketEat", "", Module.Category.PLAYER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Packet));
        this.health = (Setting<Double>)this.register(new Setting("Health", (T)28.0, (T)0.0, (T)36.0));
        this.packetSize = (Setting<Double>)this.register(new Setting("PacketIteration", (T)20.0, (T)0.0, (T)40.0));
    }
    
    public void onUpdate() {
        if (PacketEat.mc.player.isHandActive() && PacketEat.mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && (this.mode.getValue() == Mode.Packet || this.mode.getValue() == Mode.Auto)) {
            for (int i = 0; i < this.packetSize.getValue(); ++i) {
                PacketEat.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
            }
            PacketEat.mc.player.stopActiveHand();
        }
        if (this.mode.getValue() == Mode.Auto && PlayerUtil.getHealth() <= this.health.getValue()) {
            InventoryUtil.switchToSlotGhost(InventoryUtil.getHotbarItemSlot(Items.GOLDEN_APPLE));
            PacketEat.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }
    }
    
    @SubscribeEvent
    public void onPlayerRightClick(final PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem().equals(Items.GOLDEN_APPLE) && this.mode.getValue() == Mode.Desync) {
            event.setCanceled(true);
            event.getItemStack().getItem().onItemUseFinish(event.getItemStack(), event.getWorld(), (EntityLivingBase)event.getEntityPlayer());
        }
    }
    
    public enum Mode
    {
        Packet, 
        Desync, 
        Auto;
    }
}
