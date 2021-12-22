//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;

public class ItemPhysics extends Module
{
    public static ItemPhysics INSTANCE;
    public final Setting<Float> Scaling;
    
    public ItemPhysics() {
        super("ItemPhysics", "Apply physics to items.", Module.Category.RENDER, false, false, false);
        this.Scaling = (Setting<Float>)this.register(new Setting("Scaling", (T)0.5f, (T)0.0f, (T)10.0f));
        this.setInstance();
    }
    
    public static ItemPhysics getInstance() {
        if (ItemPhysics.INSTANCE == null) {
            ItemPhysics.INSTANCE = new ItemPhysics();
        }
        return ItemPhysics.INSTANCE;
    }
    
    private void setInstance() {
        ItemPhysics.INSTANCE = this;
    }
    
    static {
        ItemPhysics.INSTANCE = new ItemPhysics();
    }
}
