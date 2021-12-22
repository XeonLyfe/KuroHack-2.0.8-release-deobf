//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import java.io.*;
import java.util.stream.*;
import kurohack.*;
import java.util.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("config", new String[] { "<save/load>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            sendMessage("You`ll find the config files in your gameProfile directory under creepyware/config");
            return;
        }
        if (commands.length == 2) {
            if ("list".equals(commands[0])) {
                String configs = "Configs: ";
                final File file = new File("creepyware/");
                final List<File> directories = Arrays.stream(file.listFiles()).filter(File::isDirectory).filter(f -> !f.getName().equals("util")).collect((Collector<? super File, ?, List<File>>)Collectors.toList());
                final StringBuilder builder = new StringBuilder(configs);
                for (final File file2 : directories) {
                    builder.append(file2.getName() + ", ");
                }
                configs = builder.toString();
                sendMessage("§a" + configs);
            }
            else {
                sendMessage("§cNot a valid command... Possible usage: <list>");
            }
        }
        if (commands.length >= 3) {
            final String s = commands[0];
            switch (s) {
                case "save": {
                    KuroHack.configManager.saveConfig(commands[1]);
                    sendMessage("§aConfig has been saved.");
                    break;
                }
                case "load": {
                    KuroHack.moduleManager.onUnload();
                    KuroHack.configManager.loadConfig(commands[1]);
                    KuroHack.moduleManager.onLoad();
                    sendMessage("§aConfig has been loaded.");
                    break;
                }
                default: {
                    sendMessage("§cNot a valid command... Possible usage: <save/load>");
                    break;
                }
            }
        }
    }
}
