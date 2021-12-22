//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.*;
import net.minecraft.entity.*;

public class ReverseStep extends Module
{
    private final Setting<Integer> speed;
    private final Setting<Boolean> inliquid;
    private final Setting<Cancel> canceller;
    private static ReverseStep INSTANCE;
    
    public ReverseStep() {
        super("ReverseStep", "rs", Module.Category.MOVEMENT, true, false, false);
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (T)8, (T)1, (T)20));
        this.inliquid = (Setting<Boolean>)this.register(new Setting("Liquid", (T)false));
        this.canceller = (Setting<Cancel>)this.register(new Setting("CancelType", (T)Cancel.None));
        this.setInstance();
    }
    
    public static ReverseStep getInstance() {
        if (ReverseStep.INSTANCE == null) {
            ReverseStep.INSTANCE = new ReverseStep();
        }
        return ReverseStep.INSTANCE;
    }
    
    private void setInstance() {
        ReverseStep.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        if (ReverseStep.mc.player.isSneaking() || ReverseStep.mc.player.isDead || ReverseStep.mc.player.collidedHorizontally || !ReverseStep.mc.player.onGround || (ReverseStep.mc.player.isInWater() && !this.inliquid.getValue()) || (ReverseStep.mc.player.isInLava() && !this.inliquid.getValue()) || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() || KuroHack.moduleManager.isModuleEnabled("Burrow") || ReverseStep.mc.player.noClip || KuroHack.moduleManager.isModuleEnabled("Packetfly") || KuroHack.moduleManager.isModuleEnabled("Phase") || (ReverseStep.mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Shift) || (ReverseStep.mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Both) || (ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Space) || (ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Both) || KuroHack.moduleManager.isModuleEnabled("Strafe")) {
            return;
        }
        for (double y = 0.0; y < 90.5; y += 0.01) {
            if (!ReverseStep.mc.world.getCollisionBoxes((Entity)ReverseStep.mc.player, ReverseStep.mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                ReverseStep.mc.player.motionY = -this.speed.getValue() / 10.0f;
                break;
            }
        }
    }
    
    static {
        ReverseStep.INSTANCE = new ReverseStep();
    }
    
    public enum Cancel
    {
        None, 
        Space, 
        Shift, 
        Both;
    }
}
