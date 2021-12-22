//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;

public class KeyEvent extends EventStage
{
    public boolean info;
    public boolean pressed;
    
    public KeyEvent(final int stage, final boolean info, final boolean pressed) {
        super(stage);
        this.info = info;
        this.pressed = pressed;
    }
}
