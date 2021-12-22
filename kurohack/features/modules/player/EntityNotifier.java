//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import net.minecraft.entity.*;
import kurohack.features.setting.*;
import java.awt.*;
import net.minecraft.entity.monster.*;
import kurohack.features.command.*;
import net.minecraft.init.*;
import net.minecraft.entity.passive.*;
import java.util.*;

public class EntityNotifier extends Module
{
    private final Set<Entity> ghasts;
    private final Set<Entity> donkeys;
    private final Set<Entity> mules;
    private final Set<Entity> llamas;
    private final Image image;
    private final TrayIcon icon;
    public Setting<Boolean> Chat;
    public Setting<Boolean> Sound;
    public Setting<Boolean> Desktop;
    public Setting<Boolean> Ghasts;
    public Setting<Boolean> Donkeys;
    public Setting<Boolean> Mules;
    public Setting<Boolean> Llamas;
    
    public EntityNotifier() {
        super("EntityNotifier", "Helps you find certain things", Module.Category.PLAYER, true, false, false);
        this.ghasts = new HashSet<Entity>();
        this.donkeys = new HashSet<Entity>();
        this.mules = new HashSet<Entity>();
        this.llamas = new HashSet<Entity>();
        this.image = Toolkit.getDefaultToolkit().getImage("resources/logo.png");
        this.icon = new TrayIcon(this.image, "Kurohack");
        this.Chat = (Setting<Boolean>)this.register(new Setting("Chat", (T)true));
        this.Sound = (Setting<Boolean>)this.register(new Setting("Sound", (T)true));
        this.Desktop = (Setting<Boolean>)this.register(new Setting("DesktopNotifs", (T)true));
        this.Ghasts = (Setting<Boolean>)this.register(new Setting("Ghasts", (T)true));
        this.Donkeys = (Setting<Boolean>)this.register(new Setting("Donkeys", (T)true));
        this.Mules = (Setting<Boolean>)this.register(new Setting("Mules", (T)true));
        this.Llamas = (Setting<Boolean>)this.register(new Setting("Llamas", (T)true));
        this.icon.setImageAutoSize(true);
        try {
            final SystemTray tray = SystemTray.getSystemTray();
            tray.add(this.icon);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void onEnable() {
        this.ghasts.clear();
        this.donkeys.clear();
        this.mules.clear();
        this.llamas.clear();
    }
    
    public void onUpdate() {
        if (this.Ghasts.getValue()) {
            for (final Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityGhast) {
                    if (this.ghasts.contains(entity)) {
                        continue;
                    }
                    if (this.Chat.getValue()) {
                        Command.sendMessage("Ghast Detected at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.");
                    }
                    this.ghasts.add(entity);
                    if (!this.Desktop.getValue()) {
                        continue;
                    }
                    this.icon.displayMessage("KuroHack", "I found a ghast at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.", TrayIcon.MessageType.INFO);
                    if (!this.Sound.getValue()) {
                        continue;
                    }
                    EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                }
            }
        }
        if (this.Donkeys.getValue()) {
            for (final Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityDonkey) {
                    if (this.donkeys.contains(entity)) {
                        continue;
                    }
                    if (this.Chat.getValue()) {
                        Command.sendMessage("Donkey Detected at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.");
                    }
                    this.donkeys.add(entity);
                    if (!this.Desktop.getValue()) {
                        continue;
                    }
                    this.icon.displayMessage("KuroHack", "I found a donkey at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.", TrayIcon.MessageType.INFO);
                    if (!this.Sound.getValue()) {
                        continue;
                    }
                    EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                }
            }
        }
        if (this.Mules.getValue()) {
            for (final Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityMule) {
                    if (this.mules.contains(entity)) {
                        continue;
                    }
                    if (this.Chat.getValue()) {
                        Command.sendMessage("Mule Detected at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.");
                    }
                    this.mules.add(entity);
                    if (!this.Desktop.getValue()) {
                        continue;
                    }
                    this.icon.displayMessage("KuroHack", "I found a mule at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.", TrayIcon.MessageType.INFO);
                    if (!this.Sound.getValue()) {
                        continue;
                    }
                    EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                }
            }
        }
        if (this.Llamas.getValue()) {
            for (final Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityLlama) {
                    if (this.llamas.contains(entity)) {
                        continue;
                    }
                    if (this.Chat.getValue()) {
                        Command.sendMessage("Llama Detected at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.");
                    }
                    this.llamas.add(entity);
                    if (!this.Desktop.getValue()) {
                        continue;
                    }
                    this.icon.displayMessage("KuroHack", "I found a llama at: " + Math.round((float)entity.getPosition().getX()) + "x, " + Math.round((float)entity.getPosition().getY()) + "y, " + Math.round((float)entity.getPosition().getZ()) + "z.", TrayIcon.MessageType.INFO);
                    if (!this.Sound.getValue()) {
                        continue;
                    }
                    EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                }
            }
        }
    }
}
