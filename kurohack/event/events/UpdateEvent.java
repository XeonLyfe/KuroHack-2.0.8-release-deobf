//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;

public class UpdateEvent extends EventStage
{
    private final int stage;
    
    public UpdateEvent(final int stage) {
        this.stage = stage;
    }
    
    @Override
    public final int getStage() {
        return this.stage;
    }
}
