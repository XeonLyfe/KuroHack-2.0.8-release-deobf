//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import kurohack.event.events.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import kurohack.util.render.*;
import kurohack.util.*;
import java.util.*;
import net.minecraft.util.math.*;

public class PearlRender extends Module
{
    public PearlRender() {
        super("PearlRender", "Renders where pearls will go", Module.Category.RENDER, true, false, false);
    }
    
    public double interpolate(final double now, final double then) {
        return then + (now - then) * PearlRender.mc.getRenderPartialTicks();
    }
    
    public double[] interpolate(final Entity entity) {
        final double posX = this.interpolate(entity.posX, entity.lastTickPosX) - PearlRender.mc.getRenderManager().renderPosX;
        final double posY = this.interpolate(entity.posY, entity.lastTickPosY) - PearlRender.mc.getRenderManager().renderPosY;
        final double posZ = this.interpolate(entity.posZ, entity.lastTickPosZ) - PearlRender.mc.getRenderManager().renderPosZ;
        return new double[] { posX, posY, posZ };
    }
    
    public void drawLineToEntity(final Entity e, final float red, final float green, final float blue, final float opacity) {
        final double[] xyz = this.interpolate(e);
        this.drawLine(xyz[0], xyz[1], xyz[2], red, green, blue, opacity);
    }
    
    public void drawLine(final double posx, final double posy, final double posz, final float red, final float green, final float blue, final float opacity) {
        final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(PearlRender.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(PearlRender.mc.player.rotationYaw));
        this.drawLineFromPosToPos(eyes.x, eyes.y + PearlRender.mc.player.getEyeHeight(), eyes.z, posx, posy, posz, red, green, blue, opacity);
    }
    
    public void drawLineFromPosToPos(final double posx, final double posy, final double posz, final double posx2, final double posy2, final double posz2, final float red, final float green, final float blue, final float opacity) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLoadIdentity();
        final boolean bobbing = PearlRender.mc.gameSettings.viewBobbing;
        PearlRender.mc.gameSettings.viewBobbing = false;
        PearlRender.mc.entityRenderer.orientCamera(PearlRender.mc.getRenderPartialTicks());
        GL11.glBegin(1);
        GL11.glVertex3d(posx, posy, posz);
        GL11.glVertex3d(posx2, posy2, posz2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glColor3d(1.0, 1.0, 1.0);
        PearlRender.mc.gameSettings.viewBobbing = bobbing;
    }
    
    public void onRender3D(final Render3DEvent event) {
        int i = 0;
        for (final Entity entity : PearlRender.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderPearl) {
                if (PearlRender.mc.player.getDistanceSq(entity) >= 2500.0) {
                    continue;
                }
                final Vec3d interp = EntityUtil.getInterpolatedRenderPos(entity, PearlRender.mc.getRenderPartialTicks());
                final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                GL11.glLineWidth(1.0f);
                RenderGlobal.renderFilledBox(bb, 255.0f, 255.0f, 255.0f, 255.0f);
                GL11.glDisable(2848);
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtil.drawBlockOutline(bb, new Color(255, 255, 255), 1.0f);
                this.drawLineToEntity(entity, 255.0f, 255.0f, 255.0f, 255.0f);
                final BlockPos posEntity = entity.getPosition();
                RenderUtil.drawText(posEntity, "X: " + MathUtil.round(entity.posX, 0) + " Y: " + MathUtil.round(entity.posY, 0) + " Z:" + MathUtil.round(entity.posZ, 2));
                if (++i < 50) {}
            }
        }
    }
}
