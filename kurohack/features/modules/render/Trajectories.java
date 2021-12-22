//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import org.lwjgl.util.glu.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.base.*;

public class Trajectories extends Module
{
    public Setting slices;
    private final Setting<Float> size;
    private final Setting<Float> innerSize;
    
    public Trajectories() {
        super("Trajectories", "Draws Trajectories.", Module.Category.RENDER, false, false, false);
        this.slices = this.register(new Setting("Slices", (T)3, (T)2, (T)100));
        this.size = (Setting<Float>)this.register(new Setting("Size", (T)1.0f, (T)(-5.0f), (T)5.0f));
        this.innerSize = (Setting<Float>)this.register(new Setting("Inner Size", (T)1.0f, (T)(-5.0f), (T)5.0f));
    }
    
    public void onRender3D(final Render3DEvent var1) {
        if (Trajectories.mc.world != null && Trajectories.mc.player != null) {
            Trajectories.mc.getRenderManager();
            final double var2 = Trajectories.mc.player.lastTickPosX + (Trajectories.mc.player.posX - Trajectories.mc.player.lastTickPosX) * var1.getPartialTicks();
            final double var3 = Trajectories.mc.player.lastTickPosY + (Trajectories.mc.player.posY - Trajectories.mc.player.lastTickPosY) * var1.getPartialTicks();
            final double var4 = Trajectories.mc.player.lastTickPosZ + (Trajectories.mc.player.posZ - Trajectories.mc.player.lastTickPosZ) * var1.getPartialTicks();
            Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            if (Trajectories.mc.gameSettings.thirdPersonView == 0 && (Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow || Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFishingRod || Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEnderPearl || Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEgg || Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSnowball || Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle)) {
                GL11.glPushMatrix();
                final Item var5 = Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
                double var6 = var2 - MathHelper.cos(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
                double var7 = var3 + Trajectories.mc.player.getEyeHeight() - 0.1000000014901161;
                double var8 = var4 - MathHelper.sin(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
                double var9 = -MathHelper.sin(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(Trajectories.mc.player.rotationPitch / 180.0f * 3.1415927f) * ((var5 instanceof ItemBow) ? 1.0 : 0.4);
                double var10 = -MathHelper.sin(Trajectories.mc.player.rotationPitch / 180.0f * 3.1415927f) * ((var5 instanceof ItemBow) ? 1.0 : 0.4);
                double var11 = MathHelper.cos(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(Trajectories.mc.player.rotationPitch / 180.0f * 3.1415927f) * ((var5 instanceof ItemBow) ? 1.0 : 0.4);
                final int var12 = 72000 - Trajectories.mc.player.getItemInUseCount();
                float var13 = var12 / 20.0f;
                var13 = (var13 * var13 + var13 * 2.0f) / 3.0f;
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                final float var14 = MathHelper.sqrt(var9 * var9 + var10 * var10 + var11 * var11);
                var9 /= var14;
                var10 /= var14;
                var11 /= var14;
                final float var15 = (var5 instanceof ItemBow) ? (var13 * 2.0f) : ((var5 instanceof ItemFishingRod) ? 1.25f : ((Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.9f : 1.0f));
                var9 *= var15 * ((var5 instanceof ItemFishingRod) ? 0.75f : ((Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));
                var10 *= var15 * ((var5 instanceof ItemFishingRod) ? 0.75f : ((Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));
                var11 *= var15 * ((var5 instanceof ItemFishingRod) ? 0.75f : ((Trajectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75f : 1.5f));
                this.enableGL3D(2.0f);
                GlStateManager.color(0.0f, 1.0f, 0.0f, 1.0f);
                GL11.glEnable(2848);
                final float var16 = (float)((var5 instanceof ItemBow) ? 0.3 : 0.25);
                boolean var17 = false;
                Entity var18 = null;
                RayTraceResult var19 = null;
                while (!var17 && var7 > 0.0) {
                    final Vec3d var20 = new Vec3d(var6, var7, var8);
                    final Vec3d var21 = new Vec3d(var6 + var9, var7 + var10, var8 + var11);
                    final RayTraceResult var22 = Trajectories.mc.world.rayTraceBlocks(var20, var21, false, true, false);
                    if (var22 != null && var22.typeOfHit != RayTraceResult.Type.MISS) {
                        var19 = var22;
                        var17 = true;
                    }
                    final AxisAlignedBB var23 = new AxisAlignedBB(var6 - var16, var7 - var16, var8 - var16, var6 + var16, var7 + var16, var8 + var16);
                    final List<Entity> var24 = this.getEntitiesWithinAABB(var23.offset(var9, var10, var11).expand(1.0, 1.0, 1.0));
                    for (final Entity var25 : var24) {
                        if (var25.canBeCollidedWith() && var25 != Trajectories.mc.player) {
                            final AxisAlignedBB var26 = var25.getEntityBoundingBox().expand(0.30000001192092896, 0.30000001192092896, 0.30000001192092896);
                            final RayTraceResult var27 = var26.calculateIntercept(var20, var21);
                            if (var27 == null) {
                                continue;
                            }
                            var17 = true;
                            var18 = var25;
                            var19 = var27;
                        }
                    }
                    if (var18 != null) {
                        GlStateManager.color(1.0f, 0.0f, 0.0f, 255.0f);
                    }
                    var6 += var9;
                    var7 += var10;
                    var8 += var11;
                    var9 *= 0.9900000095367432;
                    var10 *= 0.9900000095367432;
                    var11 *= 0.9900000095367432;
                    var10 -= ((var5 instanceof ItemBow) ? 0.05 : 0.03);
                    this.drawLine3D(var6 - var2, var7 - var3, var8 - var4);
                }
                if (var19 != null && var19.typeOfHit == RayTraceResult.Type.BLOCK) {
                    GlStateManager.translate(var6 - var2, var7 - var3, var8 - var4);
                    final int var28 = var19.sideHit.getIndex();
                    if (var28 == 2) {
                        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    }
                    else if (var28 == 3) {
                        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    }
                    else if (var28 == 4) {
                        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                    }
                    else if (var28 == 5) {
                        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                    }
                    final Cylinder var29 = new Cylinder();
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    var29.setDrawStyle(100011);
                    if (var18 != null) {
                        GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
                        GL11.glLineWidth(2.5f);
                        var29.draw(0.5f, 0.5f, 0.0f, (int)this.slices.getValue(), 1);
                        GL11.glLineWidth(0.1f);
                        GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
                    }
                    var29.draw((float)this.size.getValue(), (float)this.innerSize.getValue(), 0.0f, (int)this.slices.getValue(), 1);
                }
                this.disableGL3D();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void drawLine3D(final double var1, final double var3, final double var5) {
        GL11.glVertex3d(var1, var3, var5);
    }
    
    public void enableGL3D(final float var1) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Trajectories.mc.entityRenderer.disableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(var1);
    }
    
    public void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    private List<Entity> getEntitiesWithinAABB(final AxisAlignedBB var1) {
        final ArrayList<Entity> var2 = new ArrayList<Entity>();
        final int var3 = MathHelper.floor((var1.minX - 2.0) / 16.0);
        final int var4 = MathHelper.floor((var1.maxX + 2.0) / 16.0);
        final int var5 = MathHelper.floor((var1.minZ - 2.0) / 16.0);
        final int var6 = MathHelper.floor((var1.maxZ + 2.0) / 16.0);
        for (int var7 = var3; var7 <= var4; ++var7) {
            for (int var8 = var5; var8 <= var6; ++var8) {
                if (Trajectories.mc.world.getChunkProvider().getLoadedChunk(var7, var8) != null) {
                    Trajectories.mc.world.getChunk(var7, var8).getEntitiesWithinAABBForEntity((Entity)Trajectories.mc.player, var1, (List)var2, (Predicate)null);
                }
            }
        }
        return var2;
    }
}
