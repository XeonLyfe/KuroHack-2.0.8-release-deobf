//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.clickgui2;

public abstract class Component
{
    protected abstract void updateComponent();
    
    protected abstract void render();
    
    protected abstract boolean mouseClicked(final int p0, final int p1, final int p2);
    
    protected abstract boolean mouseReleased(final int p0, final int p1, final int p2);
    
    protected abstract boolean keyTyped(final char p0, final int p1);
}
