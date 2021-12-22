//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import java.util.*;
import kurohack.features.setting.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.function.*;
import kurohack.util.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class AutoTotem extends Module
{
    private static AutoTotem instance;
    private final Queue<InventoryUtil.Task> taskList;
    private final Timer timer;
    private final Timer secondTimer;
    public Setting<Boolean> crystal;
    public Setting<Float> crystalHealth;
    public Setting<Float> crystalHoleHealth;
    public Setting<Boolean> gapple;
    public Setting<Boolean> armorCheck;
    public Setting<Integer> actions;
    public Mode2 currentMode;
    public int totems;
    public int crystals;
    public int gapples;
    public int lastTotemSlot;
    public int lastGappleSlot;
    public int lastCrystalSlot;
    public int lastObbySlot;
    public int lastWebSlot;
    public boolean holdingCrystal;
    public boolean holdingTotem;
    public boolean holdingGapple;
    public boolean didSwitchThisTick;
    private boolean second;
    private boolean switchedForHealthReason;
    
    public AutoTotem() {
        super("OffhandPlus", "Allows you to switch up your Offhand.", Category.COMBAT, true, false, false);
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.timer = new Timer();
        this.secondTimer = new Timer();
        this.crystal = (Setting<Boolean>)this.register(new Setting("Crystal", (T)true));
        this.crystalHealth = (Setting<Float>)this.register(new Setting("CrystalHP", (T)14.0f, (T)0.1f, (T)36.0f));
        this.crystalHoleHealth = (Setting<Float>)this.register(new Setting("CrystalHoleHP", (T)3.5f, (T)0.1f, (T)36.0f));
        this.gapple = (Setting<Boolean>)this.register(new Setting("Gapple", (T)true));
        this.armorCheck = (Setting<Boolean>)this.register(new Setting("ArmorCheck", (T)false));
        this.actions = (Setting<Integer>)this.register(new Setting("Actions", (T)4, (T)1, (T)4));
        this.currentMode = Mode2.TOTEMS;
        this.lastTotemSlot = -1;
        this.lastGappleSlot = -1;
        this.lastCrystalSlot = -1;
        this.lastObbySlot = -1;
        this.lastWebSlot = -1;
        AutoTotem.instance = this;
    }
    
    public static AutoTotem getInstance() {
        if (AutoTotem.instance == null) {
            AutoTotem.instance = new AutoTotem();
        }
        return AutoTotem.instance;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final ProcessRightClickBlockEvent event) {
        if (event.hand == EnumHand.MAIN_HAND && event.stack.getItem() == Items.END_CRYSTAL && AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && AutoTotem.mc.objectMouseOver != null && event.pos == AutoTotem.mc.objectMouseOver.getBlockPos()) {
            event.setCanceled(true);
            AutoTotem.mc.player.setActiveHand(EnumHand.OFF_HAND);
            AutoTotem.mc.playerController.processRightClick((EntityPlayer)AutoTotem.mc.player, (World)AutoTotem.mc.world, EnumHand.OFF_HAND);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(50L)) {
            if (AutoTotem.mc.player != null && AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && AutoTotem.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && Mouse.isButtonDown(1)) {
                AutoTotem.mc.player.setActiveHand(EnumHand.OFF_HAND);
                AutoTotem.mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
            }
        }
        else if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && AutoTotem.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            AutoTotem.mc.gameSettings.keyBindUseItem.pressed = false;
        }
        if (nullCheck()) {
            return;
        }
        this.doOffhand();
        if (this.secondTimer.passedMs(50L) && this.second) {
            this.second = false;
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (!fullNullCheck() && AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && AutoTotem.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && AutoTotem.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (packet2.getHand() == EnumHand.MAIN_HAND) {
                    if (this.timer.passedMs(50L)) {
                        AutoTotem.mc.player.setActiveHand(EnumHand.OFF_HAND);
                        AutoTotem.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    }
                    event.setCanceled(true);
                }
            }
            else if (event.getPacket() instanceof CPacketPlayerTryUseItem && ((CPacketPlayerTryUseItem)event.getPacket()).getHand() == EnumHand.OFF_HAND && !this.timer.passedMs(50L)) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            return "Crystals";
        }
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return "Totems";
        }
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            return "Gapples";
        }
        return null;
    }
    
    public void doOffhand() {
        this.didSwitchThisTick = false;
        this.holdingCrystal = (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.holdingTotem = (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING);
        this.holdingGapple = (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE);
        this.totems = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (this.holdingTotem) {
            this.totems += AutoTotem.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        }
        this.crystals = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (this.holdingCrystal) {
            this.crystals += AutoTotem.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        }
        this.gapples = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (this.holdingGapple) {
            this.gapples += AutoTotem.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        }
        this.doSwitch();
    }
    
    public void doSwitch() {
        this.currentMode = Mode2.TOTEMS;
        if (this.gapple.getValue() && AutoTotem.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && AutoTotem.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            this.currentMode = Mode2.GAPPLES;
        }
        else if (this.currentMode != Mode2.CRYSTALS && this.crystal.getValue() && ((EntityUtil.isSafe((Entity)AutoTotem.mc.player) && EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) > this.crystalHoleHealth.getValue()) || EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) > this.crystalHealth.getValue())) {
            this.currentMode = Mode2.CRYSTALS;
        }
        if (this.currentMode == Mode2.CRYSTALS && this.crystals == 0) {
            this.setMode(Mode2.TOTEMS);
        }
        if (this.currentMode == Mode2.CRYSTALS && ((!EntityUtil.isSafe((Entity)AutoTotem.mc.player) && EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) <= this.crystalHealth.getValue()) || EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) <= this.crystalHoleHealth.getValue())) {
            if (this.currentMode == Mode2.CRYSTALS) {
                this.switchedForHealthReason = true;
            }
            this.setMode(Mode2.TOTEMS);
        }
        if (this.switchedForHealthReason && ((EntityUtil.isSafe((Entity)AutoTotem.mc.player) && EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) > this.crystalHoleHealth.getValue()) || EntityUtil.getHealth((Entity)AutoTotem.mc.player, true) > this.crystalHealth.getValue())) {
            this.setMode(Mode2.CRYSTALS);
            this.switchedForHealthReason = false;
        }
        if (this.currentMode == Mode2.CRYSTALS && this.armorCheck.getValue() && (AutoTotem.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.AIR || AutoTotem.mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.AIR || AutoTotem.mc.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.AIR || AutoTotem.mc.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.AIR)) {
            this.setMode(Mode2.TOTEMS);
        }
        if (AutoTotem.mc.currentScreen instanceof GuiContainer && !(AutoTotem.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        final Item currentOffhandTolonEditionItem = AutoTotem.mc.player.getHeldItemOffhand().getItem();
        switch (this.currentMode) {
            case TOTEMS: {
                if (this.totems <= 0) {
                    break;
                }
                if (this.holdingTotem) {
                    break;
                }
                this.lastTotemSlot = InventoryUtil.findItemInventorySlot(Items.TOTEM_OF_UNDYING, false);
                final int lastSlot = this.getLastSlot(currentOffhandTolonEditionItem, this.lastTotemSlot);
                this.putItemInOffhandTolonEdition(this.lastTotemSlot, lastSlot);
                break;
            }
            case GAPPLES: {
                if (this.gapples <= 0) {
                    break;
                }
                if (this.holdingGapple) {
                    break;
                }
                this.lastGappleSlot = InventoryUtil.findItemInventorySlot(Items.GOLDEN_APPLE, false);
                final int lastSlot = this.getLastSlot(currentOffhandTolonEditionItem, this.lastGappleSlot);
                this.putItemInOffhandTolonEdition(this.lastGappleSlot, lastSlot);
                break;
            }
            default: {
                if (this.crystals <= 0) {
                    break;
                }
                if (this.holdingCrystal) {
                    break;
                }
                this.lastCrystalSlot = InventoryUtil.findItemInventorySlot(Items.END_CRYSTAL, false);
                final int lastSlot = this.getLastSlot(currentOffhandTolonEditionItem, this.lastCrystalSlot);
                this.putItemInOffhandTolonEdition(this.lastCrystalSlot, lastSlot);
                break;
            }
        }
        for (int i = 0; i < this.actions.getValue(); ++i) {
            final InventoryUtil.Task task = this.taskList.poll();
            if (task != null) {
                task.run();
                if (task.isSwitching()) {
                    this.didSwitchThisTick = true;
                }
            }
        }
    }
    
    private int getLastSlot(final Item item, final int slotIn) {
        if (item == Items.END_CRYSTAL) {
            return this.lastCrystalSlot;
        }
        if (item == Items.GOLDEN_APPLE) {
            return this.lastGappleSlot;
        }
        if (item == Items.TOTEM_OF_UNDYING) {
            return this.lastTotemSlot;
        }
        if (InventoryUtil.isBlock(item, BlockObsidian.class)) {
            return this.lastObbySlot;
        }
        if (InventoryUtil.isBlock(item, BlockWeb.class)) {
            return this.lastWebSlot;
        }
        if (item == Items.AIR) {
            return -1;
        }
        return slotIn;
    }
    
    private void putItemInOffhandTolonEdition(final int slotIn, final int slotOut) {
        if (slotIn != -1 && this.taskList.isEmpty()) {
            this.taskList.add(new InventoryUtil.Task(slotIn));
            this.taskList.add(new InventoryUtil.Task(45));
            this.taskList.add(new InventoryUtil.Task(slotOut));
            this.taskList.add(new InventoryUtil.Task());
        }
    }
    
    public void setMode(final Mode2 mode) {
        this.currentMode = ((this.currentMode == mode) ? Mode2.TOTEMS : mode);
    }
    
    public enum Mode2
    {
        TOTEMS, 
        GAPPLES, 
        CRYSTALS;
    }
}
