//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class FriendSettings extends Module
{
    private static FriendSettings INSTANCE;
    public Setting<Boolean> notify;
    
    public FriendSettings() {
        super("FriendSettings", "Change aspects of friends", Category.MISC, true, false, false);
        this.notify = (Setting<Boolean>)this.register(new Setting("Speed", "Notify", 0.0, 0.0, (T)false, 0));
        FriendSettings.INSTANCE = this;
    }
    
    public static FriendSettings getInstance() {
        if (FriendSettings.INSTANCE == null) {
            FriendSettings.INSTANCE = new FriendSettings();
        }
        return FriendSettings.INSTANCE;
    }
}
