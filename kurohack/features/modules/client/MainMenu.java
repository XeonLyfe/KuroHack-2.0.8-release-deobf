//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class MainMenu extends Module
{
    private static MainMenu INSTANCE;
    public Setting<Boolean> mainScreen;
    public Setting<Boolean> particles;
    
    public MainMenu() {
        super("MainMenuScreen", "Toggles MainMenuScreen", Category.CLIENT, true, false, false);
        this.mainScreen = (Setting<Boolean>)this.register(new Setting("MainScreen", (T)true));
        this.particles = (Setting<Boolean>)this.register(new Setting("Particles", (T)true));
        this.setInstance();
    }
    
    public static MainMenu getInstance() {
        if (MainMenu.INSTANCE == null) {
            MainMenu.INSTANCE = new MainMenu();
        }
        return MainMenu.INSTANCE;
    }
    
    private void setInstance() {
        MainMenu.INSTANCE = this;
    }
    
    static {
        MainMenu.INSTANCE = new MainMenu();
    }
}
