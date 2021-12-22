//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.util.*;

public class VanillaSpeed extends Module
{
    public Setting<Double> speed;
    
    public VanillaSpeed() {
        super("VanillaSpeed", "ec.me", Module.Category.MOVEMENT, true, false, false);
        this.speed = (Setting<Double>)this.register(new Setting("Speed", (T)1.0, (T)1.0, (T)10.0));
    }
    
    public void onUpdate() {
        if (VanillaSpeed.mc.player == null || VanillaSpeed.mc.world == null) {
            return;
        }
        final double[] calc = MathUtil.directionSpeed(this.speed.getValue() / 10.0);
        VanillaSpeed.mc.player.motionX = calc[0];
        VanillaSpeed.mc.player.motionZ = calc[1];
    }
}
