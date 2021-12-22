//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import com.mojang.text2speech.*;

public class Sex extends Module
{
    private final Narrator narrator;
    
    public Sex() {
        super("Sex!", "narrator but sex", Category.MISC, true, false, false);
        this.narrator = Narrator.getNarrator();
    }
    
    @Override
    public void onEnable() {
        if (this.isNull()) {
            return;
        }
        this.narrator.say("Sex Enable!");
        this.narrator.say("Can we have sex?");
        this.narrator.say("Look at our ass");
        this.narrator.say("so smooth!");
        this.narrator.say("dont disable me");
        this.narrator.say("Sex!");
    }
    
    @Override
    public void onDisable() {
        if (this.isNull()) {
            return;
        }
        this.narrator.clear();
    }
}
