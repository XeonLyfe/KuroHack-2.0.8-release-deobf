//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.modules.*;
import kurohack.util.*;
import kurohack.features.setting.*;
import net.minecraft.entity.player.*;
import kurohack.features.command.*;
import kurohack.*;
import net.minecraft.init.*;
import java.util.*;
import kurohack.manager.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.features.modules.HUD.*;
import com.mojang.realmsclient.gui.*;
import kurohack.event.events.*;
import net.minecraft.util.text.*;

public class Notifications extends Module
{
    private static final String fileName = "kurohack/util/ModuleMessage_List.txt";
    private static final List<String> modules;
    private static Notifications INSTANCE;
    private final Timer timer;
    public Setting<Boolean> totemPops;
    public Setting<Boolean> totemNoti;
    public Setting<Integer> delay;
    public Setting<Boolean> clearOnLogout;
    public Setting<Boolean> moduleMessage;
    public Setting<Boolean> list;
    public Setting<Boolean> watermark;
    public Setting<Boolean> visualRange;
    public Setting<Boolean> VisualRangeSound;
    public Setting<Boolean> coords;
    public Setting<Boolean> leaving;
    public Setting<Boolean> crash;
    public Setting<Boolean> pearls;
    public Setting<Boolean> popUp;
    public Timer totemAnnounce;
    private final Setting<Boolean> readfile;
    private List<EntityPlayer> knownPlayers;
    private boolean check;
    
