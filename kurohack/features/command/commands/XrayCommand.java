//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.features.modules.render.*;
import kurohack.*;
import kurohack.features.setting.*;
import java.util.*;

public class XrayCommand extends Command
{
    public XrayCommand() {
        super("xray", new String[] { "<add/del>", "<block>" });
    }
    
    public void execute(final String[] commands) {
        final XRay module = KuroHack.moduleManager.getModuleByClass(XRay.class);
        if (module != null) {
            if (commands.length == 1) {
                final StringBuilder blocks = new StringBuilder();
                for (final Setting setting : module.getSettings()) {
                    if (!setting.equals(module.enabled) && !setting.equals(module.drawn) && !setting.equals(module.bind) && !setting.equals(module.newBlock)) {
                        if (setting.equals(module.showBlocks)) {
                            continue;
                        }
                        blocks.append(setting.getName()).append(", ");
                    }
                }
                Command.sendMessage(blocks.toString());
                return;
            }
            if (commands.length == 2) {
                sendMessage("Please specify a block.");
                return;
            }
            final String addRemove = commands[0];
            final String blockName = commands[1];
            if (addRemove.equalsIgnoreCase("del") || addRemove.equalsIgnoreCase("remove")) {
                final Setting setting = module.getSettingByName(blockName);
                if (setting != null) {
                    if (setting.equals(module.enabled) || setting.equals(module.drawn) || setting.equals(module.bind) || setting.equals(module.newBlock) || setting.equals(module.showBlocks)) {
                        return;
                    }
                    module.unregister(setting);
                }
                sendMessage("<XRay>�c Removed: " + blockName);
            }
            else if (addRemove.equalsIgnoreCase("add")) {
                if (!module.shouldRender(blockName)) {
                    module.register(new Setting(blockName, (T)true, v -> module.showBlocks.getValue()));
                    sendMessage("<Xray> Added new Block: " + blockName);
                }
            }
            else {
                sendMessage("�cAn error occured, block either exists or wrong use of command: .xray <add/del(remove)> <block>");
            }
        }
    }
}
