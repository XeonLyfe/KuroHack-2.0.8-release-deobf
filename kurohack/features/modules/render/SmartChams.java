//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class SmartChams extends Module
{
    private final Setting<Float> range;
    public Setting<Integer> a;
    
    public SmartChams() {
        super("SmartChams", "Smart Aplha", Module.Category.RENDER, false, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)4.0f, (T)1.0f, (T)8.0f));
        this.a = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
    }
    
    @SubscribeEvent
    public void onRenderPre(final RenderEntityEvent.Pre event) {
        if (this.shouldRender(event.getEntity())) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, this.a.getValue() / 255.0f);
        }
    }
    
    @SubscribeEvent
    public void onRenderPost(final RenderEntityEvent.Post event) {
        if (this.shouldRender(event.getEntity())) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public boolean shouldRender(final Entity entity) {
        return entity != SmartChams.mc.player && entity instanceof EntityPlayer && entity.getDistance((Entity)SmartChams.mc.player) < this.range.getValue();
    }
}
