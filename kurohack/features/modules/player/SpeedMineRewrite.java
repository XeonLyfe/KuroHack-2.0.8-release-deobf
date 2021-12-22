//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import kurohack.*;
import java.awt.*;
import kurohack.util.render.*;
import kurohack.event.events.*;
import kurohack.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;

public class SpeedMineRewrite extends Module
{
    private static SpeedMineRewrite INSTANCE;
    private final PhobosTimer timer;
    public Setting<Mode> mode;
    public Setting<Float> damage;
    public Setting<Float> startDamage;
    public Setting<Float> endDamage;
    public Setting<Integer> breakDelay;
    public Setting<Boolean> webSwitch;
    public Setting<Boolean> doubleBreak;
    public Setting<Boolean> render;
    public Setting<Boolean> box;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    private final Setting<Integer> boxAlpha;
    public Setting<Boolean> outline;
    private final Setting<Float> lineWidth;
    public BlockPos currentPos;
    public IBlockState currentBlockState;
    private int breakTick;
    private BlockPos lastBreak;
    private EnumFacing facing;
    private boolean before;
    
    public SpeedMineRewrite() {
        super("SpeedMineRewrite", "Speeds up mining.", Module.Category.PLAYER, true, false, false);
        this.timer = new PhobosTimer();
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.PACKET));
        this.damage = (Setting<Float>)this.register(new Setting("Damage", (T)0.7f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.DAMAGE));
        this.startDamage = (Setting<Float>)this.register(new Setting("Start Damage", (T)0.2f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.BREAKER));
        this.endDamage = (Setting<Float>)this.register(new Setting("End Damage", (T)0.7f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.BREAKER));
        this.breakDelay = (Setting<Integer>)this.register(new Setting("Break Delay", (T)2, (T)0, (T)10, v -> this.mode.getValue() == Mode.BREAKER));
        this.webSwitch = (Setting<Boolean>)this.register(new Setting("WebSwitch", (T)false));
        this.doubleBreak = (Setting<Boolean>)this.register(new Setting("DoubleBreak", (T)false));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)false));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)false, v -> this.render.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)125, (T)0, (T)255, v -> this.render.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255, v -> this.render.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)85, (T)0, (T)255, v -> this.box.getValue() && this.render.getValue()));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true, v -> this.render.getValue()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("Width", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getValue() && this.render.getValue()));
        this.breakTick = 0;
        this.lastBreak = null;
        this.facing = null;
        this.before = false;
        this.setInstance();
    }
    
    public static SpeedMineRewrite getInstance() {
        if (SpeedMineRewrite.INSTANCE == null) {
            SpeedMineRewrite.INSTANCE = new SpeedMineRewrite();
        }
        return SpeedMineRewrite.INSTANCE;
    }
    
    private void setInstance() {
        SpeedMineRewrite.INSTANCE = this;
    }
    
    public void onTick() {
        if (this.currentPos != null) {
            if (!SpeedMineRewrite.mc.world.getBlockState(this.currentPos).equals(this.currentBlockState) || SpeedMineRewrite.mc.world.getBlockState(this.currentPos).getBlock() == Blocks.AIR) {
                this.currentPos = null;
                this.currentBlockState = null;
            }
            else if (this.webSwitch.getValue() && this.currentBlockState.getBlock() == Blocks.WEB && SpeedMineRewrite.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                InventoryUtil.switchToHotbarSlot(ItemSword.class, false);
            }
        }
    }
    
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        SpeedMineRewrite.mc.playerController.blockHitDelay = 0;
        if (this.mode.getValue() == Mode.BREAKER && this.lastBreak != null) {
            if (this.before) {
                if (!(this.getBlock(this.lastBreak) instanceof BlockAir)) {
                    this.breakerBreak();
                }
            }
            else if (this.getBlock(this.lastBreak) instanceof BlockAir) {
                this.before = true;
            }
        }
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.currentPos != null && this.currentBlockState.getBlock() == Blocks.OBSIDIAN) {
            final Color color = new Color(this.timer.passedMs((int)(2000.0f * KuroHack.serverManager.getTpsFactor())) ? 0 : 255, this.timer.passedMs((int)(2000.0f * KuroHack.serverManager.getTpsFactor())) ? 255 : 0, 0, 255);
            RenderUtil.drawBoxESP(this.currentPos, color, false, color, this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), false);
        }
    }
    
    @SubscribeEvent
    public void onBlockEvent(final BlockEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getStage() == 3 && SpeedMineRewrite.mc.playerController.curBlockDamageMP > 0.1f) {
            SpeedMineRewrite.mc.playerController.isHittingBlock = true;
        }
        if (event.getStage() == 4) {
            if (BlockUtil.canBreak(event.pos)) {
                SpeedMineRewrite.mc.playerController.isHittingBlock = false;
                switch (this.mode.getValue()) {
                    case PACKET: {
                        if (this.currentPos == null) {
                            this.currentPos = event.pos;
                            this.currentBlockState = SpeedMineRewrite.mc.world.getBlockState(this.currentPos);
                            this.timer.reset();
                        }
                        SpeedMineRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
                        SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
                        SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
                        event.setCanceled(true);
                        break;
                    }
                    case DAMAGE: {
                        if (SpeedMineRewrite.mc.playerController.curBlockDamageMP < this.damage.getValue()) {
                            break;
                        }
                        SpeedMineRewrite.mc.playerController.curBlockDamageMP = 1.0f;
                        break;
                    }
                    case BREAKER: {
                        this.breakerAlgo(event);
                        this.breakerBreak();
                    }
                    case INSTANT: {
                        SpeedMineRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
                        SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
                        SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
                        SpeedMineRewrite.mc.playerController.onPlayerDestroyBlock(event.pos);
                        SpeedMineRewrite.mc.world.setBlockToAir(event.pos);
                        break;
                    }
                }
            }
            final BlockPos above;
            if (this.doubleBreak.getValue() && BlockUtil.canBreak(above = event.pos.add(0, 1, 0)) && SpeedMineRewrite.mc.player.getDistance((double)above.getX(), (double)above.getY(), (double)above.getZ()) <= 5.0) {
                SpeedMineRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
                SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, event.facing));
                SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, above, event.facing));
                SpeedMineRewrite.mc.playerController.onPlayerDestroyBlock(above);
                SpeedMineRewrite.mc.world.setBlockToAir(above);
            }
        }
    }
    
    private void breakerAlgo(final BlockEvent event) {
        if (this.lastBreak == null || event.pos.getX() != this.lastBreak.getX() || event.pos.getY() != this.lastBreak.getY() || event.pos.getZ() != this.lastBreak.getZ()) {
            SpeedMineRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
            SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
            this.before = true;
            this.lastBreak = event.pos;
            this.facing = event.facing;
        }
        if (this.breakDelay.getValue() <= this.breakTick++) {
            this.breakerBreak();
            event.setCanceled(true);
            this.breakTick = 0;
        }
    }
    
    private void breakerBreak() {
        SpeedMineRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBreak, this.facing));
    }
    
    private Block getBlock(final BlockPos b) {
        return SpeedMineRewrite.mc.world.getBlockState(b).getBlock();
    }
    
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    static {
        SpeedMineRewrite.INSTANCE = new SpeedMineRewrite();
    }
    
    public enum Mode
    {
        PACKET, 
        DAMAGE, 
        INSTANT, 
        BREAKER;
    }
}
