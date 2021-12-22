//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.*;

public class BaritoneNoStop extends Command
{
    public BaritoneNoStop() {
        super("noStop", new String[] { "<prefix>", "<x>", "<y>", "<z>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 5) {
            KuroHack.baritoneManager.setPrefix(commands[0]);
            int x = 0;
            int y = 0;
            int z = 0;
            try {
                x = Integer.parseInt(commands[1]);
                y = Integer.parseInt(commands[2]);
                z = Integer.parseInt(commands[3]);
            }
            catch (NumberFormatException e) {
                sendMessage("Invalid Input for x, y or z!");
                KuroHack.baritoneManager.stop();
                return;
            }
            KuroHack.baritoneManager.start(x, y, z);
            return;
        }
        sendMessage("Stoping Baritone-Nostop.");
        KuroHack.baritoneManager.stop();
    }
}
