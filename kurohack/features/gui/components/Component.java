//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.components;

import kurohack.features.*;
import kurohack.features.gui.components.items.*;
import net.minecraft.util.*;
import kurohack.features.modules.client.*;
import kurohack.features.modules.HUD.*;
import kurohack.util.*;
import kurohack.util.render.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import kurohack.*;
import kurohack.features.gui.*;
import java.util.*;
import kurohack.util.element.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import kurohack.features.gui.components.items.buttons.*;

public class Component extends Feature
{
    private final ArrayList<Item> items;
    private final ResourceLocation arrow;
    public boolean drag;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private boolean open;
    private boolean hidden;
    
    public Component(final String name, final int x, final int y, final boolean open) {
        super(name);
        this.items = new ArrayList<Item>();
        this.arrow = new ResourceLocation("textures/arrow.png");
        this.hidden = false;
        this.x = x;
        this.y = y;
        this.width = 95;
        this.height = 18;
        this.open = open;
        this.setupItems();
    }
    
    public void setupItems() {
    }
    
    private void drag(final int mouseX, final int mouseY) {
        if (!this.drag) {
            return;
        }
        this.x = this.x2 + mouseX;
        this.y = this.y2 + mouseY;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drag(mouseX, mouseY);
        final float totalItemHeight = this.open ? (this.getTotalItemHeight() - 2.0f) : 0.0f;
        int color = -7829368;
        if (ClickGui.getInstance().devSettings.getValue()) {
            int argb = 0;
            if (ClickGui.getInstance().colorSync.getValue()) {
                Colors.INSTANCE.getCurrentColorHex();
            }
            else {
                argb = ColorUtil.toARGB(ClickGui.getInstance().topRed.getValue(), ClickGui.getInstance().topGreen.getValue(), ClickGui.getInstance().topBlue.getValue(), ClickGui.getInstance().topAlpha.getValue());
            }
            color = argb;
        }
        if (ClickGui.getInstance().rainbowRolling.getValue() && ClickGui.getInstance().colorSync.getValue() && Colors.INSTANCE.rainbow.getValue()) {
            RenderUtil.drawGradientRect((float)this.x, this.y - 1.5f, (float)this.width, (float)(this.height - 4), HUD.getInstance().colorMap.get(MathUtil.clamp(this.y, 0, this.renderer.scaledHeight)), HUD.getInstance().colorMap.get(MathUtil.clamp(this.y + this.height - 4, 0, this.renderer.scaledHeight)));
        }
        else {
            RenderUtil.drawRect((float)this.x, this.y - 1.5f, (float)(this.x + this.width), (float)(this.y + this.height - 6), color);
        }
        if (ClickGui.getInstance().frameSettings.getValue()) {
            final int n;
            color = (n = (ClickGui.getInstance().isEnabled() ? Colors.INSTANCE.getCurrentColorHex() : ColorUtil.toARGB(ClickGui.getInstance().frameRed.getValue(), ClickGui.getInstance().frameGreen.getValue(), ClickGui.getInstance().frameBlue.getValue(), ClickGui.getInstance().frameAlpha.getValue())));
            RenderUtil.drawRect((float)this.x, this.y + 11.0f, (float)(this.x + this.width), (float)(this.y + this.height - 6), ((boolean)ClickGui.getInstance().colorSync.getValue()) ? Colors.INSTANCE.getCurrentColor().getRGB() : ColorUtil.toARGB(ClickGui.getInstance().frameRed.getValue(), ClickGui.getInstance().frameGreen.getValue(), ClickGui.getInstance().frameBlue.getValue(), ClickGui.getInstance().frameAlpha.getValue()));
        }
        if (this.open) {
            RenderUtil.drawRect((float)this.x, this.y + 12.5f, (float)(this.x + this.width), this.y + this.height + totalItemHeight, ColorUtil.toARGB(10, 10, 10, ClickGui.getInstance().backgroundAlpha.getValue()));
            if (ClickGui.getInstance().outline.getValue()) {
                if (ClickGui.getInstance().rainbowRolling.getValue()) {
                    GlStateManager.disableTexture2D();
                    GlStateManager.enableBlend();
                    GlStateManager.disableAlpha();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.shadeModel(7425);
                    GL11.glBegin(1);
                    Color currentColor = new Color(HUD.getInstance().colorMap.get(MathUtil.clamp(this.y, 0, this.renderer.scaledHeight)));
                    GL11.glColor4f(currentColor.getRed() / 255.0f, currentColor.getGreen() / 255.0f, currentColor.getBlue() / 255.0f, currentColor.getAlpha() / 255.0f);
                    GL11.glVertex3f((float)(this.x + this.width), this.y - 1.5f, 0.0f);
                    GL11.glVertex3f((float)this.x, this.y - 1.5f, 0.0f);
                    GL11.glVertex3f((float)this.x, this.y - 1.5f, 0.0f);
                    float currentHeight = this.getHeight() - 1.5f;
                    for (final Item item : this.getItems()) {
                        currentColor = new Color(HUD.getInstance().colorMap.get(MathUtil.clamp((int)(this.y + (currentHeight += item.getHeight() + 1.5f)), 0, this.renderer.scaledHeight)));
                        GL11.glColor4f(currentColor.getRed() / 255.0f, currentColor.getGreen() / 255.0f, currentColor.getBlue() / 255.0f, currentColor.getAlpha() / 255.0f);
                        GL11.glVertex3f((float)this.x, this.y + currentHeight, 0.0f);
                        GL11.glVertex3f((float)this.x, this.y + currentHeight, 0.0f);
                    }
                    currentColor = new Color(HUD.getInstance().colorMap.get(MathUtil.clamp((int)(this.y + this.height + totalItemHeight), 0, this.renderer.scaledHeight)));
                    GL11.glColor4f(currentColor.getRed() / 255.0f, currentColor.getGreen() / 255.0f, currentColor.getBlue() / 255.0f, currentColor.getAlpha() / 255.0f);
                    GL11.glVertex3f((float)(this.x + this.width), this.y + this.height + totalItemHeight, 0.0f);
                    GL11.glVertex3f((float)(this.x + this.width), this.y + this.height + totalItemHeight, 0.0f);
                    for (final Item item : this.getItems()) {
                        currentColor = new Color(HUD.getInstance().colorMap.get(MathUtil.clamp((int)(this.y + (currentHeight -= item.getHeight() + 1.5f)), 0, this.renderer.scaledHeight)));
                        GL11.glColor4f(currentColor.getRed() / 255.0f, currentColor.getGreen() / 255.0f, currentColor.getBlue() / 255.0f, currentColor.getAlpha() / 255.0f);
                        GL11.glVertex3f((float)(this.x + this.width), this.y + currentHeight, 0.0f);
                        GL11.glVertex3f((float)(this.x + this.width), this.y + currentHeight, 0.0f);
                    }
                    GL11.glVertex3f((float)(this.x + this.width), (float)this.y, 0.0f);
                    GL11.glEnd();
                    GlStateManager.shadeModel(7424);
                    GlStateManager.disableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableTexture2D();
                }
                else {
                    GlStateManager.disableTexture2D();
                    GlStateManager.enableBlend();
                    GlStateManager.disableAlpha();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.shadeModel(7425);
                    GL11.glBegin(2);
                    final Color outlineColor = ClickGui.getInstance().colorSync.getValue() ? new Color(Colors.INSTANCE.getCurrentColorHex()) : new Color(KuroHack.colorManager.getColorAsIntFullAlpha());
                    GL11.glColor4f((float)outlineColor.getRed(), (float)outlineColor.getGreen(), (float)outlineColor.getBlue(), (float)outlineColor.getAlpha());
                    GL11.glVertex3f((float)this.x, this.y - 1.5f, 0.0f);
                    GL11.glVertex3f((float)(this.x + this.width), this.y - 1.5f, 0.0f);
                    GL11.glVertex3f((float)(this.x + this.width), this.y + this.height + totalItemHeight, 0.0f);
                    GL11.glVertex3f((float)this.x, this.y + this.height + totalItemHeight, 0.0f);
                    GL11.glEnd();
                    GlStateManager.shadeModel(7424);
                    GlStateManager.disableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableTexture2D();
                }
            }
        }
        KuroHack.textManager.drawStringWithShadow(this.getName(), this.x + 3.0f, this.y - 4.0f - KuroHackGui.getClickGui().getTextOffset(), -1);
        if (this.open) {
            Component.mc.getTextureManager().bindTexture(this.arrow);
            ModuleButton.drawCompleteImage(this.x - 1.5f + this.width - 12.0f, this.y - 5.0f - KuroHackGui.getClickGui().getTextOffset(), 12, 11);
            float y = this.getY() + this.getHeight() - 3.0f;
            for (final Item item2 : this.getItems()) {
                if (item2.isHidden()) {
                    continue;
                }
                item2.setLocation(this.x + 2.0f, y);
                item2.setWidth(this.getWidth() - 4);
                item2.drawScreen(mouseX, mouseY, partialTicks);
                y += item2.getHeight() + 1.5f;
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            KuroHackGui.getClickGui().getComponents().forEach(component -> {
                if (component.drag) {
                    component.drag = false;
                }
                return;
            });
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            IUtil.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            return;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        if (releaseButton == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
    }
    
    public void addButton(final Button button) {
        this.items.add(button);
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public final ArrayList<Item> getItems() {
        return this.items;
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
    }
    
    private float getTotalItemHeight() {
        float height = 0.0f;
        for (final Item item : this.getItems()) {
            height += item.getHeight() + 1.5f;
        }
        return height;
    }
}
