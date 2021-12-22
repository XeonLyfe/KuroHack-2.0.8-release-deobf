//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import java.awt.*;
import kurohack.features.command.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.*;

public class FontMod extends Module
{
    private static FontMod INSTANCE;
    public Setting<String> fontName;
    public Setting<Integer> fontSize;
    public Setting<Integer> fontStyle;
    public Setting<Boolean> antiAlias;
    public Setting<Boolean> fractionalMetrics;
    public Setting<Boolean> shadow;
    public Setting<Boolean> showFonts;
    public Setting<Boolean> full;
    private boolean reloadFont;
    
    public FontMod() {
        super("Font", "CustomFont for all of the clients text. Use the font command.", Category.CLIENT, true, false, false);
        this.fontName = (Setting<String>)this.register(new Setting("FontName", (T)"Arial", "Name of the font."));
        this.fontSize = (Setting<Integer>)this.register(new Setting("FontSize", (T)18, "Size of the font."));
        this.fontStyle = (Setting<Integer>)this.register(new Setting("FontStyle", (T)0, "Style of the font."));
        this.antiAlias = (Setting<Boolean>)this.register(new Setting("AntiAlias", (T)true, "Smoother font."));
        this.fractionalMetrics = (Setting<Boolean>)this.register(new Setting("Metrics", (T)true, "Thinner font."));
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)true, "Less shadow offset font."));
        this.showFonts = (Setting<Boolean>)this.register(new Setting("Fonts", (T)false, "Shows all fonts."));
        this.full = (Setting<Boolean>)this.register(new Setting("Speed", "Full", 0.0, 0.0, (T)false, 0));
        this.reloadFont = false;
        this.setInstance();
    }
    
    public static FontMod getInstance() {
        if (FontMod.INSTANCE == null) {
            FontMod.INSTANCE = new FontMod();
        }
        return FontMod.INSTANCE;
    }
    
    public static boolean checkFont(final String font, final boolean message) {
        final String[] availableFontFamilyNames;
        final String[] fonts = availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (final String s : availableFontFamilyNames) {
            if (!message && s.equals(font)) {
                return true;
            }
            if (message) {
                Command.sendMessage(s);
            }
        }
        return false;
    }
    
    private void setInstance() {
        FontMod.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        final Setting setting;
        if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
            if (setting.getName().equals("FontName") && !checkFont(setting.getPlannedValue().toString(), false)) {
                Command.sendMessage("§cThat font doesnt exist.");
                event.setCanceled(true);
                return;
            }
            this.reloadFont = true;
        }
    }
    
    @Override
    public void onTick() {
        if (this.showFonts.getValue()) {
            checkFont("Hello", true);
            Command.sendMessage("Current Font: " + this.fontName.getValue());
            this.showFonts.setValue(false);
        }
        if (this.reloadFont) {
            KuroHack.textManager.init(false);
            this.reloadFont = false;
        }
    }
    
    static {
        FontMod.INSTANCE = new FontMod();
    }
}
