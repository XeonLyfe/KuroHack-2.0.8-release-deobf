//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.features.modules.client.*;
import kurohack.*;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", new String[] { "<char>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("§cSpecify a new prefix.");
            return;
        }
        KuroHack.moduleManager.getModuleByClass(ClickGui.class).prefix.setValue(commands[0]);
        Command.sendMessage("Prefix set to §a" + KuroHack.commandManager.getPrefix());
    }
}
