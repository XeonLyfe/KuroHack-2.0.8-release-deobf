//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.*;

public class RPC extends Module
{
    public static RPC INSTANCE;
    public Setting<Boolean> showIP;
    public Setting<Boolean> users;
    
    public RPC() {
        super("RPC", "Discord rich presence", Category.MISC, false, false, false);
        this.showIP = (Setting<Boolean>)this.register(new Setting("IP", (T)false));
        this.users = (Setting<Boolean>)this.register(new Setting("Users", (T)false));
        RPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        DiscordPresence.start();
    }
    
    @Override
    public void onDisable() {
        DiscordPresence.stop();
    }
}
