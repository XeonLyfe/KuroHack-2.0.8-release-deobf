//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.HUD;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class ModuleTools extends Module
{
    private static ModuleTools INSTANCE;
    public Setting<Notifier> notifier;
    public Setting<PopNotifier> popNotifier;
    
    public ModuleTools() {
        super("ModuleTools", "Change settings", Category.HUD, true, false, false);
        this.notifier = (Setting<Notifier>)this.register(new Setting("ModuleNotifier", (T)Notifier.PHOBOS));
        this.popNotifier = (Setting<PopNotifier>)this.register(new Setting("PopNotifier", (T)PopNotifier.KUROHACK));
        ModuleTools.INSTANCE = this;
    }
    
    public static ModuleTools getInstance() {
        if (ModuleTools.INSTANCE == null) {
            ModuleTools.INSTANCE = new ModuleTools();
        }
        return ModuleTools.INSTANCE;
    }
    
    public enum Notifier
    {
        PHOBOS, 
        KUROHACK;
    }
    
    public enum PopNotifier
    {
        PHOBOS, 
        KUROHACK, 
        NONE;
    }
}
