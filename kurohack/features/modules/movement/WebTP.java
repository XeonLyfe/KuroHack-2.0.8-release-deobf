//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.client.entity.*;

public class WebTP extends Module
{
    private final Setting<Mode> mode;
    
    public WebTP() {
        super("WebTP", "", Module.Category.MOVEMENT, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Vanilla));
    }
    
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        if (WebTP.mc.player.isInWeb) {
            switch (this.mode.getValue()) {
                case Normal: {
                    for (int i = 0; i < 10; ++i) {
                        final EntityPlayerSP player = WebTP.mc.player;
                        --player.motionY;
                    }
                    break;
                }
                case Vanilla: {
                    WebTP.mc.player.isInWeb = false;
                    break;
                }
            }
        }
    }
    
    public enum Mode
    {
        Normal, 
        Vanilla;
    }
}
