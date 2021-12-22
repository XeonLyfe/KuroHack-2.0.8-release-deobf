//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AirJump extends Module
{
    public AirJump() {
        super("AirJump", "Wtf", Module.Category.MOVEMENT, true, false, false);
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        AirJump.mc.player.onGround = true;
    }
    
    public void onDisable() {
        super.onDisable();
        AirJump.mc.player.onGround = false;
    }
}
