//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.*;
import java.util.*;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("commands");
    }
    
    public void execute(final String[] commands) {
        sendMessage("You can use following commands: ");
        for (final Command command : KuroHack.commandManager.getCommands()) {
            sendMessage(KuroHack.commandManager.getPrefix() + command.getName());
        }
    }
}
