//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HellenKeller extends Module
{
    float masterLevel;
    
    public HellenKeller() {
        super("HellenKeller", "Play like Hellen Keller", Category.RENDER, true, false, false);
    }
    
    @Override
    public void onEnable() {
        this.masterLevel = HellenKeller.mc.gameSettings.getSoundLevel(SoundCategory.MASTER);
        HellenKeller.mc.gameSettings.setSoundLevel(SoundCategory.MASTER, 0.0f);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Chat event) {
        GlStateManager.pushMatrix();
        Gui.drawRect(0, 0, HellenKeller.mc.displayWidth, HellenKeller.mc.displayHeight, new Color(0, 0, 0, 255).getRGB());
        GlStateManager.popMatrix();
    }
    
    @Override
    public void onDisable() {
        HellenKeller.mc.gameSettings.setSoundLevel(SoundCategory.MASTER, this.masterLevel);
    }
}
