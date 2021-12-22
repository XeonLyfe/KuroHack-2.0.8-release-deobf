//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class CameraClip extends Module
{
    private static CameraClip INSTANCE;
    public Setting<Boolean> extend;
    public Setting<Double> distance;
    
    public CameraClip() {
        super("CameraClip", "Makes your Camera clip.", Module.Category.RENDER, false, false, false);
        this.extend = (Setting<Boolean>)this.register(new Setting("Speed", "Extend", 0.0, 0.0, (T)false, 0));
        this.distance = (Setting<Double>)this.register(new Setting("Distance", (T)10.0, (T)0.0, (T)50.0, v -> this.extend.getValue(), "By how much you want to extend the distance."));
        this.setInstance();
    }
    
    public static CameraClip getInstance() {
        if (CameraClip.INSTANCE == null) {
            CameraClip.INSTANCE = new CameraClip();
        }
        return CameraClip.INSTANCE;
    }
    
    private void setInstance() {
        CameraClip.INSTANCE = this;
    }
    
    static {
        CameraClip.INSTANCE = new CameraClip();
    }
}
