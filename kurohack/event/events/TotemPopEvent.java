//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;
import net.minecraft.entity.player.*;

public class TotemPopEvent extends EventStage
{
    private final EntityPlayer entity;
    private final int entId;
    
    public TotemPopEvent(final EntityPlayer entity, final int entId) {
        this.entity = entity;
        this.entId = entId;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
    
    public int getEntityId() {
        return this.entId;
    }
}
