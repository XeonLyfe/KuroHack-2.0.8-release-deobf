//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import kurohack.*;
import com.mojang.realmsclient.gui.*;

public class ExtraTab extends Module
{
    private static ExtraTab INSTANCE;
    public Setting<Integer> size;
    
    public ExtraTab() {
        super("ExtraTab", "Extends Tab.", Category.MISC, true, false, false);
        this.size = (Setting<Integer>)this.register(new Setting("Size", (T)250, (T)1, (T)1000));
        this.setInstance();
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String string;
        final String name = string = ((networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName()));
        if (KuroHack.friendManager.isFriend(name)) {
            return ChatFormatting.AQUA + name;
        }
        return name;
    }
    
    public static ExtraTab getINSTANCE() {
        if (ExtraTab.INSTANCE == null) {
            ExtraTab.INSTANCE = new ExtraTab();
        }
        return ExtraTab.INSTANCE;
    }
    
    private void setInstance() {
        ExtraTab.INSTANCE = this;
    }
    
    static {
        ExtraTab.INSTANCE = new ExtraTab();
    }
}
