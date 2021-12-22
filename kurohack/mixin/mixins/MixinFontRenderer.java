//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import kurohack.features.modules.client.*;
import kurohack.*;
import kurohack.features.modules.HUD.*;
import org.spongepowered.asm.mixin.injection.*;
import kurohack.features.modules.misc.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer
{
    @Shadow
    protected abstract int renderString(final String p0, final float p1, final float p2, final int p3, final boolean p4);
    
    @Shadow
    protected abstract void renderStringAtPos(final String p0, final boolean p1);
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At("HEAD") }, cancellable = true)
    public void renderStringHook(final String text, final float x, final float y, final int color, final boolean dropShadow, final CallbackInfoReturnable<Integer> info) {
        if (FontMod.getInstance().isOn() && (boolean)FontMod.getInstance().full.getValue() && KuroHack.textManager != null) {
            final float result = KuroHack.textManager.drawString(text, x, y, color, dropShadow);
            info.setReturnValue((Object)(int)result);
        }
    }
    
    @Redirect(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I"))
    public int renderStringHook(final FontRenderer fontrenderer, final String text, final float x, final float y, final int color, final boolean dropShadow) {
        if (KuroHack.moduleManager != null && (boolean)HUD.getInstance().shadow.getValue() && dropShadow) {
            return this.renderString(text, x - 0.5f, y - 0.5f, color, true);
        }
        return this.renderString(text, x, y, color, dropShadow);
    }
    
    @Redirect(method = { "renderString(Ljava/lang/String;FFIZ)I" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
    public void renderStringAtPosHook(final FontRenderer renderer, final String text, final boolean shadow) {
        if (Media.getInstance().isOn() && (boolean)Media.getInstance().changeOwn.getValue()) {
            this.renderStringAtPos(text.replace(Media.getPlayerName(), (CharSequence)Media.getInstance().ownName.getValue()), shadow);
        }
        else {
            this.renderStringAtPos(text, shadow);
        }
    }
}
