//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.gui.custom;

import net.minecraft.util.*;
import kurohack.features.gui.effect.Particle.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import kurohack.*;
import java.net.*;
import kurohack.util.render.*;
import kurohack.features.modules.client.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class GuiCustomMainScreen extends GuiScreen
{
    private final ResourceLocation resourceLocation;
    private int y;
    private int x;
    public ParticleSystem particleSystem;
    private int singleplayerWidth;
    private int multiplayerWidth;
    private int settingsWidth;
    private int discordWidth;
    private int exitWidth;
    private int textHeight;
    private float xOffset;
    private float yOffset;
    
    public GuiCustomMainScreen() {
        this.resourceLocation = new ResourceLocation("textures/background.png");
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final float width, final float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static boolean isHovered(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }
    
    public void initGui() {
        this.x = this.width / 4;
        this.y = this.height / 4 + 48;
        this.buttonList.add(new TextButton(0, this.x, this.y + 20, "Singleplayer"));
        this.buttonList.add(new TextButton(0, this.x, this.y + 44, "Multiplayer"));
        this.buttonList.add(new TextButton(0, this.x, this.y + 66, "Settings"));
        this.buttonList.add(new TextButton(0, this.x, this.y + 88, "Discord"));
        this.buttonList.add(new TextButton(0, this.x, this.y + 132, "Quitting"));
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public void updateScreen() {
        if (this.particleSystem != null) {
            this.particleSystem.update();
        }
        super.updateScreen();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (isHovered(this.x, this.y + 20, KuroHack.textManager.getStringWidth("Singleplayer") / 4, KuroHack.textManager.getFontHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiWorldSelection((GuiScreen)this));
        }
        else if (isHovered(this.x, this.y + 44, KuroHack.textManager.getStringWidth("Multiplayer") / 4, KuroHack.textManager.getFontHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        else if (isHovered(this.x, this.y + 66, KuroHack.textManager.getStringWidth("settings") / 4, KuroHack.textManager.getFontHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
        }
        else if (isHovered(this.x, this.y + 132, KuroHack.textManager.getStringWidth("Quitting") / 4, KuroHack.textManager.getFontHeight(), mouseX, mouseY)) {
            this.mc.shutdown();
        }
        else if (isHovered(this.x, this.y + 88, KuroHack.textManager.getStringWidth("discord") / 4, KuroHack.textManager.getFontHeight(), mouseX, mouseY)) {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://discord.gg/AnXvN9CHEC"));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void drawLogo() {
        final ResourceLocation logo = new ResourceLocation("textures/hmm.png");
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        this.mc.getTextureManager().bindTexture(logo);
        drawCompleteImage(1.0f, 3.0f, 250.0f, 48.0f);
        this.mc.getTextureManager().deleteTexture(logo);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.x = this.width / 4;
        this.y = this.height / 4 + 48;
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        this.mc.getTextureManager().bindTexture(this.resourceLocation);
        drawCompleteImage(-16.0f + this.xOffset, -9.0f + this.yOffset, (float)(this.width + 32), (float)(this.height + 18));
        RenderUtil.drawRect(this.xOffset, this.yOffset, 70.0f, 1000.0f, ColorUtil.toRGBA(20, 20, 20, 70));
        super.drawScreen(970, 540, partialTicks);
        this.drawLogo();
        if (this.particleSystem != null && MainMenu.getInstance().particles.getValue()) {
            this.particleSystem.render(mouseX, mouseY);
        }
        else {
            this.particleSystem = new ParticleSystem(new ScaledResolution(this.mc));
        }
        KuroHack.textManager.drawStringBig("KuroHack ", (float)this.x, this.y - 10.0f, Color.WHITE.getRGB(), true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public BufferedImage parseBackground(final BufferedImage background) {
        int width;
        int srcWidth;
        int srcHeight;
        int height;
        for (width = 1920, srcWidth = background.getWidth(), srcHeight = background.getHeight(), height = 1080; width < srcWidth || height < srcHeight; width *= 2, height *= 2) {}
        final BufferedImage imgNew = new BufferedImage(width, height, 2);
        final Graphics g = imgNew.getGraphics();
        g.drawImage(background, 0, 0, null);
        g.dispose();
        return imgNew;
    }
    
    private static class TextButton extends GuiButton
    {
        public TextButton(final int buttonId, final int x, final int y, final String buttonText) {
            super(buttonId, x, y, KuroHack.textManager.getStringWidth(buttonText), KuroHack.textManager.getFontHeight(), buttonText);
        }
        
        public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
            if (this.visible) {
                this.enabled = true;
                this.hovered = (mouseX >= this.x - KuroHack.textManager.getStringWidth(this.displayString) / 2.0f && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
                KuroHack.textManager.drawStringWithShadow(this.displayString, this.x - KuroHack.textManager.getStringWidth(this.displayString) / 2.0f, (float)this.y, Color.WHITE.getRGB());
                if (this.hovered) {
                    RenderUtil.drawLine(this.x - 5.0f - KuroHack.textManager.getStringWidth(this.displayString) / 2.0f, (float)(this.y + 2 + KuroHack.textManager.getFontHeight()), this.x + KuroHack.textManager.getStringWidth(this.displayString) / 2.0f + 1.0f, (float)(this.y + 2 + KuroHack.textManager.getFontHeight()), 1.0f, Color.WHITE.getRGB());
                    KuroHack.textManager.drawStringSmall("Click me!", (float)this.x, this.y - 10.0f, Color.WHITE.getRGB(), true);
                }
            }
        }
        
        public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
            return this.enabled && this.visible && mouseX >= this.x - KuroHack.textManager.getStringWidth(this.displayString) / 2.0f && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        }
    }
}
