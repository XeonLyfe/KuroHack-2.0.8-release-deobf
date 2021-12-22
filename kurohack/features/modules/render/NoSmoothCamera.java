//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoSmoothCamera extends Module
{
    public NoSmoothCamera() {
        super("NoSmoothCamera", "aaa", Module.Category.RENDER, true, false, false);
    }
    
    @SubscribeEvent
    public void onUpdate(final EventPreMotionUpdate event) {
        NoSmoothCamera.mc.gameSettings.smoothCamera = false;
    }
}
