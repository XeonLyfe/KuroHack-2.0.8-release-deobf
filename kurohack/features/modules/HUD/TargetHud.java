//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.HUD;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import kurohack.*;
import kurohack.features.modules.combat.*;
import kurohack.util.render.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;
import java.text.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import kurohack.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;

public class TargetHud extends Module
{
    public Setting<Boolean> targetHud;
    public Setting<Boolean> targetHudBackground;
    public Setting<Integer> targetHudX;
    public Setting<Integer> targetHudY;
    public Setting<Boolean> Line;
    public Setting<TargetHudDesign> design;
    
    public TargetHud() {
        super("TargetHud", "TargetHud", Category.HUD, false, false, true);
        this.targetHud = (Setting<Boolean>)this.register(new Setting("targetHud", (T)false));
        this.targetHudBackground = (Setting<Boolean>)this.register(new Setting("TargetHudBackground", (T)Boolean.TRUE, v -> this.targetHud.getValue()));
        this.targetHudX = (Setting<Integer>)this.register(new Setting("TargetHudX", (T)2, (T)0, (T)1000, v -> this.targetHud.getValue()));
        this.targetHudY = (Setting<Integer>)this.register(new Setting("TargetHudY", (T)2, (T)0, (T)1000, v -> this.targetHud.getValue()));
        this.Line = (Setting<Boolean>)this.register(new Setting("Line", (T)false, v -> this.targetHud.getValue()));
        this.design = (Setting<TargetHudDesign>)this.register(new Setting("Design", (T)TargetHudDesign.NORMAL, v -> this.targetHud.getValue()));
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.targetHud.getValue()) {
            this.drawTargetHud(event.partialTicks);
        }
        if (this.Line.getValue()) {
            Gui.drawRect((int)this.targetHudX.getValue(), (int)this.targetHudY.getValue(), this.targetHudX.getValue() + 230, this.targetHudY.getValue() + 1, Integer.MIN_VALUE);
        }
    }
    
    public static EntityPlayer getClosestEnemy() {
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer player : TargetHud.mc.world.playerEntities) {
            if (player != TargetHud.mc.player) {
                if (KuroHack.friendManager.isFriend(player)) {
                    continue;
                }
                if (closestPlayer == null) {
                    closestPlayer = player;
                }
                else {
                    if (TargetHud.mc.player.getDistanceSq((Entity)player) >= TargetHud.mc.player.getDistanceSq((Entity)closestPlayer)) {
                        continue;
                    }
                    closestPlayer = player;
                }
            }
        }
        return closestPlayer;
    }
    
    public void drawTargetHud(final float partialTicks) {
        if (this.design.getValue() == TargetHudDesign.NORMAL) {
            final EntityPlayer target = (AutoCrystal.target != null) ? AutoCrystal.target : ((Killaura.target instanceof EntityPlayer) ? Killaura.target : getClosestEnemy());
            if (target == null) {
                return;
            }
            if (this.targetHudBackground.getValue()) {
                RenderUtil.drawRectangleCorrectly(this.targetHudX.getValue(), this.targetHudY.getValue(), 230, 87, ColorUtil.toRGBA(30, 30, 30, 240));
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            try {
                GuiInventory.drawEntityOnScreen(this.targetHudX.getValue() + 30, this.targetHudY.getValue() + 85, 35, 0.0f, 0.0f, (EntityLivingBase)target);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.renderer.drawStringWithShadow(target.getName(), (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + 10), ColorUtil.toRGBA(255, 255, 255, 255));
            final float health = target.getHealth() + target.getAbsorptionAmount();
            final int healthColor = (health >= 16.0f) ? ColorUtil.toRGBA(0, 255, 0, 240) : ((health >= 10.0f) ? ColorUtil.toRGBA(255, 255, 0, 240) : ColorUtil.toRGBA(255, 0, 0, 245));
            final DecimalFormat df = new DecimalFormat("##.#");
            this.renderer.drawStringWithShadow(df.format(target.getHealth() + target.getAbsorptionAmount()), (float)(this.targetHudX.getValue() + 60 + this.renderer.getStringWidth(target.getName() + "  ")), (float)(this.targetHudY.getValue() + 10), healthColor);
            int ping;
            if (EntityUtil.isFakePlayer(target)) {
                ping = 0;
            }
            else {
                Objects.requireNonNull(TargetHud.mc.getConnection()).getPlayerInfo(target.getUniqueID());
                ping = TargetHud.mc.getConnection().getPlayerInfo(target.getUniqueID()).getResponseTime();
            }
            final int color = (ping >= 100) ? ColorUtil.toRGBA(0, 255, 0, 255) : ((ping > 50) ? ColorUtil.toRGBA(255, 255, 0, 255) : ColorUtil.toRGBA(255, 255, 255, 255));
            this.renderer.drawStringWithShadow("Ping: " + ping, (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + this.renderer.getFontHeight() + 20), color);
            this.renderer.drawStringWithShadow("Popping: " + KuroHack.totemPopManager.getTotemPops(target), (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + this.renderer.getFontHeight() * 2 + 30), ColorUtil.toRGBA(255, 255, 255, 255));
            GlStateManager.enableTexture2D();
            int iteration = 0;
            final int i = this.targetHudX.getValue() + 50;
            final int y = this.targetHudY.getValue() + this.renderer.getFontHeight() * 3 + 44;
            for (final ItemStack is : target.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) {
                    continue;
                }
                final int x = i - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                RenderUtil.itemRender.zLevel = 200.0f;
                RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
                RenderUtil.itemRender.renderItemOverlayIntoGUI(TargetHud.mc.fontRenderer, is, x, y, "");
                RenderUtil.itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
                this.renderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.renderer.getStringWidth(s)), (float)(y + 9), 16777215);
                final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
                final float red = 1.0f - green;
                final int dmg = 100 - (int)(red * 100.0f);
                this.renderer.drawStringWithShadow(dmg + "", x + 8 - this.renderer.getStringWidth(dmg + "") / 2.0f, (float)(y - 5), ColorUtil.toRGBA((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
            this.drawOverlay(partialTicks, (Entity)target, this.targetHudX.getValue() + 150, this.targetHudY.getValue() + 6);
        }
    }
    
    public void drawOverlay(final float partialTicks, final Entity player, final int x, final int y) {
        float yaw = 0.0f;
        final int dir = MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        switch (dir) {
            case 1: {
                yaw = 90.0f;
                break;
            }
            case 2: {
                yaw = -180.0f;
                break;
            }
            case 3: {
                yaw = -90.0f;
                break;
            }
        }
        final BlockPos northPos = this.traceToBlock(partialTicks, yaw, player);
        final Block north = this.getBlock(northPos);
        if (north != null && north != Blocks.AIR) {
            final int damage = this.getBlockDamage(northPos);
            if (damage != 0) {
                RenderUtil.drawRect((float)(x + 16), (float)y, (float)(x + 32), (float)(y + 16), 1627324416);
            }
            this.drawBlock(north, (float)(x + 16), (float)y);
        }
        final BlockPos southPos;
        final Block south;
        if ((south = this.getBlock(southPos = this.traceToBlock(partialTicks, yaw - 180.0f, player))) != null && south != Blocks.AIR) {
            final int damage = this.getBlockDamage(southPos);
            if (damage != 0) {
                RenderUtil.drawRect((float)(x + 16), (float)(y + 32), (float)(x + 32), (float)(y + 48), 1627324416);
            }
            this.drawBlock(south, (float)(x + 16), (float)(y + 32));
        }
        final BlockPos eastPos;
        final Block east;
        if ((east = this.getBlock(eastPos = this.traceToBlock(partialTicks, yaw + 90.0f, player))) != null && east != Blocks.AIR) {
            final int damage = this.getBlockDamage(eastPos);
            if (damage != 0) {
                RenderUtil.drawRect((float)(x + 32), (float)(y + 16), (float)(x + 48), (float)(y + 32), 1627324416);
            }
            this.drawBlock(east, (float)(x + 32), (float)(y + 16));
        }
        final BlockPos westPos;
        final Block west;
        if ((west = this.getBlock(westPos = this.traceToBlock(partialTicks, yaw - 90.0f, player))) != null && west != Blocks.AIR) {
            final int damage = this.getBlockDamage(westPos);
            if (damage != 0) {
                RenderUtil.drawRect((float)x, (float)(y + 16), (float)(x + 16), (float)(y + 32), 1627324416);
            }
            this.drawBlock(west, (float)x, (float)(y + 16));
        }
    }
    
    private int getBlockDamage(final BlockPos pos) {
        for (final DestroyBlockProgress destBlockProgress : TargetHud.mc.renderGlobal.damagedBlocks.values()) {
            if (destBlockProgress.getPosition().getX() == pos.getX() && destBlockProgress.getPosition().getY() == pos.getY()) {
                if (destBlockProgress.getPosition().getZ() != pos.getZ()) {
                    continue;
                }
                return destBlockProgress.getPartialBlockDamage();
            }
        }
        return 0;
    }
    
    private BlockPos traceToBlock(final float partialTicks, final float yaw, final Entity player) {
        final Vec3d pos = EntityUtil.interpolateEntity(player, partialTicks);
        final Vec3d dir = MathUtil.direction(yaw);
        return new BlockPos(pos.x + dir.x, pos.y, pos.z + dir.z);
    }
    
    private Block getBlock(final BlockPos pos) {
        final Block block = TargetHud.mc.world.getBlockState(pos).getBlock();
        if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN) {
            return block;
        }
        return Blocks.AIR;
    }
    
    private void drawBlock(final Block block, final float x, final float y) {
        final ItemStack stack = new ItemStack(block);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translate(x, y, 0.0f);
        TargetHud.mc.getRenderItem().zLevel = 501.0f;
        TargetHud.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
        TargetHud.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    public enum TargetHudDesign
    {
        NORMAL, 
        COMPACT;
    }
}
