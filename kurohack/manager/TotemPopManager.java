//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import kurohack.features.modules.client.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import kurohack.features.command.*;
import java.util.*;
import kurohack.features.modules.HUD.*;
import kurohack.*;
import com.mojang.realmsclient.gui.*;
import java.util.function.*;

public class TotemPopManager extends Feature
{
    private Notifications notifications;
    private Map<EntityPlayer, Integer> poplist;
    private final Set<EntityPlayer> toAnnounce;
    
    public TotemPopManager() {
        this.poplist = new ConcurrentHashMap<EntityPlayer, Integer>();
        this.toAnnounce = new HashSet<EntityPlayer>();
    }
    
    public void onUpdate() {
        if (this.notifications.totemAnnounce.passedMs((int)this.notifications.delay.getValue()) && this.notifications.isOn() && (boolean)this.notifications.totemPops.getValue()) {
            for (final EntityPlayer player : this.toAnnounce) {
                if (player == null) {
                    continue;
                }
                int playerNumber = 0;
                for (final char character : player.getName().toCharArray()) {
                    playerNumber += character;
                    playerNumber *= 10;
                }
                Command.sendOverwriteMessage(this.pop(player), playerNumber, (boolean)this.notifications.totemNoti.getValue());
                this.toAnnounce.remove(player);
                this.notifications.totemAnnounce.reset();
                break;
            }
        }
    }
    
    public String pop(final EntityPlayer player) {
        if (this.getTotemPops(player) == 1) {
            if (!ModuleTools.getInstance().isEnabled()) {
                return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totem.";
            }
            switch ((ModuleTools.PopNotifier)ModuleTools.getInstance().popNotifier.getValue()) {
                case KUROHACK: {
                    final String text = ChatFormatting.RED + "[KuroHack] " + ChatFormatting.GREEN + player.getName() + ChatFormatting.GRAY + " just popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.GRAY + " totem.";
                    return text;
                }
                case PHOBOS: {
                    final String text = ChatFormatting.GOLD + player.getName() + ChatFormatting.RED + " popped " + ChatFormatting.GOLD + this.getTotemPops(player) + ChatFormatting.RED + " totem.";
                    return text;
                }
                case NONE: {
                    return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totem.";
                }
            }
        }
        else {
            if (!ModuleTools.getInstance().isEnabled()) {
                return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totems.";
            }
            switch ((ModuleTools.PopNotifier)ModuleTools.getInstance().popNotifier.getValue()) {
                case KUROHACK: {
                    final String text = ChatFormatting.RED + "[KuroHack] " + ChatFormatting.GREEN + player.getName() + ChatFormatting.GRAY + " just popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.GRAY + " totems.";
                    return text;
                }
                case PHOBOS: {
                    final String text = ChatFormatting.GOLD + player.getName() + ChatFormatting.RED + " popped " + ChatFormatting.GOLD + this.getTotemPops(player) + ChatFormatting.RED + " totems.";
                    return text;
                }
                case NONE: {
                    return ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totems.";
                }
            }
        }
        return "";
    }
    
    public void onLogout() {
        this.onOwnLogout((boolean)this.notifications.clearOnLogout.getValue());
    }
    
    public void init() {
        this.notifications = (Notifications)KuroHack.moduleManager.getModuleByClass((Class)Notifications.class);
    }
    
    public void onTotemPop(final EntityPlayer player) {
        this.popTotem(player);
        if (!player.equals((Object)TotemPopManager.mc.player)) {
            this.toAnnounce.add(player);
            this.notifications.totemAnnounce.reset();
        }
    }
    
    public String death1(final EntityPlayer player) {
        if (this.getTotemPops(player) == 1) {
            if (!ModuleTools.getInstance().isEnabled()) {
                return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totem!";
            }
            switch ((ModuleTools.PopNotifier)ModuleTools.getInstance().popNotifier.getValue()) {
                case KUROHACK: {
                    final String text = ChatFormatting.RED + "[KuroHack] " + ChatFormatting.GREEN + player.getName() + ChatFormatting.GRAY + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.GRAY + " totem.";
                    return text;
                }
                case PHOBOS: {
                    final String text = ChatFormatting.GOLD + player.getName() + ChatFormatting.RED + " died after popping " + ChatFormatting.GOLD + this.getTotemPops(player) + ChatFormatting.RED + " totem.";
                    return text;
                }
                case NONE: {
                    return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totem!";
                }
            }
        }
        else {
            if (!ModuleTools.getInstance().isEnabled()) {
                return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totems!";
            }
            switch ((ModuleTools.PopNotifier)ModuleTools.getInstance().popNotifier.getValue()) {
                case KUROHACK: {
                    final String text = ChatFormatting.RED + "[KuroHack] " + ChatFormatting.GREEN + player.getName() + ChatFormatting.GRAY + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.GRAY + " totems.";
                    return text;
                }
                case PHOBOS: {
                    final String text = ChatFormatting.GOLD + player.getName() + ChatFormatting.RED + " died after popping " + ChatFormatting.GOLD + this.getTotemPops(player) + ChatFormatting.RED + " totems.";
                    return text;
                }
                case NONE: {
                    return KuroHack.commandManager.getClientMessage() + ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + this.getTotemPops(player) + ChatFormatting.WHITE + " Totems!";
                }
            }
        }
        return null;
    }
    
    public void onDeath(final EntityPlayer player) {
        if (this.getTotemPops(player) != 0 && !player.equals((Object)TotemPopManager.mc.player) && this.notifications.isOn() && (boolean)this.notifications.totemPops.getValue()) {
            int playerNumber = 0;
            for (final char character : player.getName().toCharArray()) {
                playerNumber += character;
                playerNumber *= 10;
            }
            Command.sendOverwriteMessage(this.death1(player), playerNumber, (boolean)this.notifications.totemNoti.getValue());
            this.toAnnounce.remove(player);
        }
        this.resetPops(player);
    }
    
    public void onLogout(final EntityPlayer player, final boolean clearOnLogout) {
        if (clearOnLogout) {
            this.resetPops(player);
        }
    }
    
    public void onOwnLogout(final boolean clearOnLogout) {
        if (clearOnLogout) {
            this.clearList();
        }
    }
    
    public void clearList() {
        this.poplist = new ConcurrentHashMap<EntityPlayer, Integer>();
    }
    
    public void resetPops(final EntityPlayer player) {
        this.setTotemPops(player, 0);
    }
    
    public void popTotem(final EntityPlayer player) {
        this.poplist.merge(player, 1, Integer::sum);
    }
    
    public void setTotemPops(final EntityPlayer player, final int amount) {
        this.poplist.put(player, amount);
    }
    
    public int getTotemPops(final EntityPlayer player) {
        final Integer pops = this.poplist.get(player);
        if (pops == null) {
            return 0;
        }
        return pops;
    }
    
    public String getTotemPopString(final EntityPlayer player) {
        return "§f" + ((this.getTotemPops(player) <= 0) ? "" : ("-" + this.getTotemPops(player) + " "));
    }
}
