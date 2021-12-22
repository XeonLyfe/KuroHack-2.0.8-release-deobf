//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.event.events;

import kurohack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

@Cancelable
public class JesusEvent extends EventStage
{
    private BlockPos pos;
    private AxisAlignedBB boundingBox;
    
    public JesusEvent(final int stage, final BlockPos pos) {
        super(stage);
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}
