//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.util.element.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class InventoryManager implements IUtil
{
    public Map<String, List<ItemStack>> inventories;
    private int recoverySlot;
    
    public InventoryManager() {
        this.inventories = new HashMap<String, List<ItemStack>>();
        this.recoverySlot = -1;
    }
    
    public void update() {
        if (this.recoverySlot != -1) {
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange((this.recoverySlot == 8) ? 7 : (this.recoverySlot + 1)));
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.recoverySlot));
            InventoryManager.mc.player.inventory.currentItem = this.recoverySlot;
            InventoryManager.mc.playerController.syncCurrentPlayItem();
            this.recoverySlot = -1;
        }
    }
    
    public void recoverSilent(final int slot) {
        this.recoverySlot = slot;
    }
}
