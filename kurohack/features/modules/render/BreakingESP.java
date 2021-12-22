//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import java.awt.*;
import kurohack.util.render.*;

public class BreakingESP extends Module
{
    public final Setting<Float> lineWidth;
    public final Setting<Integer> boxAlpha;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    
    public BreakingESP() {
        super("BreakingESP", "Renders a box on blocks being broken", Module.Category.RENDER, true, false, false);
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)125, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f, object -> this.outline.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)85, (T)0, (T)255, object -> this.box.getValue()));
    }
    
    public void onRender3D(final Render3DEvent render3DEvent) {
        if (BreakingESP.mc.playerController.currentBlock != null) {
            final Color color = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.boxAlpha.getValue());
            RenderUtil.boxESP(BreakingESP.mc.playerController.currentBlock, color, this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), false);
        }
    }
}
