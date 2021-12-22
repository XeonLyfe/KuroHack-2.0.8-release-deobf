//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class Chams extends Module
{
    private static Chams INSTANCE;
    public Setting<Boolean> colorSync;
    public Setting<Boolean> colored;
    public Setting<Boolean> textured;
    public Setting<Boolean> rainbow;
    public Setting<Integer> saturation;
    public Setting<Integer> brightness;
    public Setting<Integer> speed;
    public Setting<Boolean> xqz;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Integer> hiddenRed;
    public Setting<Integer> hiddenGreen;
    public Setting<Integer> hiddenBlue;
    public Setting<Integer> hiddenAlpha;
    
    public Chams() {
        super("Chams", "Renders players through walls.", Module.Category.RENDER, false, false, false);
        this.colorSync = (Setting<Boolean>)this.register(new Setting("Sync", (T)false));
        this.colored = (Setting<Boolean>)this.register(new Setting("Colored", (T)false));
        this.textured = (Setting<Boolean>)this.register(new Setting("Textured", (T)false));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false, v -> this.colored.getValue()));
        this.saturation = (Setting<Integer>)this.register(new Setting("Saturation", (T)50, (T)0, (T)100, v -> this.colored.getValue() && this.rainbow.getValue()));
        this.brightness = (Setting<Integer>)this.register(new Setting("Brightness", (T)100, (T)0, (T)100, v -> this.colored.getValue() && this.rainbow.getValue()));
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (T)40, (T)1, (T)100, v -> this.colored.getValue() && this.rainbow.getValue()));
        this.xqz = (Setting<Boolean>)this.register(new Setting("XQZ", (T)false, v -> this.colored.getValue() && !this.rainbow.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255, v -> this.colored.getValue() && !this.rainbow.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255, v -> this.colored.getValue() && !this.rainbow.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255, v -> this.colored.getValue() && !this.rainbow.getValue()));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255, v -> this.colored.getValue()));
        this.hiddenRed = (Setting<Integer>)this.register(new Setting("Hidden Red", (T)255, (T)0, (T)255, v -> this.colored.getValue() && this.xqz.getValue() && !this.rainbow.getValue()));
        this.hiddenGreen = (Setting<Integer>)this.register(new Setting("Hidden Green", (T)0, (T)0, (T)255, v -> this.colored.getValue() && this.xqz.getValue() && !this.rainbow.getValue()));
        this.hiddenBlue = (Setting<Integer>)this.register(new Setting("Hidden Blue", (T)255, (T)0, (T)255, v -> this.colored.getValue() && this.xqz.getValue() && !this.rainbow.getValue()));
        this.hiddenAlpha = (Setting<Integer>)this.register(new Setting("Hidden Alpha", (T)255, (T)0, (T)255, v -> this.colored.getValue() && this.xqz.getValue() && !this.rainbow.getValue()));
        this.setInstance();
    }
    
    public static Chams getInstance() {
        if (Chams.INSTANCE == null) {
            Chams.INSTANCE = new Chams();
        }
        return Chams.INSTANCE;
    }
    
    private void setInstance() {
        Chams.INSTANCE = this;
    }
    
    static {
        Chams.INSTANCE = new Chams();
    }
}
