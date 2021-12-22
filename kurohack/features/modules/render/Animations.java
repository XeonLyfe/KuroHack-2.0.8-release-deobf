//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Animations extends Module
{
    private final Setting<Mode> mode;
    private final Setting<Swing> swing;
    private final Setting<Boolean> slow;
    
    public Animations() {
        super("Animations", "Change animations.", Module.Category.RENDER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("OldAnimations", (T)Mode.OneDotEight));
        this.swing = (Setting<Swing>)this.register(new Setting("Swing", (T)Swing.Mainhand));
        this.slow = (Setting<Boolean>)this.register(new Setting("Slow", (T)false));
    }
    
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        if (this.swing.getValue() == Swing.Offhand) {
            Animations.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (this.mode.getValue() == Mode.OneDotEight && Animations.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Animations.mc.entityRenderer.itemRenderer.itemStackMainHand = Animations.mc.player.getHeldItemMainhand();
        }
    }
    
    public void onEnable() {
        if (this.slow.getValue()) {
            Animations.mc.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 255000));
        }
    }
    
    public void onDisable() {
        if (this.slow.getValue()) {
            Animations.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send send) {
        final Object t = send.getPacket();
        if (t instanceof CPacketAnimation && this.swing.getValue() == Swing.Disable) {
            send.setCanceled(true);
        }
    }
    
    private enum Mode
    {
        Normal, 
        OneDotEight;
    }
    
    private enum Swing
    {
        Mainhand, 
        Offhand, 
        Disable;
    }
}
