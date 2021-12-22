//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import net.minecraft.util.math.*;
import kurohack.features.setting.*;
import kurohack.util.render.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import kurohack.features.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import kurohack.features.modules.combat.*;
import kurohack.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import kurohack.event.events.*;

public class Tracker extends Module
{
    private static Tracker instance;
    private final Timer timer;
    private final Set<BlockPos> manuallyPlaced;
    public Setting<TextUtil.Color> color;
    public Setting<Boolean> autoEnable;
    public Setting<Boolean> autoDisable;
    private EntityPlayer trackedPlayer;
    private int usedExp;
    private int usedStacks;
    private int usedCrystals;
    private int usedCStacks;
    private boolean shouldEnable;
    
    public Tracker() {
        super("Tracker", "Tracks players in 1v1s. Only good in duels tho!", Category.MISC, true, false, true);
        this.timer = new Timer();
        this.manuallyPlaced = new HashSet<BlockPos>();
        this.color = (Setting<TextUtil.Color>)this.register(new Setting("Speed", "Color", 0.0, 0.0, (T)TextUtil.Color.RED, 0));
        this.autoEnable = (Setting<Boolean>)this.register(new Setting("Speed", "AutoEnable", 0.0, 0.0, (T)false, 0));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("Speed", "AutoDisable", 0.0, 0.0, (T)true, 0));
        this.usedExp = 0;
        this.usedStacks = 0;
        this.usedCrystals = 0;
        this.usedCStacks = 0;
        this.shouldEnable = false;
        Tracker.instance = this;
    }
    
    public static Tracker getInstance() {
        if (Tracker.instance == null) {
            Tracker.instance = new Tracker();
        }
        return Tracker.instance;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (!fullNullCheck() && (this.autoEnable.getValue() || this.autoDisable.getValue()) && event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            final String message = packet.getChatComponent().getFormattedText();
            if (this.autoEnable.getValue() && (message.contains("has accepted your duel request") || message.contains("Accepted the duel request from")) && !message.contains("<")) {
                Command.sendMessage("Tracker will enable in 5 seconds.");
                this.timer.reset();
                this.shouldEnable = true;
            }
            else if (this.autoDisable.getValue() && message.contains("has defeated") && message.contains(Tracker.mc.player.getName()) && !message.contains("<")) {
                this.disable();
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (!fullNullCheck() && this.isOn() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
            if (Tracker.mc.player.getHeldItem(packet.hand).getItem() == Items.END_CRYSTAL && !AntiTrap.placedPos.contains(packet.position) && !AutoCrystal.placedPos.contains(packet.position)) {
                this.manuallyPlaced.add(packet.position);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (this.shouldEnable && this.timer.passedS(5.0) && this.isOff()) {
            this.enable();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.isOff()) {
            return;
        }
        if (this.trackedPlayer == null) {
            this.trackedPlayer = EntityUtil.getClosestEnemy(1000.0);
        }
        else {
            if (this.usedStacks != this.usedExp / 64) {
                this.usedStacks = this.usedExp / 64;
                Command.sendMessage(TextUtil.coloredString(this.trackedPlayer.getName() + " used: " + this.usedStacks + " Stacks of EXP.", this.color.getValue()));
            }
            if (this.usedCStacks != this.usedCrystals / 64) {
                this.usedCStacks = this.usedCrystals / 64;
                Command.sendMessage(TextUtil.coloredString(this.trackedPlayer.getName() + " used: " + this.usedCStacks + " Stacks of Crystals.", this.color.getValue()));
            }
        }
    }
    
    public void onSpawnEntity(final Entity entity) {
        if (this.isOff()) {
            return;
        }
        if (entity instanceof EntityExpBottle && Objects.equals(Tracker.mc.world.getClosestPlayerToEntity(entity, 3.0), this.trackedPlayer)) {
            ++this.usedExp;
        }
        if (entity instanceof EntityEnderCrystal) {
            if (AntiTrap.placedPos.contains(entity.getPosition().down())) {
                AntiTrap.placedPos.remove(entity.getPosition().down());
            }
            else if (this.manuallyPlaced.contains(entity.getPosition().down())) {
                this.manuallyPlaced.remove(entity.getPosition().down());
            }
            else if (!AutoCrystal.placedPos.contains(entity.getPosition().down())) {
                ++this.usedCrystals;
            }
        }
    }
    
    @SubscribeEvent
    public void onConnection(final ConnectionEvent event) {
        if (this.isOff() || event.getStage() != 1) {
            return;
        }
        final String name = event.getName();
        if (this.trackedPlayer != null && name != null && name.equals(this.trackedPlayer.getName()) && this.autoDisable.getValue()) {
            Command.sendMessage(name + " logged, Tracker disableing.");
            this.disable();
        }
    }
    
    @Override
    public void onToggle() {
        this.manuallyPlaced.clear();
        AntiTrap.placedPos.clear();
        this.shouldEnable = false;
        this.trackedPlayer = null;
        this.usedExp = 0;
        this.usedStacks = 0;
        this.usedCrystals = 0;
        this.usedCStacks = 0;
    }
    
    @Override
    public void onLogout() {
        if (this.autoDisable.getValue()) {
            this.disable();
        }
    }
    
    @SubscribeEvent
    public void onDeath(final DeathEvent event) {
        if (this.isOn() && (event.player.equals((Object)this.trackedPlayer) || event.player.equals((Object)Tracker.mc.player))) {
            this.usedExp = 0;
            this.usedStacks = 0;
            this.usedCrystals = 0;
            this.usedCStacks = 0;
            if (this.autoDisable.getValue()) {
                this.disable();
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.trackedPlayer != null) {
            return this.trackedPlayer.getName();
        }
        return null;
    }
}
