//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;
import kurohack.features.setting.*;

public class ValueChangeEvent extends EventStage
{
    public Setting setting;
    public Object value;
    
    public ValueChangeEvent(final Setting setting, final Object value) {
        this.setting = setting;
        this.value = value;
    }
}
