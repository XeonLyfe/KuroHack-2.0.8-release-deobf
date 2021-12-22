//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack;

import club.minnced.discord.rpc.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import kurohack.features.modules.misc.*;

public class DiscordPresence
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static Thread thread;
    private static int index;
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordPresence.rpc.Discord_Initialize("844905497340411965", handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = ((Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) ? "Main menu" : ("Playing " + ((Minecraft.getMinecraft().currentServerData != null) ? (RPC.INSTANCE.showIP.getValue() ? ("on " + Minecraft.getMinecraft().currentServerData.serverIP + ".") : " multiplayer") : " singleplayer")));
        DiscordPresence.presence.state = "Sex activity";
        DiscordPresence.presence.largeImageKey = "kuro";
        DiscordPresence.presence.largeImageText = "v2.0.8";
        DiscordPresence.presence.smallImageKey = "kuro";
        DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
        DiscordRichPresence presence;
        String string;
        String string2;
        final StringBuilder sb;
        (DiscordPresence.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordPresence.rpc.Discord_RunCallbacks();
                presence = DiscordPresence.presence;
                if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
                    string = "Main menu";
                }
                else {
                    new StringBuilder().append("Playing ");
                    if (Minecraft.getMinecraft().currentServerData != null) {
                        if (RPC.INSTANCE.showIP.getValue()) {
                            string2 = "on " + Minecraft.getMinecraft().currentServerData.serverIP + ".";
                        }
                        else {
                            string2 = " multiplayer";
                        }
                    }
                    else {
                        string2 = " singleplayer";
                    }
                    string = sb.append(string2).toString();
                }
                presence.details = string;
                DiscordPresence.presence.state = "Kuro is real hecker";
                if (RPC.INSTANCE.users.getValue() && DiscordPresence.index == 6) {
                    DiscordPresence.index = 1;
                }
                DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        if (DiscordPresence.thread != null && !DiscordPresence.thread.isInterrupted()) {
            DiscordPresence.thread.interrupt();
        }
        DiscordPresence.rpc.Discord_Shutdown();
    }
    
    static {
        DiscordPresence.index = 1;
        rpc = DiscordRPC.INSTANCE;
        DiscordPresence.presence = new DiscordRichPresence();
    }
}
