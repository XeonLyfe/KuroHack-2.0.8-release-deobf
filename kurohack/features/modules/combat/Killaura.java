//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.event.events.*;
import kurohack.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import kurohack.util.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.entity.*;

public class Killaura extends Module
{
    public static Entity target;
    private final Timer timer;
    public Setting<Float> range;
    public Setting<Boolean> autoSwitch;
    public Setting<Boolean> delay;
    public Setting<Boolean> rotate;
    public Setting<Boolean> stay;
    public Setting<Boolean> armorBreak;
    public Setting<Boolean> eating;
    public Setting<Boolean> onlySharp;
    public Setting<Boolean> teleport;
    public Setting<Float> raytrace;
    public Setting<Float> teleportRange;
    public Setting<Boolean> lagBack;
    public Setting<Boolean> teekaydelay;
    public Setting<Integer> time32k;
    public Setting<Integer> multi;
    public Setting<Boolean> multi32k;
    public Setting<Boolean> players;
    public Setting<Boolean> mobs;
    public Setting<Boolean> animals;
    public Setting<Boolean> vehicles;
    public Setting<Boolean> projectiles;
    public Setting<Boolean> tps;
    public Setting<Boolean> packet;
    public Setting<Boolean> swing;
    public Setting<Boolean> sneak;
    public Setting<Boolean> info;
    private final Setting<TargetMode> targetMode;
    public Setting<Float> health;
    
