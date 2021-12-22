//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class SmallShield extends Module
{
    private static SmallShield INSTANCE;
    public Setting<Boolean> normalOffset;
    public Setting<Float> offset;
    public Setting<Float> offX;
    public Setting<Float> offY;
    public Setting<Float> mainX;
    public Setting<Float> mainY;
    
    public SmallShield() {
        super("SmallShield", "Makes you offhand lower.", Module.Category.RENDER, false, false, false);
        this.normalOffset = (Setting<Boolean>)this.register(new Setting("Speed", "OffNormal", 0.0, 0.0, (T)false, 0));
        this.offset = (Setting<Float>)this.register(new Setting("Offset", (T)0.7f, (T)0.0f, (T)1.0f, v -> this.normalOffset.getValue()));
        this.offX = (Setting<Float>)this.register(new Setting("OffX", (T)0.0f, (T)(-1.0f), (T)1.0f, v -> !this.normalOffset.getValue()));
        this.offY = (Setting<Float>)this.register(new Setting("OffY", (T)0.0f, (T)(-1.0f), (T)1.0f, v -> !this.normalOffset.getValue()));
        this.mainX = (Setting<Float>)this.register(new Setting("MainX", (T)0.0f, (T)(-1.0f), (T)1.0f));
        this.mainY = (Setting<Float>)this.register(new Setting("MainY", (T)0.0f, (T)(-1.0f), (T)1.0f));
        this.setInstance();
    }
    
    public static SmallShield getINSTANCE() {
        if (SmallShield.INSTANCE == null) {
            SmallShield.INSTANCE = new SmallShield();
        }
        return SmallShield.INSTANCE;
    }
    
    private void setInstance() {
        SmallShield.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (this.normalOffset.getValue()) {
            SmallShield.mc.entityRenderer.itemRenderer.equippedProgressOffHand = this.offset.getValue();
        }
    }
    
    static {
        SmallShield.INSTANCE = new SmallShield();
    }
}
