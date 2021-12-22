//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.util.element.*;

public class Media extends Module
{
    private static Media instance;
    public final Setting<Boolean> changeOwn;
    public final Setting<String> ownName;
    
    public Media() {
        super("Media", "Helps with creating Media", Category.MISC, false, false, false);
        this.changeOwn = (Setting<Boolean>)this.register(new Setting("MyNameIs", (T)true));
        this.ownName = (Setting<String>)this.register(new Setting("Name", (T)"Type Your Name U Want Change here...", v -> this.changeOwn.getValue()));
        Media.instance = this;
    }
    
    public static Media getInstance() {
        if (Media.instance == null) {
            Media.instance = new Media();
        }
        return Media.instance;
    }
    
    public static String getPlayerName() {
        if (fullNullCheck() || !ServerModule.getInstance().isConnected()) {
            return IUtil.mc.getSession().getUsername();
        }
        final String name = ServerModule.getInstance().getPlayerName();
        if (name == null || name.isEmpty()) {
            return IUtil.mc.getSession().getUsername();
        }
        return name;
    }
}
