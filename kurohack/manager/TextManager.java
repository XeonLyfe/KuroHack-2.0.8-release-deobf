//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import kurohack.features.gui.font.*;
import kurohack.features.modules.client.*;
import kurohack.*;
import java.awt.*;
import kurohack.util.*;
import net.minecraft.util.math.*;

public class TextManager extends Feature
{
    private final Timer idleTimer;
    public int scaledWidth;
    public int scaledHeight;
    public int scaleFactor;
    private CustomFont customFont;
    private final CustomFont headerFont;
    private final CustomFont smallString;
    private boolean idling;
    
    public TextManager() {
        this.idleTimer = new Timer();
        this.customFont = new CustomFont(new Font("Verdana", 0, 17), true, false);
        this.headerFont = new CustomFont(new Font("Tahoma", 1, 40), true, false);
        this.smallString = new CustomFont(new Font("tahoma", 1, 12), true, false);
        this.updateResolution();
    }
    
    public void init(final boolean startup) {
        final FontMod cFont = (FontMod)KuroHack.moduleManager.getModuleByClass((Class)FontMod.class);
        try {
            this.setFontRenderer(new Font((String)cFont.fontName.getValue(), (int)cFont.fontStyle.getValue(), (int)cFont.fontSize.getValue()), (boolean)cFont.antiAlias.getValue(), (boolean)cFont.fractionalMetrics.getValue());
        }
        catch (Exception ex) {}
    }
    
    public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(text, x, y, color, true);
    }
    
    public float drawString(final String text, final float x, final float y, final int color, final boolean shadow) {
        if (!KuroHack.moduleManager.isModuleEnabled((Class)FontMod.class)) {
            return (float)TextManager.mc.fontRenderer.drawString(text, x, y, color, shadow);
        }
        if (shadow) {
            return this.customFont.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return this.customFont.drawString(text, x, y, color);
    }
    
    public void drawRainbowString(final String text, final float x, final float y, final int startColor, final float factor, final boolean shadow) {
        Color currentColor = new Color(startColor);
        final float hueIncrement = 1.0f / factor;
        final String[] rainbowStrings = text.split("§.");
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        final float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        final float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            final char currentChar = text.charAt(i);
            final char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                shouldRainbow = false;
            }
            else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else {
                if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                    final String escapeString = text.substring(i);
                    this.drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
                    break;
                }
                this.drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow);
                if (String.valueOf(currentChar).equals("§")) {
                    shouldContinue = true;
                }
                currentWidth += this.getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
                    currentHue += hueIncrement;
                }
            }
        }
    }
    
    public int getStringWidth(final String text) {
        if (KuroHack.moduleManager.isModuleEnabled((Class)FontMod.class)) {
            return this.customFont.getStringWidth(text);
        }
        return TextManager.mc.fontRenderer.getStringWidth(text);
    }
    
    public int getFontHeight() {
        if (KuroHack.moduleManager.isModuleEnabled((Class)FontMod.class)) {
            final String text = "A";
            return this.customFont.getStringHeight(text);
        }
        return TextManager.mc.fontRenderer.FONT_HEIGHT;
    }
    
    public void setFontRenderer(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        this.customFont = new CustomFont(font, antiAlias, fractionalMetrics);
    }
    
    public Font getCurrentFont() {
        return this.customFont.getFont();
    }
    
    public void updateResolution() {
        this.scaledWidth = TextManager.mc.displayWidth;
        this.scaledHeight = TextManager.mc.displayHeight;
        this.scaleFactor = 1;
        final boolean flag = TextManager.mc.isUnicode();
        int i = TextManager.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        final double scaledWidthD = this.scaledWidth / (double)this.scaleFactor;
        final double scaledHeightD = this.scaledHeight / (double)this.scaleFactor;
        this.scaledWidth = MathHelper.ceil(scaledWidthD);
        this.scaledHeight = MathHelper.ceil(scaledHeightD);
    }
    
    public String getIdleSign() {
        if (this.idleTimer.passedMs(500L)) {
            this.idling = !this.idling;
            this.idleTimer.reset();
        }
        if (this.idling) {
            return "_";
        }
        return "";
    }
    
    public float drawStringBig(final String string, final float x, final float y, final int colour, final boolean shadow) {
        if (shadow) {
            return this.headerFont.drawStringWithShadow(string, (double)x, (double)y, colour);
        }
        return this.headerFont.drawString(string, x, y, colour);
    }
    
    public float drawStringSmall(final String string, final float x, final float y, final int colour, final boolean shadow) {
        if (shadow) {
            return this.smallString.drawStringWithShadow(string, (double)x, (double)y, colour);
        }
        return this.smallString.drawString(string, x, y, colour);
    }
    
    public static void drawStringWithShadow(final String text, final float x, final float y, final int colour, final boolean shadow) {
        if (!shadow) {
            TextManager.mc.fontRenderer.drawStringWithShadow(text, (float)(int)x, (float)(int)y, colour);
        }
    }
    
    public static void drawString(final String text, final float x, final float y, final int color) {
        if (KuroHack.moduleManager.isModuleEnabled((Class)FontMod.class)) {
            TextManager.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
    }
}
