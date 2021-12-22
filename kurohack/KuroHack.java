//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack;

import net.minecraftforge.fml.common.*;
import kurohack.features.gui.custom.*;
import kurohack.manager.*;
import kurohack.features.modules.misc.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.nio.*;
import kurohack.util.*;
import org.lwjgl.opengl.*;
import java.io.*;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.*;

@Mod(modid = "kurohack", name = "Kurohack", version = "2.0.8")
public class KuroHack
{
    public static final String MODID = "kurohack";
    public static final String MODNAME = "Kurohack";
    public static final String MODVER = "2.0.8";
    public static final Logger LOGGER;
    public static ModuleManager moduleManager;
    public static SpeedManager speedManager;
    public static PositionManager positionManager;
    public static RotationManager rotationManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static ConfigManager configManager;
    public static FileManager fileManager;
    public static FriendManager friendManager;
    public static TextManager textManager;
    public static ColorManager colorManager;
    public static ServerManager serverManager;
    public static PotionManager potionManager;
    public static InventoryManager inventoryManager;
    public static TimerManager timerManager;
    public static PacketManager packetManager;
    public static ReloadManager reloadManager;
    public static TotemPopManager totemPopManager;
    public static HoleManager holeManager;
    public static NotificationManager notificationManager;
    public static SafetyManager safetyManager;
    public static GuiCustomMainScreen customMainScreen;
    public static NoStopManager baritoneManager;
    public static TotemPopListener popListener;
    @Mod.Instance
    public static KuroHack INSTANCE;
    private static boolean unloaded;
    
    public static void load() {
        KuroHack.LOGGER.info("\n\nLoading KuroHack");
        KuroHack.unloaded = false;
        if (KuroHack.reloadManager != null) {
            KuroHack.reloadManager.unload();
            KuroHack.reloadManager = null;
        }
        KuroHack.baritoneManager = new NoStopManager();
        KuroHack.totemPopManager = new TotemPopManager();
        KuroHack.timerManager = new TimerManager();
        KuroHack.packetManager = new PacketManager();
        KuroHack.serverManager = new ServerManager();
        KuroHack.colorManager = new ColorManager();
        KuroHack.textManager = new TextManager();
        KuroHack.moduleManager = new ModuleManager();
        KuroHack.speedManager = new SpeedManager();
        KuroHack.rotationManager = new RotationManager();
        KuroHack.positionManager = new PositionManager();
        KuroHack.commandManager = new CommandManager();
        KuroHack.eventManager = new EventManager();
        KuroHack.configManager = new ConfigManager();
        KuroHack.fileManager = new FileManager();
        KuroHack.friendManager = new FriendManager();
        KuroHack.potionManager = new PotionManager();
        KuroHack.inventoryManager = new InventoryManager();
        KuroHack.holeManager = new HoleManager();
        KuroHack.notificationManager = new NotificationManager();
        KuroHack.safetyManager = new SafetyManager();
        KuroHack.popListener = new TotemPopListener();
        KuroHack.LOGGER.info("Initialized Managers");
        KuroHack.moduleManager.init();
        KuroHack.LOGGER.info("Modules loaded.");
        KuroHack.configManager.init();
        KuroHack.eventManager.init();
        KuroHack.LOGGER.info("EventManager loaded.");
        KuroHack.textManager.init(true);
        KuroHack.moduleManager.onLoad();
        KuroHack.totemPopManager.init();
        KuroHack.timerManager.init();
        if (KuroHack.moduleManager.getModuleByClass(RPC.class).isEnabled()) {
            DiscordPresence.start();
        }
        KuroHack.LOGGER.info("KuroHack successfully loaded!\n");
    }
    
    public static String getVersion() {
        return getVersion();
    }
    
    public static void unload(final boolean unload) {
        KuroHack.LOGGER.info("\n\nUnloading KuroHack");
        if (unload) {
            (KuroHack.reloadManager = new ReloadManager()).init((KuroHack.commandManager != null) ? KuroHack.commandManager.getPrefix() : ".");
        }
        if (KuroHack.baritoneManager != null) {
            KuroHack.baritoneManager.stop();
        }
        onUnload();
        KuroHack.eventManager = null;
        KuroHack.holeManager = null;
        KuroHack.timerManager = null;
        KuroHack.moduleManager = null;
        KuroHack.totemPopManager = null;
        KuroHack.serverManager = null;
        KuroHack.colorManager = null;
        KuroHack.textManager = null;
        KuroHack.speedManager = null;
        KuroHack.rotationManager = null;
        KuroHack.positionManager = null;
        KuroHack.commandManager = null;
        KuroHack.configManager = null;
        KuroHack.fileManager = null;
        KuroHack.friendManager = null;
        KuroHack.potionManager = null;
        KuroHack.inventoryManager = null;
        KuroHack.notificationManager = null;
        KuroHack.safetyManager = null;
        KuroHack.LOGGER.info("KuroHack unloaded!\n");
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    public static void onUnload() {
        if (!KuroHack.unloaded) {
            KuroHack.eventManager.onUnload();
            KuroHack.moduleManager.onUnload();
            KuroHack.configManager.saveConfig(KuroHack.configManager.config.replaceFirst("kurohack/", ""));
            KuroHack.moduleManager.onUnloadPost();
            KuroHack.timerManager.unload();
            KuroHack.unloaded = true;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        KuroHack.LOGGER.info("============================================");
        KuroHack.LOGGER.info("......ApiLoader (Rat) has been injected.....");
        KuroHack.LOGGER.info("......Thanks for the using this rat!........");
        KuroHack.LOGGER.info("============================================");
    }
    
    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (final InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/kurohack/icons/logo-16x.png");
                 final InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/kurohack/icons/logo-32x.png")) {
                final ByteBuffer[] icons = { IconUtils.INSTANCE.readImageToBuffer(inputStream16x), IconUtils.INSTANCE.readImageToBuffer(inputStream32x) };
                Display.setIcon((ByteBuffer[])icons);
            }
            catch (Exception e) {
                KuroHack.LOGGER.error("Couldn't set Windows Icon", (Throwable)e);
            }
        }
    }
    
    private void setWindowsIcon() {
        setWindowIcon();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        this.setWindowsIcon();
        KuroHack.customMainScreen = new GuiCustomMainScreen();
        Display.setTitle(" [ KuroHack | v2.0.8 ]");
        load();
    }
    
    static {
        LOGGER = LogManager.getLogger("KuroHack");
        KuroHack.unloaded = false;
    }
}
