//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;

public class Suicide extends Module
{
    public Suicide() {
        super("Suicide", "Suicide", Category.MISC, true, false, false);
    }
    
    @Override
    public void onEnable() {
        if (Suicide.mc.player != null) {
            Suicide.mc.player.sendChatMessage("/kill");
        }
        this.toggle();
    }
}
