//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.notifications;

import kurohack.util.*;
import kurohack.features.modules.HUD.*;
import kurohack.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import kurohack.util.render.*;

public class Notifications
{
    private final String text;
    private final long disableTime;
    private final float width;
    private final Timer timer;
    
    public Notifications(final String text, final long disableTime) {
        this.timer = new Timer();
        this.text = text;
        this.disableTime = disableTime;
        this.width = (float)KuroHack.moduleManager.getModuleByClass(HUD.class).renderer.getStringWidth(text);
        this.timer.reset();
    }
    
    public void onDraw(final int y) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.timer.passedMs(this.disableTime)) {
            KuroHack.notificationManager.getNotifications().remove(this);
        }
        RenderUtil.drawRect(scaledResolution.getScaledWidth() - 4 - this.width, (float)y, (float)(scaledResolution.getScaledWidth() - 2), (float)(y + KuroHack.moduleManager.getModuleByClass(HUD.class).renderer.getFontHeight() + 3), 1962934272);
        KuroHack.moduleManager.getModuleByClass(HUD.class).renderer.drawString(this.text, scaledResolution.getScaledWidth() - this.width - 3.0f, (float)(y + 2), -1, true);
    }
}
