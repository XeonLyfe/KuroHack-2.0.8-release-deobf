//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command.commands;

import kurohack.features.command.*;
import net.minecraft.client.audio.*;
import kurohack.util.element.*;
import net.minecraftforge.fml.common.*;

public class ReloadSoundCommand extends Command
{
    public ReloadSoundCommand() {
        super("sound", new String[0]);
    }
    
    public void execute(final String[] commands) {
        try {
            final SoundManager sndManager = (SoundManager)ObfuscationReflectionHelper.getPrivateValue((Class)SoundHandler.class, (Object)IUtil.mc.getSoundHandler(), new String[] { "sndManager", "sndManager" });
            sndManager.reloadSoundSystem();
            sendMessage("§aReloaded Sound System.");
        }
        catch (Exception e) {
            System.out.println("Could not restart sound manager: " + e.toString());
            e.printStackTrace();
            sendMessage("§cCouldnt Reload Sound System!");
        }
    }
}
