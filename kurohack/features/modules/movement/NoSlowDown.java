//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.movement;

import kurohack.features.modules.*;
import net.minecraft.client.settings.*;
import kurohack.features.setting.*;
import kurohack.features.gui.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class NoSlowDown extends Module
{
    private static NoSlowDown INSTANCE;
    private static final KeyBinding[] keys;
    public final Setting<Double> webHorizontalFactor;
    public final Setting<Double> webVerticalFactor;
    public Setting<Boolean> guiMove;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> soulSand;
    public Setting<Boolean> strict;
    public Setting<Boolean> sneakPacket;
    public Setting<Boolean> endPortal;
    public Setting<Boolean> webs;
    private boolean sneaking;
    
    public NoSlowDown() {
        super("NoSlowDown", "Prevents you from getting slowed down.", Module.Category.MOVEMENT, true, false, false);
        this.webHorizontalFactor = (Setting<Double>)this.register(new Setting("WebHSpeed", (T)2.0, (T)0.0, (T)100.0));
        this.webVerticalFactor = (Setting<Double>)this.register(new Setting("WebVSpeed", (T)2.0, (T)0.0, (T)100.0));
        this.guiMove = (Setting<Boolean>)this.register(new Setting("GuiMove", (T)true));
        this.noSlow = (Setting<Boolean>)this.register(new Setting("NoSlow", (T)true));
        this.soulSand = (Setting<Boolean>)this.register(new Setting("SoulSand", (T)true));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.sneakPacket = (Setting<Boolean>)this.register(new Setting("SneakPacket", (T)false));
        this.endPortal = (Setting<Boolean>)this.register(new Setting("EndPortal", (T)false));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", (T)false));
        this.sneaking = false;
        this.setInstance();
    }
    
    public static NoSlowDown getInstance() {
        if (NoSlowDown.INSTANCE == null) {
            NoSlowDown.INSTANCE = new NoSlowDown();
        }
        return NoSlowDown.INSTANCE;
    }
    
    private void setInstance() {
        NoSlowDown.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (this.guiMove.getValue()) {
            if (NoSlowDown.mc.currentScreen instanceof GuiOptions || NoSlowDown.mc.currentScreen instanceof KuroHackGui || NoSlowDown.mc.currentScreen instanceof GuiVideoSettings || NoSlowDown.mc.currentScreen instanceof GuiScreenOptionsSounds || NoSlowDown.mc.currentScreen instanceof GuiContainer || NoSlowDown.mc.currentScreen instanceof GuiIngameMenu) {
                for (final KeyBinding bind : NoSlowDown.keys) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
                }
            }
            else if (NoSlowDown.mc.currentScreen == null) {
                for (final KeyBinding bind : NoSlowDown.keys) {
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
        if (this.webs.getValue() && NoSlowDown.mc.player.isInWeb) {
            final EntityPlayerSP player = NoSlowDown.mc.player;
            player.motionX *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player2 = NoSlowDown.mc.player;
            player2.motionZ *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player3 = NoSlowDown.mc.player;
            player3.motionY *= this.webVerticalFactor.getValue();
        }
        final Item item = NoSlowDown.mc.player.getActiveItemStack().getItem();
        if (this.sneaking && !NoSlowDown.mc.player.isHandActive() && this.sneakPacket.getValue()) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowDown.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = false;
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final PlayerInteractEvent.RightClickItem event) {
        final Item item = NoSlowDown.mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || (item instanceof ItemPotion && this.sneakPacket.getValue())) && !this.sneaking) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowDown.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent event) {
        if (this.noSlow.getValue() && NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.moveForward *= 5.0f;
        }
    }
    
    @SubscribeEvent
    public void onKeyEvent(final KeyEvent event) {
        if (this.guiMove.getValue() && event.getStage() == 0 && !(NoSlowDown.mc.currentScreen instanceof GuiChat)) {
            event.info = event.pressed;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.strict.getValue() && this.noSlow.getValue() && NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(NoSlowDown.mc.player.posX), Math.floor(NoSlowDown.mc.player.posY), Math.floor(NoSlowDown.mc.player.posZ)), EnumFacing.DOWN));
        }
    }
    
    static {
        NoSlowDown.INSTANCE = new NoSlowDown();
        keys = new KeyBinding[] { NoSlowDown.mc.gameSettings.keyBindForward, NoSlowDown.mc.gameSettings.keyBindBack, NoSlowDown.mc.gameSettings.keyBindLeft, NoSlowDown.mc.gameSettings.keyBindRight, NoSlowDown.mc.gameSettings.keyBindJump, NoSlowDown.mc.gameSettings.keyBindSprint };
    }
}
