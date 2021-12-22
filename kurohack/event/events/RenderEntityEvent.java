//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;
import net.minecraft.entity.*;

public abstract class RenderEntityEvent extends EventStage
{
    private final Entity entity;
    private final double x;
    private final double y;
    private final double z;
    
    private RenderEntityEvent(final Entity entity, final double x, final double y, final double z) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public static class Pre extends RenderEntityEvent
    {
        public Pre(final Entity entity, final double x, final double y, final double z) {
            super(entity, x, y, z, null);
        }
    }
    
    public static class Post extends RenderEntityEvent
    {
        public Post(final Entity entity, final double x, final double y, final double z) {
            super(entity, x, y, z, null);
        }
    }
}
