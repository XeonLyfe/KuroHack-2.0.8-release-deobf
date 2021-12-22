//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.gui.clickgui2.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ClickGui2
{
    public static ClickGui2 INSTANCE;
    public static KuroGUI KuroGUI;
    
    @SubscribeEvent
    public void onInput(final InputEvent.KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null && mc.world != null && Keyboard.isCreated() && Keyboard.getEventKeyState()) {
            final int eventKey;
            final int key = eventKey = Keyboard.getEventKey();
            final KuroGUI kuroGUI = ClickGui2.KuroGUI;
            if (eventKey == kurohack.features.gui.clickgui2.KuroGUI.GUI_KEY && mc.currentScreen != ClickGui2.KuroGUI) {
                mc.displayGuiScreen((GuiScreen)ClickGui2.KuroGUI);
                ClickGui2.KuroGUI.onGuiOpened();
            }
            else {
                final int n = key;
                final KuroGUI kuroGUI2 = ClickGui2.KuroGUI;
                if (n == kurohack.features.gui.clickgui2.KuroGUI.GUI_KEY_CLOSE && mc.currentScreen == ClickGui2.KuroGUI) {
                    mc.displayGuiScreen((GuiScreen)null);
                }
            }
        }
    }
}
