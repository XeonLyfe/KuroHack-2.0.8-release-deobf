//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Sprint extends Module
{
    public Setting<Mode> mode;
    private static Sprint INSTANCE;
    
    public Sprint() {
        super("Sprint", "Modifies sprinting", Module.Category.MOVEMENT, false, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Speed", "Mode", 0.0, 0.0, (T)Mode.LEGIT, 0));
        this.setInstance();
    }
    
    private void setInstance() {
        Sprint.INSTANCE = this;
    }
    
    public static Sprint getInstance() {
        if (Sprint.INSTANCE == null) {
            Sprint.INSTANCE = new Sprint();
        }
        return Sprint.INSTANCE;
    }
    
    @SubscribeEvent
    public void onSprint(final MoveEvent event) {
        if (event.getStage() == 1 && this.mode.getValue() == Mode.RAGE && (Sprint.mc.player.movementInput.moveForward != 0.0f || Sprint.mc.player.movementInput.moveStrafe != 0.0f)) {
            event.setCanceled(true);
        }
    }
    
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case RAGE: {
                if ((!Sprint.mc.gameSettings.keyBindForward.isKeyDown() && !Sprint.mc.gameSettings.keyBindBack.isKeyDown() && !Sprint.mc.gameSettings.keyBindLeft.isKeyDown() && !Sprint.mc.gameSettings.keyBindRight.isKeyDown()) || Sprint.mc.player.isSneaking() || Sprint.mc.player.collidedHorizontally) {
                    break;
                }
                if (Sprint.mc.player.getFoodStats().getFoodLevel() <= 6.0f) {
                    break;
                }
                Sprint.mc.player.setSprinting(true);
                break;
            }
            case LEGIT: {
                if (!Sprint.mc.gameSettings.keyBindForward.isKeyDown() || Sprint.mc.player.isSneaking() || Sprint.mc.player.isHandActive() || Sprint.mc.player.collidedHorizontally || Sprint.mc.player.getFoodStats().getFoodLevel() <= 6.0f) {
                    break;
                }
                if (Sprint.mc.currentScreen != null) {
                    break;
                }
                Sprint.mc.player.setSprinting(true);
                break;
            }
        }
    }
    
    public void onDisable() {
        if (!nullCheck()) {
            Sprint.mc.player.setSprinting(false);
        }
    }
    
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    static {
        Sprint.INSTANCE = new Sprint();
    }
    
    public enum Mode
    {
        LEGIT, 
        RAGE;
    }
}
