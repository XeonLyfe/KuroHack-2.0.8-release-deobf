//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.util;

public class PairUtil<F, S>
{
    private F first;
    private S second;
    
    public PairUtil(final F f, final S s) {
        this.first = f;
        this.second = s;
    }
    
    public F getFirst() {
        return this.first;
    }
    
    public void setFirst(final F f) {
        this.first = f;
    }
    
    public S getSecond() {
        return this.second;
    }
    
    public void setSecond(final S s) {
        this.second = s;
    }
}
