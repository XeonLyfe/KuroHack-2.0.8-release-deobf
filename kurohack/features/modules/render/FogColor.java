//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.common.*;
import java.awt.*;

public class FogColor extends Module
{
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Boolean> rainbow;
    private Setting<Boolean> fog;
    private static FogColor INSTANCE;
    
    public FogColor() {
        super("FogColor", "Changes the color of the sky", Module.Category.RENDER, false, false, false);
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false));
        this.fog = (Setting<Boolean>)this.register(new Setting("Fog", (T)true));
    }
    
    private void setInstance() {
        FogColor.INSTANCE = this;
    }
    
    public static FogColor getInstance() {
        if (FogColor.INSTANCE == null) {
            FogColor.INSTANCE = new FogColor();
        }
        return FogColor.INSTANCE;
    }
    
    @SubscribeEvent
    public void fogColors(final EntityViewRenderEvent.FogColors event) {
        event.setRed(this.red.getValue() / 255.0f);
        event.setGreen(this.green.getValue() / 255.0f);
        event.setBlue(this.blue.getValue() / 255.0f);
    }
    
    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        if (this.fog.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void onUpdate() {
        if (this.rainbow.getValue()) {
            this.doRainbow();
        }
    }
    
    public void doRainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);
        this.red.setValue(color_rgb_o >> 16 & 0xFF);
        this.green.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue.setValue(color_rgb_o & 0xFF);
    }
    
    static {
        FogColor.INSTANCE = new FogColor();
    }
}
