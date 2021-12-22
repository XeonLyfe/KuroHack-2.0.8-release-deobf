//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Gamemode extends Module
{
    public Gamemode() {
        super("FakeGamemode", "fake gamemode", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Gamemode.mc.player == null) {
            return;
        }
        Minecraft.getMinecraft();
        Gamemode.mc.playerController.setGameType(GameType.CREATIVE);
    }
    
    @Override
    public void onDisable() {
        if (Gamemode.mc.player == null) {
            return;
        }
        Gamemode.mc.playerController.setGameType(GameType.SURVIVAL);
    }
}
