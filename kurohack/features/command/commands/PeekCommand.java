//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import kurohack.features.modules.misc.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;

public class PeekCommand extends Command
{
    public PeekCommand() {
        super("peek", new String[] { "<player>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            final ItemStack stack = PeekCommand.mc.player.getHeldItemMainhand();
            if (stack == null || !(stack.getItem() instanceof ItemShulkerBox)) {
                Command.sendMessage("§cYou need to hold a Shulker in your mainhand.");
                return;
            }
            ToolTips.displayInv(stack, null);
        }
        if (commands.length > 1) {
            if (ToolTips.getInstance().isOn() && ToolTips.getInstance().shulkerSpy.getValue()) {
                for (final Map.Entry<EntityPlayer, ItemStack> entry : ToolTips.getInstance().spiedPlayers.entrySet()) {
                    if (!entry.getKey().getName().equalsIgnoreCase(commands[0])) {
                        continue;
                    }
                    final ItemStack stack2 = entry.getValue();
                    ToolTips.displayInv(stack2, entry.getKey().getName());
                    break;
                }
            }
            else {
                Command.sendMessage("§cYou need to turn on Tooltips - ShulkerSpy");
            }
        }
    }
}
