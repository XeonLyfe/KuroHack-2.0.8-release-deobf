//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins.accessors;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketSetSlot.class })
public interface ISPacketSetSlot
{
    @Accessor("windowId")
    int getId();
    
    @Accessor("windowId")
    void setWindowId(final int p0);
}
