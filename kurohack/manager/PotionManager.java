//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import kurohack.features.modules.HUD.*;
import kurohack.features.modules.client.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.potion.*;

public class PotionManager extends Feature
{
    private final Map<EntityPlayer, PotionList> potions;
    
    public PotionManager() {
        this.potions = new ConcurrentHashMap<EntityPlayer, PotionList>();
    }
    
    public void onLogout() {
        this.potions.clear();
    }
    
    public void updatePlayer() {
        final PotionList list = new PotionList();
        for (final PotionEffect effect : PotionManager.mc.player.getActivePotionEffects()) {
            list.addEffect(effect);
        }
        this.potions.put((EntityPlayer)PotionManager.mc.player, list);
    }
    
    public void update() {
        this.updatePlayer();
        if (HUD.getInstance().isOn() && (boolean)HUD.getInstance().textRadar.getValue() && (boolean)Managers.getInstance().potions.getValue()) {
            final ArrayList<EntityPlayer> removeList = new ArrayList<EntityPlayer>();
            for (final Map.Entry<EntityPlayer, PotionList> potionEntry : this.potions.entrySet()) {
                boolean notFound = true;
                for (final EntityPlayer player : PotionManager.mc.world.playerEntities) {
                    if (this.potions.get(player) == null) {
                        final PotionList list = new PotionList();
                        for (final PotionEffect effect : player.getActivePotionEffects()) {
                            list.addEffect(effect);
                        }
                        this.potions.put(player, list);
                        notFound = false;
                    }
                    if (!potionEntry.getKey().equals((Object)player)) {
                        continue;
                    }
                    notFound = false;
                }
                if (!notFound) {
                    continue;
                }
                removeList.add(potionEntry.getKey());
            }
            for (final EntityPlayer player2 : removeList) {
                this.potions.remove(player2);
            }
        }
    }
    
    public List<PotionEffect> getOwnPotions() {
        return this.getPlayerPotions((EntityPlayer)PotionManager.mc.player);
    }
    
    public List<PotionEffect> getPlayerPotions(final EntityPlayer player) {
        final PotionList list = this.potions.get(player);
        List<PotionEffect> potions = new ArrayList<PotionEffect>();
        if (list != null) {
            potions = list.getEffects();
        }
        return potions;
    }
    
    public void onTotemPop(final EntityPlayer player) {
        final PotionList list = new PotionList();
        this.potions.put(player, list);
    }
    
    public PotionEffect[] getImportantPotions(final EntityPlayer player) {
        final PotionEffect[] array = new PotionEffect[3];
        for (final PotionEffect effect : this.getPlayerPotions(player)) {
            final Potion potion = effect.getPotion();
            final String lowerCase = I18n.format(potion.getName(), new Object[0]).toLowerCase();
            switch (lowerCase) {
                case "strength": {
                    array[0] = effect;
                    continue;
                }
                case "weakness": {
                    array[1] = effect;
                    continue;
                }
                case "speed": {
                    array[2] = effect;
                    continue;
                }
            }
        }
        return array;
    }
    
    public String getPotionString(final PotionEffect effect) {
        final Potion potion = effect.getPotion();
        return I18n.format(potion.getName(), new Object[0]) + " " + ((!(boolean)HUD.getInstance().potions1.getValue() && effect.getAmplifier() == 0) ? "" : (effect.getAmplifier() + 1 + " ")) + "�f" + Potion.getPotionDurationString(effect, 1.0f);
    }
    
    public String getColoredPotionString(final PotionEffect effect) {
        final Potion potion = effect.getPotion();
        final String format = I18n.format(potion.getName(), new Object[0]);
        switch (format) {
            case "Jump Boost":
            case "Speed": {
                return "�b" + this.getPotionString(effect);
            }
            case "Resistance":
            case "Strength": {
                return "�c" + this.getPotionString(effect);
            }
            case "Wither":
            case "Slowness":
            case "Weakness": {
                return "�0" + this.getPotionString(effect);
            }
            case "Absorption": {
                return "�9" + this.getPotionString(effect);
            }
            case "Haste":
            case "Fire Resistance": {
                return "�6" + this.getPotionString(effect);
            }
            case "Regeneration": {
                return "�d" + this.getPotionString(effect);
            }
            case "Night Vision":
            case "Poison": {
                return "�a" + this.getPotionString(effect);
            }
            default: {
                return "�f" + this.getPotionString(effect);
            }
        }
    }
    
    public String getTextRadarPotionWithDuration(final EntityPlayer player) {
        final PotionEffect[] array = this.getImportantPotions(player);
        final PotionEffect strength = array[0];
        final PotionEffect weakness = array[1];
        final PotionEffect speed = array[2];
        return "" + ((strength != null) ? ("�c S" + (strength.getAmplifier() + 1) + " " + Potion.getPotionDurationString(strength, 1.0f)) : "") + ((weakness != null) ? ("�8 W " + Potion.getPotionDurationString(weakness, 1.0f)) : "") + ((speed != null) ? ("�b S" + (speed.getAmplifier() + 1) + " " + Potion.getPotionDurationString(weakness, 1.0f)) : "");
    }
    
    public String getTextRadarPotion(final EntityPlayer player) {
        final PotionEffect[] array = this.getImportantPotions(player);
        final PotionEffect strength = array[0];
        final PotionEffect weakness = array[1];
        final PotionEffect speed = array[2];
        return "" + ((strength != null) ? ("�c S" + (strength.getAmplifier() + 1) + " ") : "") + ((weakness != null) ? "�8 W " : "") + ((speed != null) ? ("�b S" + (speed.getAmplifier() + 1) + " ") : "");
    }
    
    public static class PotionList
    {
        private final List<PotionEffect> effects;
        
        public PotionList() {
            this.effects = new ArrayList<PotionEffect>();
        }
        
        public void addEffect(final PotionEffect effect) {
            if (effect != null) {
                this.effects.add(effect);
            }
        }
        
        public List<PotionEffect> getEffects() {
            return this.effects;
        }
    }
}
