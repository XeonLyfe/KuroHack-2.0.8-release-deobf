//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.clickgui2;

import net.minecraft.client.gui.*;
import net.minecraftforge.common.*;
import kurohack.util.element.*;
import kurohack.util.*;
import java.io.*;
import net.minecraft.util.*;

public class KuroGUI extends GuiScreen
{
    public static int GUI_KEY;
    public static int GUI_KEY_CLOSE;
    public static String BACKGROUND;
    public static boolean PAUSE_GAME;
    
    public KuroGUI() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        KuroHackUtils.renderSkeetBox(new Quad(10.0f, 10.0f, 400.0f, 400.0f));
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    public void onGuiClosed() {
        final String background = KuroGUI.BACKGROUND;
        switch (background) {
            case "BOTH":
            case "BLUR": {
                this.mc.entityRenderer.stopUseShader();
                break;
            }
        }
    }
    
    public void onGuiOpened() {
        final String background = KuroGUI.BACKGROUND;
        switch (background) {
            case "BOTH":
            case "BLUR": {
                this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                break;
            }
        }
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public boolean doesGuiPauseGame() {
        return KuroGUI.PAUSE_GAME;
    }
    
    static {
        KuroGUI.GUI_KEY = 54;
        KuroGUI.GUI_KEY_CLOSE = 1;
        KuroGUI.BACKGROUND = "BLUR";
        KuroGUI.PAUSE_GAME = false;
    }
}
