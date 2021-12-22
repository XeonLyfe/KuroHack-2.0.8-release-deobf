//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import net.minecraft.entity.player.*;
import java.util.*;
import kurohack.event.events.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ShadowESP extends Module
{
    public ShadowESP() {
        super("ShadowEsp", "idk", Module.Category.RENDER, true, false, false);
    }
    
    public void onDisable() {
        for (final EntityPlayer player : ShadowESP.mc.world.playerEntities) {
            if (!player.isGlowing()) {
                continue;
            }
            player.setGlowing(false);
        }
        super.onDisable();
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        for (final Entity player : ShadowESP.mc.world.loadedEntityList) {
            if (!(player instanceof EntityPlayer)) {
                continue;
            }
            player.setGlowing(true);
        }
    }
}
