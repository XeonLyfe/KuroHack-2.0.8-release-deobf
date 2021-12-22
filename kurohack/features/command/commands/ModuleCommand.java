//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.*;
import kurohack.features.modules.*;
import kurohack.features.setting.*;
import com.google.gson.*;
import kurohack.manager.*;
import kurohack.features.*;
import java.util.*;

public class ModuleCommand extends Command
{
    public ModuleCommand() {
        super("module", new String[] { "<module>", "<set/reset>", "<setting>", "<value>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            sendMessage("Modules: ");
            for (final Module.Category category : KuroHack.moduleManager.getCategories()) {
                String modules = category.getName() + ": ";
                for (final Module module : KuroHack.moduleManager.getModulesByCategory(category)) {
                    modules = modules + (module.isEnabled() ? "§a" : "§c") + module.getName() + "§r, ";
                }
                sendMessage(modules);
            }
            return;
        }
        Module module2 = KuroHack.moduleManager.getModuleByDisplayName(commands[0]);
        if (module2 == null) {
            module2 = KuroHack.moduleManager.getModuleByName(commands[0]);
            if (module2 == null) {
                sendMessage("§cThis module doesnt exist.");
                return;
            }
            sendMessage("§c This is the original name of the module. Its current name is: " + module2.getDisplayName());
        }
        else {
            if (commands.length == 2) {
                sendMessage(module2.getDisplayName() + " : " + module2.getDescription());
                for (final Setting setting2 : module2.getSettings()) {
                    sendMessage(setting2.getName() + " : " + setting2.getValue() + ", " + setting2.getDescription());
                }
                return;
            }
            if (commands.length == 3) {
                if (commands[1].equalsIgnoreCase("set")) {
                    sendMessage("§cPlease specify a setting.");
                }
                else if (commands[1].equalsIgnoreCase("reset")) {
                    for (final Setting setting3 : module2.getSettings()) {
                        setting3.setValue(setting3.getDefaultValue());
                    }
                }
                else {
                    sendMessage("§cThis command doesnt exist.");
                }
                return;
            }
            if (commands.length == 4) {
                sendMessage("§cPlease specify a value.");
                return;
            }
            final Setting setting4;
            if (commands.length == 5 && (setting4 = module2.getSettingByName(commands[2])) != null) {
                final JsonParser jp = new JsonParser();
                if (setting4.getType().equalsIgnoreCase("String")) {
                    setting4.setValue(commands[3]);
                    sendMessage("§a" + module2.getName() + " " + setting4.getName() + " has been set to " + commands[3] + ".");
                    return;
                }
                try {
                    if (setting4.getName().equalsIgnoreCase("Enabled")) {
                        if (commands[3].equalsIgnoreCase("true")) {
                            module2.enable();
                        }
                        if (commands[3].equalsIgnoreCase("false")) {
                            module2.disable();
                        }
                    }
                    ConfigManager.setValueFromJson(module2, setting4, jp.parse(commands[3]));
                }
                catch (Exception e) {
                    sendMessage("§cBad Value! This setting requires a: " + setting4.getType() + " value.");
                    return;
                }
                sendMessage("§a" + module2.getName() + " " + setting4.getName() + " has been set to " + commands[3] + ".");
            }
        }
    }
}
