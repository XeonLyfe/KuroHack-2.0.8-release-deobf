//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import org.lwjgl.input.*;

public class ThirdPerson extends Module
{
    public Setting<Boolean> onlyHold;
    public Setting<Bind> bind;
    
    public ThirdPerson() {
        super("ThirdPerson", "Third person camera but hold bind.", Category.RENDER, true, false, false);
        this.onlyHold = (Setting<Boolean>)this.register(new Setting("OnlyHoldBind", (T)false));
        this.bind = (Setting<Bind>)this.register(new Setting("Bind:", (T)new Bind(-1)));
    }
    
    @Override
    public void onUpdate() {
        if (this.bind.getCurrentState().getKey() > -1) {
            if (Keyboard.isKeyDown(this.bind.getCurrentState().getKey())) {
                ThirdPerson.mc.gameSettings.thirdPersonView = 1;
            }
            else {
                ThirdPerson.mc.gameSettings.thirdPersonView = 0;
            }
        }
    }
    
    @Override
    public void onEnable() {
        if (!this.onlyHold.getCurrentState()) {
            ThirdPerson.mc.gameSettings.thirdPersonView = 1;
        }
    }
    
    @Override
    public void onDisable() {
        if (!this.onlyHold.getCurrentState()) {
            ThirdPerson.mc.gameSettings.thirdPersonView = 0;
        }
    }
}