    public Killaura() {
        super("Killaura", "Kills aura.", Category.COMBAT, true, false, false);
        this.timer = new Timer();
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)0.1f, (T)7.0f));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)false));
        this.delay = (Setting<Boolean>)this.register(new Setting("Delay", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.stay = (Setting<Boolean>)this.register(new Setting("Stay", (T)true, v -> this.rotate.getValue()));
        this.armorBreak = (Setting<Boolean>)this.register(new Setting("ArmorBreak", (T)false));
        this.eating = (Setting<Boolean>)this.register(new Setting("Eating", (T)true));
        this.onlySharp = (Setting<Boolean>)this.register(new Setting("Axe/Sword", (T)true));
        this.teleport = (Setting<Boolean>)this.register(new Setting("Teleport", (T)false));
        this.raytrace = (Setting<Float>)this.register(new Setting("Raytrace", (T)6.0f, (T)0.1f, (T)7.0f, v -> !this.teleport.getValue(), "Wall Range."));
        this.teleportRange = (Setting<Float>)this.register(new Setting("TpRange", (T)15.0f, (T)0.1f, (T)50.0f, v -> this.teleport.getValue(), "Teleport Range."));
        this.lagBack = (Setting<Boolean>)this.register(new Setting("LagBack", (T)true, v -> this.teleport.getValue()));
        this.teekaydelay = (Setting<Boolean>)this.register(new Setting("32kDelay", (T)false));
        this.time32k = (Setting<Integer>)this.register(new Setting("32kTime", (T)5, (T)1, (T)50));
        this.multi = (Setting<Integer>)this.register(new Setting("32kPackets", (T)2, v -> !this.teekaydelay.getValue()));
        this.multi32k = (Setting<Boolean>)this.register(new Setting("Multi32k", (T)false));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", (T)true));
        this.mobs = (Setting<Boolean>)this.register(new Setting("Mobs", (T)false));
        this.animals = (Setting<Boolean>)this.register(new Setting("Animals", (T)false));
        this.vehicles = (Setting<Boolean>)this.register(new Setting("Entities", (T)false));
        this.projectiles = (Setting<Boolean>)this.register(new Setting("Projectiles", (T)false));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", (T)true));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.swing = (Setting<Boolean>)this.register(new Setting("Swing", (T)true));
        this.sneak = (Setting<Boolean>)this.register(new Setting("State", (T)false));
        this.info = (Setting<Boolean>)this.register(new Setting("Info", (T)true));
        this.targetMode = (Setting<TargetMode>)this.register(new Setting("Target", (T)TargetMode.CLOSEST));
        this.health = (Setting<Float>)this.register(new Setting("Health", (T)6.0f, (T)0.1f, (T)36.0f, v -> this.targetMode.getValue() == TargetMode.SMART));
    }
    
    @Override
    public void onTick() {
        if (!this.rotate.getValue()) {
            this.doKillaura();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && this.rotate.getValue()) {
            if (this.stay.getValue() && Killaura.target != null) {
                KuroHack.rotationManager.lookAtEntity(Killaura.target);
            }
            this.doKillaura();
        }
    }
    
    private void doKillaura() {
        if (this.onlySharp.getValue() && !EntityUtil.holdingWeapon((EntityPlayer)Killaura.mc.player)) {
            Killaura.target = null;
            return;
        }
        int n = 0;
        if (this.delay.getValue() && (!EntityUtil.holding32k((EntityPlayer)Killaura.mc.player) || this.teekaydelay.getValue())) {
            n = (int)(DamageUtil.getCooldownByWeapon((EntityPlayer)Killaura.mc.player) * (this.tps.getValue() ? KuroHack.serverManager.getTpsFactor() : 1.0f));
        }
        final int wait = n;
        if (!this.timer.passedMs(wait) || (!this.eating.getValue() && Killaura.mc.player.isHandActive() && (!Killaura.mc.player.getHeldItemOffhand().getItem().equals(Items.SHIELD) || Killaura.mc.player.getActiveHand() != EnumHand.OFF_HAND))) {
            return;
        }
        if (this.targetMode.getValue() != TargetMode.FOCUS || Killaura.target == null || (Killaura.mc.player.getDistanceSq(Killaura.target) >= MathUtil.square(this.range.getValue()) && (!this.teleport.getValue() || Killaura.mc.player.getDistanceSq(Killaura.target) >= MathUtil.square(this.teleportRange.getValue()))) || (!Killaura.mc.player.canEntityBeSeen(Killaura.target) && !EntityUtil.canEntityFeetBeSeen(Killaura.target) && Killaura.mc.player.getDistanceSq(Killaura.target) >= MathUtil.square(this.raytrace.getValue()) && !this.teleport.getValue())) {
            Killaura.target = this.getTarget();
        }
        if (Killaura.target == null) {
            return;
        }
        final int sword;
        if (this.autoSwitch.getValue() && (sword = InventoryUtil.findHotbarBlock(ItemSword.class)) != -1) {
            InventoryUtil.switchToHotbarSlot(sword, false);
        }
        if (this.rotate.getValue()) {
            KuroHack.rotationManager.lookAtEntity(Killaura.target);
        }
        if (this.teleport.getValue()) {
            KuroHack.positionManager.setPositionPacket(Killaura.target.posX, EntityUtil.canEntityFeetBeSeen(Killaura.target) ? Killaura.target.posY : (Killaura.target.posY + Killaura.target.getEyeHeight()), Killaura.target.posZ, true, true, !this.lagBack.getValue());
        }
        if (EntityUtil.holding32k((EntityPlayer)Killaura.mc.player) && !this.teekaydelay.getValue()) {
            if (this.multi32k.getValue()) {
                for (final EntityPlayer player : Killaura.mc.world.playerEntities) {
                    if (!EntityUtil.isValid((Entity)player, this.range.getValue())) {
                        continue;
                    }
                    this.teekayAttack((Entity)player);
                }
            }
            else {
                this.teekayAttack(Killaura.target);
            }
            this.timer.reset();
            return;
        }
        if (this.armorBreak.getValue()) {
            Killaura.mc.playerController.windowClick(Killaura.mc.player.inventoryContainer.windowId, 9, Killaura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Killaura.mc.player);
            EntityUtil.attackEntity(Killaura.target, this.packet.getValue(), this.swing.getValue());
            Killaura.mc.playerController.windowClick(Killaura.mc.player.inventoryContainer.windowId, 9, Killaura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Killaura.mc.player);
            EntityUtil.attackEntity(Killaura.target, this.packet.getValue(), this.swing.getValue());
        }
        else {
            final boolean sneaking = Killaura.mc.player.isSneaking();
            final boolean sprint = Killaura.mc.player.isSprinting();
            if (this.sneak.getValue()) {
                if (sneaking) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (sprint) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
            EntityUtil.attackEntity(Killaura.target, this.packet.getValue(), this.swing.getValue());
            if (this.sneak.getValue()) {
                if (sprint) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                if (sneaking) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
            }
        }
        this.timer.reset();
    }
    
    private void teekayAttack(final Entity entity) {
        for (int i = 0; i < this.multi.getValue(); ++i) {
            this.startEntityAttackThread(entity, i * this.time32k.getValue());
        }
    }
    
    private void startEntityAttackThread(final Entity entity, final int time) {
        final Timer timer;
        new Thread(() -> {
            timer = new Timer();
            timer.reset();
            try {
                Thread.sleep(time);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            EntityUtil.attackEntity(entity, true, this.swing.getValue());
        }).start();
    }
    
    private Entity getTarget() {
        Entity target = null;
        double distance = (double)(this.teleport.getValue() ? this.teleportRange.getValue() : ((double)(float)this.range.getValue()));
        double maxHealth = 36.0;
        for (final Entity entity : Killaura.mc.world.loadedEntityList) {
            if (((this.players.getValue() && entity instanceof EntityPlayer) || (this.animals.getValue() && EntityUtil.isPassive(entity)) || (this.mobs.getValue() && EntityUtil.isMobAggressive(entity)) || (this.vehicles.getValue() && EntityUtil.isVehicle(entity)) || (this.projectiles.getValue() && EntityUtil.isProjectile(entity))) && (!(entity instanceof EntityLivingBase) || !EntityUtil.isntValid(entity, distance))) {
                if (!this.teleport.getValue() && !Killaura.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && Killaura.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue())) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = Killaura.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
                else {
                    if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                        target = entity;
                        break;
                    }
                    if (this.targetMode.getValue() == TargetMode.SMART && EntityUtil.getHealth(entity) < this.health.getValue()) {
                        target = entity;
                        break;
                    }
                    if (this.targetMode.getValue() != TargetMode.HEALTH && Killaura.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = Killaura.mc.player.getDistanceSq(entity);
                        maxHealth = EntityUtil.getHealth(entity);
                    }
                    if (this.targetMode.getValue() != TargetMode.HEALTH) {
                        continue;
                    }
                    if (EntityUtil.getHealth(entity) >= maxHealth) {
                        continue;
                    }
                    target = entity;
                    distance = Killaura.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
            }
        }
        return target;
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.info.getValue() && Killaura.target instanceof EntityPlayer) {
            return Killaura.target.getName();
        }
        return null;
    }
    
    public enum TargetMode
    {
        FOCUS, 
        CLOSEST, 
        HEALTH, 
        SMART;
    }
}
