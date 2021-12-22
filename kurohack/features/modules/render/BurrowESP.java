//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.render;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.entity.player.*;
import kurohack.*;
import kurohack.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import kurohack.util.render.*;
import net.minecraft.init.*;
import java.awt.*;
import kurohack.event.events.*;
import java.util.function.*;

public class BurrowESP extends Module
{
    private final Setting<Integer> boxRed;
    private final Setting<Integer> outlineGreen;
    private final Setting<Integer> boxGreen;
    private final Setting<Boolean> box;
    private final Setting<Boolean> cOutline;
    private final Setting<Integer> outlineBlue;
    private final Setting<Boolean> name;
    private final Setting<Integer> boxAlpha;
    private final Setting<Float> outlineWidth;
    private final Setting<Integer> outlineRed;
    private final Setting<Boolean> outline;
    private final Setting<Integer> boxBlue;
    private final Map<EntityPlayer, BlockPos> burrowedPlayers;
    private final Setting<Integer> outlineAlpha;
    
    public BurrowESP() {
        super("BurrowESP", "Shows gay people.", Module.Category.RENDER, true, false, false);
        this.name = (Setting<Boolean>)this.register(new Setting("Name", (T)false));
        this.box = new Setting<Boolean>("Box", true);
        this.boxRed = (Setting<Integer>)this.register(new Setting("BoxRed", (T)255, (T)0, (T)255, v -> this.box.getValue()));
        this.boxGreen = (Setting<Integer>)this.register(new Setting("BoxGreen", (T)255, (T)0, (T)255, v -> this.box.getValue()));
        this.boxBlue = (Setting<Integer>)this.register(new Setting("BoxBlue", (T)255, (T)0, (T)255, v -> this.box.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255, v -> this.box.getValue()));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.outlineWidth = (Setting<Float>)this.register(new Setting("OutlineWidth", (T)1.0f, (T)0.0f, (T)5.0f, v -> this.outline.getValue()));
        this.cOutline = (Setting<Boolean>)this.register(new Setting("CustomOutline", (T)false, v -> this.outline.getValue()));
        this.outlineRed = (Setting<Integer>)this.register(new Setting("OutlineRed", (T)255, (T)0, (T)255, v -> this.cOutline.getValue()));
        this.outlineGreen = (Setting<Integer>)this.register(new Setting("OutlineGreen", (T)255, (T)0, (T)255, v -> this.cOutline.getValue()));
        this.outlineBlue = (Setting<Integer>)this.register(new Setting("OutlineBlue", (T)255, (T)0, (T)255, v -> this.cOutline.getValue()));
        this.outlineAlpha = (Setting<Integer>)this.register(new Setting("OutlineAlpha", (T)255, (T)0, (T)255, v -> this.cOutline.getValue()));
        this.burrowedPlayers = new HashMap<EntityPlayer, BlockPos>();
    }
    
    private void getPlayers() {
        for (final EntityPlayer entityPlayer : BurrowESP.mc.world.playerEntities) {
            if (entityPlayer != BurrowESP.mc.player && !KuroHack.friendManager.isFriend(entityPlayer.getName()) && EntityUtil.isLiving((Entity)entityPlayer)) {
                if (!this.isBurrowed(entityPlayer)) {
                    continue;
                }
                this.burrowedPlayers.put(entityPlayer, new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
            }
        }
    }
    
    public void onEnable() {
        this.burrowedPlayers.clear();
    }
    
    private void lambda$onRender3D$8(final Map.Entry entry) {
        this.renderBurrowedBlock(entry.getValue());
        if (this.name.getValue()) {
            RenderUtil.drawText(new AxisAlignedBB((BlockPos)entry.getValue()), ((EntityPlayer)entry.getKey()).getGameProfile().getName());
        }
    }
    
    private boolean isBurrowed(final EntityPlayer entityPlayer) {
        final BlockPos blockPos = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY + 0.2), Math.floor(entityPlayer.posZ));
        return BurrowESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || BurrowESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || BurrowESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST || BurrowESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.ANVIL;
    }
    
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        this.burrowedPlayers.clear();
        this.getPlayers();
    }
    
    private void renderBurrowedBlock(final BlockPos blockPos) {
        RenderUtil.drawBoxESP(blockPos, new Color(this.boxRed.getValue(), this.boxGreen.getValue(), this.boxBlue.getValue(), this.boxAlpha.getValue()), true, new Color(this.outlineRed.getValue(), this.outlineGreen.getValue(), this.outlineBlue.getValue(), this.outlineAlpha.getValue()), this.outlineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
    }
    
    public void onRender3D(final Render3DEvent render3DEvent) {
        if (!this.burrowedPlayers.isEmpty()) {
            this.burrowedPlayers.entrySet().forEach(this::lambda$onRender3D$8);
        }
    }
}