    public Notifications() {
        super("Notifications", "Sends Messages.", Category.CLIENT, true, false, false);
        this.timer = new Timer();
        this.totemPops = (Setting<Boolean>)this.register(new Setting("TotemPops", (T)false));
        this.totemNoti = (Setting<Boolean>)this.register(new Setting("TotemNoti", (T)true, v -> this.totemPops.getValue()));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)2000, (T)0, (T)5000, v -> this.totemPops.getValue(), "Delays messages."));
        this.clearOnLogout = (Setting<Boolean>)this.register(new Setting("LogoutClear", (T)false));
        this.moduleMessage = (Setting<Boolean>)this.register(new Setting("ModuleMessage", (T)false));
        this.list = (Setting<Boolean>)this.register(new Setting("List", (T)false, v -> this.moduleMessage.getValue()));
        this.watermark = (Setting<Boolean>)this.register(new Setting("Watermark", (T)true, v -> this.moduleMessage.getValue()));
        this.visualRange = (Setting<Boolean>)this.register(new Setting("VisualRange", (T)false));
        this.VisualRangeSound = (Setting<Boolean>)this.register(new Setting("VisualRangeSound", (T)false));
        this.coords = (Setting<Boolean>)this.register(new Setting("Coords", (T)true, v -> this.visualRange.getValue()));
        this.leaving = (Setting<Boolean>)this.register(new Setting("Leaving", (T)false, v -> this.visualRange.getValue()));
        this.crash = (Setting<Boolean>)this.register(new Setting("Crash", (T)false));
        this.pearls = (Setting<Boolean>)this.register(new Setting("PearlNotifs", (T)false));
        this.popUp = (Setting<Boolean>)this.register(new Setting("PopUpVisualRange", (T)false));
        this.totemAnnounce = new Timer();
        this.readfile = (Setting<Boolean>)this.register(new Setting("LoadFile", (T)false, v -> this.moduleMessage.getValue()));
        this.knownPlayers = new ArrayList<EntityPlayer>();
        this.setInstance();
    }
    
    public static Notifications getInstance() {
        if (Notifications.INSTANCE == null) {
            Notifications.INSTANCE = new Notifications();
        }
        return Notifications.INSTANCE;
    }
    
    public static void displayCrash(final Exception e) {
        Command.sendMessage("§cException caught: " + e.getMessage());
    }
    
    private void setInstance() {
        Notifications.INSTANCE = this;
    }
    
    @Override
    public void onLoad() {
        this.check = true;
        this.loadFile();
        this.check = false;
    }
    
    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<EntityPlayer>();
        if (!this.check) {
            this.loadFile();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.readfile.getValue()) {
            if (!this.check) {
                Command.sendMessage("Loading File...");
                this.timer.reset();
                this.loadFile();
            }
            this.check = true;
        }
        if (this.check && this.timer.passedMs(750L)) {
            this.readfile.setValue(false);
            this.check = false;
        }
        if (this.visualRange.getValue()) {
            final ArrayList<EntityPlayer> tickPlayerList = new ArrayList<EntityPlayer>(Notifications.mc.world.playerEntities);
            if (tickPlayerList.size() > 0) {
                for (final EntityPlayer player : tickPlayerList) {
                    if (!player.getName().equals(Notifications.mc.player.getName())) {
                        if (this.knownPlayers.contains(player)) {
                            continue;
                        }
                        this.knownPlayers.add(player);
                        if (KuroHack.friendManager.isFriend(player)) {
                            Command.sendMessage("Player §a" + player.getName() + "§r entered your visual range" + (this.coords.getValue() ? (" at (" + (int)player.posX + ", " + (int)player.posY + ", " + (int)player.posZ + ")!") : "!"), (boolean)this.popUp.getValue());
                        }
                        else {
                            Command.sendMessage("Player §c" + player.getName() + "§r entered your visual range" + (this.coords.getValue() ? (" at (" + (int)player.posX + ", " + (int)player.posY + ", " + (int)player.posZ + ")!") : "!"), (boolean)this.popUp.getValue());
                        }
                        if (this.VisualRangeSound.getValue()) {
                            Notifications.mc.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        }
                        return;
                    }
                }
            }
            if (this.knownPlayers.size() > 0) {
                for (final EntityPlayer player : this.knownPlayers) {
                    if (tickPlayerList.contains(player)) {
                        continue;
                    }
                    this.knownPlayers.remove(player);
                    if (this.leaving.getValue()) {
                        if (KuroHack.friendManager.isFriend(player)) {
                            Command.sendMessage("Player §a" + player.getName() + "§r left your visual range" + (this.coords.getValue() ? (" at (" + (int)player.posX + ", " + (int)player.posY + ", " + (int)player.posZ + ")!") : "!"), (boolean)this.popUp.getValue());
                        }
                        else {
                            Command.sendMessage("Player §c" + player.getName() + "§r left your visual range" + (this.coords.getValue() ? (" at (" + (int)player.posX + ", " + (int)player.posY + ", " + (int)player.posZ + ")!") : "!"), (boolean)this.popUp.getValue());
                        }
                    }
                }
            }
        }
    }
    
    public void loadFile() {
        final List<String> fileInput = FileManager.readTextFileAllLines("kurohack/util/ModuleMessage_List.txt");
        final Iterator<String> i = fileInput.iterator();
        Notifications.modules.clear();
        while (i.hasNext()) {
            final String s = i.next();
            if (s.replaceAll("\\s", "").isEmpty()) {
                continue;
            }
            Notifications.modules.add(s);
        }
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject && this.pearls.getValue()) {
            final SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
            final EntityPlayer player = Notifications.mc.world.getClosestPlayer(packet.getX(), packet.getY(), packet.getZ(), 1.0, false);
            if (player == null) {
                return;
            }
            if (packet.getEntityID() == 85) {
                Command.sendMessage("§cPearl thrown by " + player.getName() + " at X:" + (int)packet.getX() + " Y:" + (int)packet.getY() + " Z:" + (int)packet.getZ());
            }
        }
    }
    
    public TextComponentString getNotifierOn(final Module module) {
        if (ModuleTools.getInstance().isEnabled()) {
            switch (ModuleTools.getInstance().notifier.getValue()) {
                case KUROHACK: {
                    final TextComponentString text = new TextComponentString(ChatFormatting.RED + "[KuroHack] " + ChatFormatting.GRAY + module.getDisplayName() + " toggled " + ChatFormatting.GREEN + "on" + ChatFormatting.GRAY + ".");
                    return text;
                }
                case PHOBOS: {
                    final TextComponentString text = new TextComponentString(KuroHack.commandManager.getClientMessage() + ChatFormatting.BOLD + module.getDisplayName() + ChatFormatting.RESET + ChatFormatting.GREEN + " enabled.");
                    return text;
                }
            }
        }
        final TextComponentString text = new TextComponentString(KuroHack.commandManager.getClientMessage() + ChatFormatting.GREEN + module.getDisplayName() + " toggled on.");
        return text;
    }
    
    public TextComponentString getNotifierOff(final Module module) {
        if (ModuleTools.getInstance().isEnabled()) {
            switch (ModuleTools.getInstance().notifier.getValue()) {
                case KUROHACK: {
                    final TextComponentString text = new TextComponentString(ChatFormatting.RED + "[KUROHACK] " + ChatFormatting.GRAY + module.getDisplayName() + " toggled " + ChatFormatting.RED + "off" + ChatFormatting.GRAY + ".");
                    return text;
                }
                case PHOBOS: {
                    final TextComponentString text = new TextComponentString(KuroHack.commandManager.getClientMessage() + ChatFormatting.BOLD + module.getDisplayName() + ChatFormatting.RESET + ChatFormatting.RED + " disabled.");
                    return text;
                }
            }
        }
        final TextComponentString text = new TextComponentString(KuroHack.commandManager.getClientMessage() + ChatFormatting.RED + module.getDisplayName() + " toggled off.");
        return text;
    }
    
    @SubscribeEvent
    public void onToggleModule(final ClientEvent event) {
        if (!this.moduleMessage.getValue()) {
            return;
        }
        Module module;
        if (event.getStage() == 0 && !(module = (Module)event.getFeature()).equals(this) && (Notifications.modules.contains(module.getDisplayName()) || !this.list.getValue())) {
            int moduleNumber = 0;
            for (final char character : module.getDisplayName().toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            Notifications.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)this.getNotifierOff(module), moduleNumber);
        }
        if (event.getStage() == 1 && (Notifications.modules.contains((module = (Module)event.getFeature()).getDisplayName()) || !this.list.getValue())) {
            int moduleNumber = 0;
            for (final char character : module.getDisplayName().toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            Notifications.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)this.getNotifierOn(module), moduleNumber);
        }
    }
    
    static {
        modules = new ArrayList<String>();
        Notifications.INSTANCE = new Notifications();
    }
}
