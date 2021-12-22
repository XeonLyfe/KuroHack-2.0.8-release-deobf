//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.*;
import com.mojang.realmsclient.gui.*;
import kurohack.features.modules.misc.*;
import kurohack.util.element.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", new String[] { "<add/del/name/clear>", "<name>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (KuroHack.friendManager.getFriends().isEmpty()) {
                sendMessage("You currently dont have any friends added.");
            }
            else {
                sendMessage("Friends: ");
                for (final Map.Entry<String, UUID> entry : KuroHack.friendManager.getFriends().entrySet()) {
                    sendMessage((String)entry.getKey());
                }
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        KuroHack.friendManager.addFriend(commands[1]);
                        sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                        if (FriendSettings.getInstance().notify.getValue()) {
                            IUtil.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just added you to my friends list on Charlie dana hack!"));
                        }
                    }
                    case "del": {
                        KuroHack.friendManager.removeFriend(commands[1]);
                        if (FriendSettings.getInstance().notify.getValue()) {
                            IUtil.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just removed you from my friends list on Charlie dana hack!"));
                        }
                        sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    }
                    default: {
                        sendMessage("Unknown Command, try friend add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        final String s2 = commands[0];
        switch (s2) {
            case "reset": {
                KuroHack.friendManager.onLoad();
                sendMessage("Friends got reset.");
            }
            default: {
                sendMessage(commands[0] + (KuroHack.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            }
        }
    }
}
