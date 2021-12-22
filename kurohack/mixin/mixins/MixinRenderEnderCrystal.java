//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import kurohack.features.modules.render.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import kurohack.event.events.*;
import org.lwjgl.opengl.*;
import kurohack.features.modules.client.*;
import kurohack.util.render.*;
import java.awt.*;
import kurohack.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEnderCrystal
{
    @Shadow
    @Final
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    private static ResourceLocation glint;
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(final ModelBase model, final Entity entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (CrystalScale.INSTANCE.isEnabled()) {
            if ((boolean)CrystalScale.INSTANCE.animateScale.getValue() && CrystalScale.INSTANCE.scaleMap.containsKey(entity)) {
                GlStateManager.scale((float)CrystalScale.INSTANCE.scaleMap.get(entity), (float)CrystalScale.INSTANCE.scaleMap.get(entity), (float)CrystalScale.INSTANCE.scaleMap.get(entity));
            }
            else {
                GlStateManager.scale((float)CrystalScale.INSTANCE.scale.getValue(), (float)CrystalScale.INSTANCE.scale.getValue(), (float)CrystalScale.INSTANCE.scale.getValue());
            }
        }
        if (CrystalScale.INSTANCE.isEnabled() && (boolean)CrystalScale.INSTANCE.wireframe.getValue()) {
            final RenderEntityModelEvent event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            CrystalScale.INSTANCE.onRenderModel(event);
        }
        if (CrystalScale.INSTANCE.isEnabled() && (boolean)CrystalScale.INSTANCE.chams.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            if (CrystalScale.INSTANCE.rainbow.getValue()) {
                final Color rainbowColor1 = CrystalScale.INSTANCE.colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : new Color(RenderUtil.getRainbow((int)CrystalScale.INSTANCE.speed.getValue() * 100, 0, (int)CrystalScale.INSTANCE.saturation.getValue() / 100.0f, (int)CrystalScale.INSTANCE.brightness.getValue() / 100.0f));
                final Color rainbowColor2 = EntityUtil.getColor(entity, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), (int)CrystalScale.INSTANCE.alpha.getValue(), true);
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f(rainbowColor2.getRed() / 255.0f, rainbowColor2.getGreen() / 255.0f, rainbowColor2.getBlue() / 255.0f, (int)CrystalScale.INSTANCE.alpha.getValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            }
            else if ((boolean)CrystalScale.INSTANCE.xqz.getValue() && (boolean)CrystalScale.INSTANCE.throughWalls.getValue()) {
                final Color hiddenColor = CrystalScale.INSTANCE.colorSync.getValue() ? EntityUtil.getColor(entity, (int)CrystalScale.INSTANCE.hiddenRed.getValue(), (int)CrystalScale.INSTANCE.hiddenGreen.getValue(), (int)CrystalScale.INSTANCE.hiddenBlue.getValue(), (int)CrystalScale.INSTANCE.hiddenAlpha.getValue(), true) : EntityUtil.getColor(entity, (int)CrystalScale.INSTANCE.hiddenRed.getValue(), (int)CrystalScale.INSTANCE.hiddenGreen.getValue(), (int)CrystalScale.INSTANCE.hiddenBlue.getValue(), (int)CrystalScale.INSTANCE.hiddenAlpha.getValue(), true);
                final Color color;
                final Color visibleColor = color = (CrystalScale.INSTANCE.colorSync.getValue() ? EntityUtil.getColor(entity, (int)CrystalScale.INSTANCE.red.getValue(), (int)CrystalScale.INSTANCE.green.getValue(), (int)CrystalScale.INSTANCE.blue.getValue(), (int)CrystalScale.INSTANCE.alpha.getValue(), true) : EntityUtil.getColor(entity, (int)CrystalScale.INSTANCE.red.getValue(), (int)CrystalScale.INSTANCE.green.getValue(), (int)CrystalScale.INSTANCE.blue.getValue(), (int)CrystalScale.INSTANCE.alpha.getValue(), true));
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f(hiddenColor.getRed() / 255.0f, hiddenColor.getGreen() / 255.0f, hiddenColor.getBlue() / 255.0f, (int)CrystalScale.INSTANCE.alpha.getValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
                GL11.glColor4f(visibleColor.getRed() / 255.0f, visibleColor.getGreen() / 255.0f, visibleColor.getBlue() / 255.0f, (int)CrystalScale.INSTANCE.alpha.getValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
            else {
                final Color color2;
                final Color visibleColor = color2 = (CrystalScale.INSTANCE.colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entity, (int)CrystalScale.INSTANCE.red.getValue(), (int)CrystalScale.INSTANCE.green.getValue(), (int)CrystalScale.INSTANCE.blue.getValue(), (int)CrystalScale.INSTANCE.alpha.getValue(), true));
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f(visibleColor.getRed() / 255.0f, visibleColor.getGreen() / 255.0f, visibleColor.getBlue() / 255.0f, (int)CrystalScale.INSTANCE.alpha.getValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            if (CrystalScale.INSTANCE.glint.getValue()) {
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GlStateManager.enableAlpha();
                GlStateManager.color(1.0f, 0.0f, 0.0f, 0.13f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.disableAlpha();
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
            }
        }
        else {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        if (CrystalScale.INSTANCE.isEnabled()) {
            if ((boolean)CrystalScale.INSTANCE.animateScale.getValue() && CrystalScale.INSTANCE.scaleMap.containsKey(entity)) {
                GlStateManager.scale(1.0f / CrystalScale.INSTANCE.scaleMap.get(entity), 1.0f / CrystalScale.INSTANCE.scaleMap.get(entity), 1.0f / CrystalScale.INSTANCE.scaleMap.get(entity));
            }
            else {
                GlStateManager.scale(1.0f / (float)CrystalScale.INSTANCE.scale.getValue(), 1.0f / (float)CrystalScale.INSTANCE.scale.getValue(), 1.0f / (float)CrystalScale.INSTANCE.scale.getValue());
            }
        }
    }
    
    static {
        MixinRenderEnderCrystal.glint = new ResourceLocation("textures/glint.png");
    }
}
