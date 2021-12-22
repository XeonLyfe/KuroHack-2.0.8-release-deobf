//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class Shaders extends Module
{
    private static final Shaders INSTANCE;
    public Setting<Mode> shader;
    
    public Shaders() {
        super("Shaders", "Cool.", Module.Category.RENDER, false, false, false);
        this.shader = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.green));
    }
    
    public void onUpdate() {
        if (OpenGlHelper.shadersSupported && Shaders.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (Shaders.mc.entityRenderer.getShaderGroup() != null) {
                Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            try {
                Shaders.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (Shaders.mc.entityRenderer.getShaderGroup() != null && Shaders.mc.currentScreen == null) {
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    public String getDisplayInfo() {
        return this.shader.currentEnumName();
    }
    
    public void onDisable() {
        if (Shaders.mc.entityRenderer.getShaderGroup() != null) {
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    static {
        INSTANCE = new Shaders();
    }
    
    public enum Mode
    {
        notch, 
        antialias, 
        art, 
        bits, 
        blobs, 
        blobs2, 
        blur, 
        bumpy, 
        color_convolve, 
        creeper, 
        deconverge, 
        desaturate, 
        flip, 
        fxaa, 
        green, 
        invert, 
        ntsc, 
        pencil, 
        phosphor, 
        sobel, 
        spider, 
        wobble;
    }
}
