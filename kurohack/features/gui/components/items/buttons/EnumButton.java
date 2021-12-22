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
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class EnumButton extends Button
{
    public Setting setting;
    
    public EnumButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
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
        KuroHack.textManager.drawStringWithShadow(this.setting.getName() + " §7" + this.setting.currentEnumName(), this.x + 2.3f, this.y - 1.7f - KuroHackGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            EnumButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_NOTE_HARP, 1.0f));
        }
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
        this.setting.increaseEnum();
    }
    
    public boolean getState() {
        return true;
    }
}
