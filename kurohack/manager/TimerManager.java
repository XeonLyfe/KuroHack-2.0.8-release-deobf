//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import kurohack.features.modules.player.*;
import kurohack.*;

public class TimerManager extends Feature
{
    private float timer;
    private TimerSpeed module;
    
    public TimerManager() {
        this.timer = 1.0f;
    }
    
    public void init() {
        this.module = (TimerSpeed)KuroHack.moduleManager.getModuleByClass((Class)TimerSpeed.class);
    }
    
    public void unload() {
        this.timer = 1.0f;
        TimerManager.mc.timer.tickLength = 50.0f;
    }
    
    public void update() {
        if (this.module != null && this.module.isEnabled()) {
            this.timer = this.module.speed;
        }
        TimerManager.mc.timer.tickLength = 50.0f / ((this.timer <= 0.0f) ? 0.1f : this.timer);
    }
    
    public void setTimer(final float timer) {
        if (timer > 0.0f) {
            this.timer = timer;
        }
    }
    
    public float getTimer() {
        return this.timer;
    }
    
    public void reset() {
        this.timer = 1.0f;
    }
}
