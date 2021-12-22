//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.components.items.buttons;

import kurohack.features.modules.*;
import net.minecraft.util.*;
import kurohack.features.gui.components.items.*;
import org.lwjgl.opengl.*;
import kurohack.features.setting.*;
import java.util.*;
import kurohack.features.modules.client.*;
import kurohack.*;
import kurohack.features.gui.*;
import kurohack.util.render.*;
import java.awt.*;
import kurohack.util.element.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class ModuleButton extends Button
{
    private final Module module;
    private final ResourceLocation logo;
    private List<Item> items;
    private boolean subOpen;
    
    public ModuleButton(final Module module) {
        super(module.getName());
        this.logo = new ResourceLocation("textures/gear.png");
        this.items = new ArrayList<Item>();
        this.module = module;
        this.initSettings();
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float)width, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float)width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public void initSettings() {
        final ArrayList<Item> newItems = new ArrayList<Item>();
        if (!this.module.getSettings().isEmpty()) {
            for (final Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    newItems.add((Item)new BooleanButton(setting));
                }
                if (setting.getValue() instanceof Bind && !this.module.getName().equalsIgnoreCase("Hud")) {
                    newItems.add((Item)new BindButton(setting));
                }
                if (setting.getValue() instanceof String || setting.getValue() instanceof Character) {
                    newItems.add((Item)new StringButton(setting));
                }
                if (setting.isNumberSetting()) {
                    if (setting.hasRestriction()) {
                        newItems.add((Item)new Slider(setting));
                        continue;
                    }
                    newItems.add((Item)new UnlimitedSlider(setting));
                }
                if (!setting.isEnumSetting()) {
                    continue;
                }
                newItems.add((Item)new EnumButton(setting));
            }
        }
        this.items = newItems;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!this.items.isEmpty()) {
            final ClickGui gui = KuroHack.moduleManager.getModuleByClass(ClickGui.class);
            KuroHack.textManager.drawStringWithShadow(((boolean)gui.openCloseChange.getValue()) ? (this.subOpen ? gui.close.getValue() : gui.open.getValue()) : gui.moduleButton.getValue(), this.x - 1.5f + this.width - 7.4f, this.y - 2.0f - KuroHackGui.getClickGui().getTextOffset(), -1);
        }
        if (ClickGui.getInstance().gear.getValue()) {
            ModuleButton.mc.getTextureManager().bindTexture(this.logo);
            drawCompleteImage(this.x - 1.5f + this.width - 7.5f, this.y - 2.0f - KuroHackGui.getClickGui().getTextOffset(), 9, 9);
        }
        if (this.subOpen) {
            float height = 1.0f;
            for (final Item item : this.items) {
                if (!item.isHidden()) {
                    item.setLocation(this.x + 1.0f, this.y + (height += 15.0f));
                    item.setHeight(15);
                    item.setWidth(this.width - 9);
                    item.drawScreen(mouseX, mouseY, partialTicks);
                }
                item.update();
            }
        }
        if (this.isHovering(mouseX, mouseY)) {
            RenderUtil.drawRect((float)(mouseX + 10), (float)mouseY, (float)(mouseX + 10 + this.renderer.getStringWidth(this.module.getDescription())), (float)(mouseY + 10), ColorUtil.toRGBA(0, 0, 0, 50));
            RenderUtil.drawBorder((float)(mouseX + 10), (float)mouseY, (float)this.renderer.getStringWidth(this.module.getDescription()), 10.0f, new Color(ColorUtil.toRGBA(ClickGui.getInstance().newred.getCurrentState(), ClickGui.getInstance().newgreen.getCurrentState(), ClickGui.getInstance().newblue.getCurrentState(), 0)));
            this.renderer.drawStringWithShadow(this.module.getDescription(), (float)(mouseX + 10), (float)mouseY, -1);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.items.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
                IUtil.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            if (this.subOpen) {
                for (final Item item : this.items) {
                    if (item.isHidden()) {
                        continue;
                    }
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        super.onKeyTyped(typedChar, keyCode);
        if (!this.items.isEmpty() && this.subOpen) {
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                item.onKeyTyped(typedChar, keyCode);
            }
        }
    }
    
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void toggle() {
        this.module.toggle();
    }
    
    public boolean getState() {
        return this.module.isEnabled();
    }
}
