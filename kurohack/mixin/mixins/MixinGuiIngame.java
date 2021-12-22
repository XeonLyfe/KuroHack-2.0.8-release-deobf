//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import kurohack.features.gui.custom.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import kurohack.features.modules.render.*;
import kurohack.*;
import kurohack.features.modules.HUD.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame extends Gui
{
    @Shadow
    @Final
    public GuiNewChat persistantChatGUI;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    public void init(final Minecraft mcIn, final CallbackInfo ci) {
        this.persistantChatGUI = (GuiNewChat)new GuiCustomNewChat(mcIn);
    }
    
    @Inject(method = { "renderPortal" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPortalHook(final float n, final ScaledResolution scaledResolution, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().portal.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPumpkinOverlayHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().pumpkin.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (KuroHack.moduleManager != null && !(boolean)HUD.getInstance().potionIcons.getValue()) {
            info.cancel();
        }
    }
}
