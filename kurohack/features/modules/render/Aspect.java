//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Aspect extends Module
{
    public Setting<Float> aspect;
    
    public Aspect() {
        super("Aspect", "Cool.", Module.Category.RENDER, true, false, false);
        this.aspect = (Setting<Float>)this.register(new Setting("Alpha", (T)1.0f, (T)0.1f, (T)5.0f));
    }
    
    @SubscribeEvent
    public void onPerspectiveEvent(final PerspectiveEvent perspectiveEvent) {
        perspectiveEvent.setAspect((float)this.aspect.getValue());
    }
}
