//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.*;
import kurohack.util.*;
import net.minecraft.entity.*;

public class YPort extends Module
{
    public Setting<Boolean> useTimer;
    private final Setting<Double> yPortSpeed;
    public Setting<Boolean> stepyport;
    private Timer timer;
    private float stepheight;
    
    public YPort() {
        super("YPort", "yp", Module.Category.MOVEMENT, true, false, false);
        this.useTimer = (Setting<Boolean>)this.register(new Setting("UseTimer", (T)false));
        this.yPortSpeed = (Setting<Double>)this.register(new Setting("Speed", (T)0.1, (T)0.0, (T)1.0));
        this.stepyport = (Setting<Boolean>)this.register(new Setting("Step", (T)true));
        this.timer = new Timer();
        this.stepheight = 2.0f;
    }
    
    public void onDisable() {
        this.timer.reset();
        EntityUtil.resetTimer();
    }
    
    public void onUpdate() {
        if (YPort.mc.player.isSneaking() || YPort.mc.player.isInWater() || YPort.mc.player.isInLava() || YPort.mc.player.isOnLadder() || KuroHack.moduleManager.isModuleEnabled("Strafe")) {
            return;
        }
        if (YPort.mc.player == null || YPort.mc.world == null) {
            this.disable();
            return;
        }
        this.handleYPortSpeed();
        if ((!YPort.mc.player.isOnLadder() || YPort.mc.player.isInWater() || YPort.mc.player.isInLava()) && this.stepyport.getValue()) {
            Step.mc.player.stepHeight = this.stepheight;
        }
    }
    
    public void onToggle() {
        Step.mc.player.stepHeight = 0.6f;
        YPort.mc.player.motionY = -3.0;
    }
    
    private void handleYPortSpeed() {
        if (!MotionUtil.isMoving((EntityLivingBase)YPort.mc.player) || (YPort.mc.player.isInWater() && YPort.mc.player.isInLava()) || YPort.mc.player.collidedHorizontally) {
            return;
        }
        if (YPort.mc.player.onGround) {
            if (this.useTimer.getValue()) {
                EntityUtil.setTimer(1.15f);
            }
            YPort.mc.player.jump();
            MotionUtil.setSpeed((EntityLivingBase)YPort.mc.player, MotionUtil.getBaseMoveSpeed() + this.yPortSpeed.getValue());
        }
        else {
            YPort.mc.player.motionY = -1.0;
            EntityUtil.resetTimer();
        }
    }
}
