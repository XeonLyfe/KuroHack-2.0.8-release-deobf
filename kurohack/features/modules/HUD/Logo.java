//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.HUD;

import kurohack.features.modules.*;
import net.minecraft.util.*;
import kurohack.features.setting.*;
import kurohack.util.element.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import kurohack.event.events.*;
import kurohack.features.*;
import kurohack.features.modules.client.*;
import kurohack.util.render.*;

public class Logo extends Module
{
    public static final ResourceLocation mark;
    public Setting<Integer> imageX;
    public Setting<Integer> imageY;
    public Setting<Integer> imageWidth;
    public Setting<Integer> imageHeight;
    private int color;
    
    public Logo() {
        super("Logo", "Puts a logo there (there)", Category.HUD, false, false, false);
        this.imageX = (Setting<Integer>)this.register(new Setting("logoX", (T)0, (T)0, (T)300));
        this.imageY = (Setting<Integer>)this.register(new Setting("logoY", (T)0, (T)0, (T)300));
        this.imageWidth = (Setting<Integer>)this.register(new Setting("logoWidth", (T)97, (T)0, (T)1000));
        this.imageHeight = (Setting<Integer>)this.register(new Setting("logoHeight", (T)97, (T)0, (T)1000));
    }
    
    public void renderLogo() {
        final int width = this.imageWidth.getValue();
        final int height = this.imageHeight.getValue();
        final int x = this.imageX.getValue();
        final int y = this.imageY.getValue();
        IUtil.mc.renderEngine.bindTexture(Logo.mark);
        GlStateManager.color(255.0f, 255.0f, 255.0f);
        Gui.drawScaledCustomSizeModalRect(x - 2, y - 36, 7.0f, 7.0f, width - 7, height - 7, width, height, (float)width, (float)height);
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (!Feature.fullNullCheck()) {
            final int width = this.renderer.scaledWidth;
            final int height = this.renderer.scaledHeight;
            this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
            if (this.enabled.getValue()) {
                this.renderLogo();
            }
        }
    }
    
    static {
        mark = new ResourceLocation("textures/logo.png");
    }
}
