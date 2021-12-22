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

public class UnlimitedSlider extends Button
{
    public Setting setting;
    
    public UnlimitedSlider(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 14;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (ClickGui.getInstance().rainbowRolling.getValue()) {
            final int color = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight)), KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            final int color2 = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight)), KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            RenderUtil.drawGradientRect((float)(int)this.x, (float)(int)this.y, this.width + 7.4f, (float)this.height, color, color2);
        }
        else {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.isHovering(mouseX, mouseY) ? KuroHack.colorManager.getColorWithAlpha(KuroHack.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : KuroHack.colorManager.getColorWithAlpha(KuroHack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()));
        }
        KuroHack.textManager.drawStringWithShadow(" - " + this.setting.getName() + " §7" + this.setting.getValue() + "§r +", this.x + 2.3f, this.y - 1.7f - KuroHackGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            IUtil.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_NOTE_HARP, 1.0f));
            if (this.isRight(mouseX)) {
                if (this.setting.getValue() instanceof Double) {
                    this.setting.setValue(this.setting.getValue() + 1.0);
                }
                else if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(this.setting.getValue() + 1.0f);
                }
                else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue(this.setting.getValue() + 1);
                }
            }
            else if (this.setting.getValue() instanceof Double) {
                this.setting.setValue(this.setting.getValue() - 1.0);
            }
            else if (this.setting.getValue() instanceof Float) {
                this.setting.setValue(this.setting.getValue() - 1.0f);
            }
            else if (this.setting.getValue() instanceof Integer) {
                this.setting.setValue(this.setting.getValue() - 1);
            }
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return true;
    }
    
    public boolean isRight(final int x) {
        return x > this.x + (this.width + 7.4f) / 2.0f;
    }
}
