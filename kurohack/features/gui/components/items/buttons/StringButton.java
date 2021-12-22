//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.components.items.buttons;

import kurohack.features.setting.*;
import kurohack.features.modules.client.*;
import kurohack.features.modules.HUD.*;
import kurohack.util.*;
import kurohack.*;
import kurohack.util.render.*;
import kurohack.features.gui.*;
import kurohack.util.element.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.util.*;

public class StringButton extends Button
{
    public boolean isListening;
    private final Setting setting;
    private CurrentString currentString;
    
    public StringButton(final Setting setting) {
        super(setting.getName());
        this.currentString = new CurrentString("");
        this.setting = setting;
        this.width = 14;
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (ClickGui.getInstance().rainbowRolling.getValue()) {
            final int color = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight)), KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            final int color2 = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight)), KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            RenderUtil.drawGradientRect(this.x, this.y, this.width + 7.4f, this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? color : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077), this.getState() ? (this.isHovering(mouseX, mouseY) ? color2 : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        else {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? KuroHack.colorManager.getColorWithAlpha(KuroHack.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : KuroHack.colorManager.getColorWithAlpha(KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        if (this.isListening) {
            KuroHack.textManager.drawStringWithShadow(this.currentString.getString() + KuroHack.textManager.getIdleSign(), this.x + 2.3f, this.y - 1.7f - KuroHackGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
        else {
            KuroHack.textManager.drawStringWithShadow((this.setting.shouldRenderName() ? (this.setting.getName() + " �7") : "") + this.setting.getValue(), this.x + 2.3f, this.y - 1.7f - KuroHackGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            IUtil.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_NOTE_HARP, 1.0f));
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isListening) {
            if (keyCode == 1) {
                return;
            }
            if (keyCode == 28) {
                this.enterString();
            }
            else if (keyCode == 14) {
                this.setString(removeLastChar(this.currentString.getString()));
            }
            else {
                Label_0122: {
                    if (keyCode == 47) {
                        if (!Keyboard.isKeyDown(157)) {
                            if (!Keyboard.isKeyDown(29)) {
                                break Label_0122;
                            }
                        }
                        try {
                            this.setString(this.currentString.getString() + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    this.setString(this.currentString.getString() + typedChar);
                }
            }
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    private void enterString() {
        if (this.currentString.getString().isEmpty()) {
            this.setting.setValue(this.setting.getDefaultValue());
        }
        else {
            this.setting.setValue(this.currentString.getString());
        }
        this.setString("");
        super.onMouseClick();
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
        this.isListening = !this.isListening;
    }
    
    public boolean getState() {
        return !this.isListening;
    }
    
    public void setString(final String newString) {
        this.currentString = new CurrentString(newString);
    }
    
    public static class CurrentString
    {
        private final String string;
        
        public CurrentString(final String string) {
            this.string = string;
        }
        
        public String getString() {
            return this.string;
        }
    }
}
