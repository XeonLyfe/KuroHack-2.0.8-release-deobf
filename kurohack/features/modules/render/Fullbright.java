//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import kurohack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Fullbright extends Module
{
    public Setting<Mode> mode;
    public Setting<Boolean> effects;
    private float previousSetting;
    
    public Fullbright() {
        super("Fullbright", "Makes your game brighter.", Module.Category.RENDER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Speed", "Mode", 0.0, 0.0, (T)Mode.GAMMA, 0));
        this.effects = (Setting<Boolean>)this.register(new Setting("Speed", "Effects", 0.0, 0.0, (T)false, 0));
        this.previousSetting = 1.0f;
    }
    
    public void onEnable() {
        this.previousSetting = Fullbright.mc.gameSettings.gammaSetting;
    }
    
    public void onUpdate() {
        if (this.mode.getValue() == Mode.GAMMA) {
            Fullbright.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.getValue() == Mode.POTION) {
            Fullbright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }
    
    public void onDisable() {
        if (this.mode.getValue() == Mode.POTION) {
            Fullbright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        Fullbright.mc.gameSettings.gammaSetting = this.previousSetting;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() == 0 && event.getPacket() instanceof SPacketEntityEffect && this.effects.getValue()) {
            final SPacketEntityEffect packet = (SPacketEntityEffect)event.getPacket();
            if (Fullbright.mc.player != null && packet.getEntityId() == Fullbright.mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15)) {
                event.setCanceled(true);
            }
        }
    }
    
    public enum Mode
    {
        GAMMA, 
        POTION;
    }
}
