//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class Particles extends Module
{
    private static Particles INSTANCE;
    public Setting<Integer> particleLength;
    public Setting<Integer> particlered;
    public Setting<Integer> particlegreen;
    public Setting<Integer> particleblue;
    
    public Particles() {
        super("Particles", "Sex", Category.CLIENT, true, false, false);
        this.particleLength = (Setting<Integer>)this.register(new Setting("ParticleLength", (T)80, (T)0, (T)300));
        this.particlered = (Setting<Integer>)this.register(new Setting("ParticleRed", (T)0, (T)0, (T)255));
        this.particlegreen = (Setting<Integer>)this.register(new Setting("ParticleGreen", (T)255, (T)0, (T)255));
        this.particleblue = (Setting<Integer>)this.register(new Setting("ParticleBlue", (T)255, (T)0, (T)255));
        this.setInstance();
    }
    
    public static Particles getInstance() {
        if (Particles.INSTANCE == null) {
            Particles.INSTANCE = new Particles();
        }
        return Particles.INSTANCE;
    }
    
    private void setInstance() {
        Particles.INSTANCE = this;
    }
    
    static {
        Particles.INSTANCE = new Particles();
    }
}
