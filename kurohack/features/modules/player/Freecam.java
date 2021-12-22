//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import kurohack.features.*;
import net.minecraft.world.*;
import kurohack.util.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import kurohack.event.events.*;

public class Freecam extends Module
{
    private static Freecam INSTANCE;
    public Setting<Double> speed;
    public Setting<Boolean> view;
    public Setting<Boolean> packet;
    public Setting<Boolean> disable;
    public Setting<Boolean> legit;
    private AxisAlignedBB oldBoundingBox;
    private EntityOtherPlayerMP entity;
    private Vec3d position;
    private Entity riding;
    private float yaw;
    private float pitch;
    
    public Freecam() {
        super("Freecam", "Look around freely.", Module.Category.PLAYER, true, false, false);
        this.speed = (Setting<Double>)this.register(new Setting("Speed", (T)0.5, (T)0.1, (T)5.0));
        this.view = (Setting<Boolean>)this.register(new Setting("3D", (T)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)true));
        this.disable = (Setting<Boolean>)this.register(new Setting("Logout/Off", (T)true));
        this.legit = (Setting<Boolean>)this.register(new Setting("Legit", (T)false));
        this.setInstance();
    }
    
    public static Freecam getInstance() {
        if (Freecam.INSTANCE == null) {
            Freecam.INSTANCE = new Freecam();
        }
        return Freecam.INSTANCE;
    }
    
    private void setInstance() {
        Freecam.INSTANCE = this;
    }
    
    public void onEnable() {
        if (!Feature.fullNullCheck()) {
            this.oldBoundingBox = Freecam.mc.player.getEntityBoundingBox();
            Freecam.mc.player.setEntityBoundingBox(new AxisAlignedBB(Freecam.mc.player.posX, Freecam.mc.player.posY, Freecam.mc.player.posZ, Freecam.mc.player.posX, Freecam.mc.player.posY, Freecam.mc.player.posZ));
            if (Freecam.mc.player.getRidingEntity() != null) {
                this.riding = Freecam.mc.player.getRidingEntity();
                Freecam.mc.player.dismountRidingEntity();
            }
            (this.entity = new EntityOtherPlayerMP((World)Freecam.mc.world, Freecam.mc.session.getProfile())).copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.entity.rotationYaw = Freecam.mc.player.rotationYaw;
            this.entity.rotationYawHead = Freecam.mc.player.rotationYawHead;
            this.entity.inventory.copyInventory(Freecam.mc.player.inventory);
            Freecam.mc.world.addEntityToWorld(69420, (Entity)this.entity);
            this.position = Freecam.mc.player.getPositionVector();
            this.yaw = Freecam.mc.player.rotationYaw;
            this.pitch = Freecam.mc.player.rotationPitch;
            Freecam.mc.player.noClip = true;
        }
    }
    
    public void onDisable() {
        if (!Feature.fullNullCheck()) {
            Freecam.mc.player.setEntityBoundingBox(this.oldBoundingBox);
            if (this.riding != null) {
                Freecam.mc.player.startRiding(this.riding, true);
            }
            if (this.entity != null) {
                Freecam.mc.world.removeEntity((Entity)this.entity);
            }
            if (this.position != null) {
                Freecam.mc.player.setPosition(this.position.x, this.position.y, this.position.z);
            }
            Freecam.mc.player.rotationYaw = this.yaw;
            Freecam.mc.player.rotationPitch = this.pitch;
            Freecam.mc.player.noClip = false;
        }
    }
    
    public void onUpdate() {
        Freecam.mc.player.noClip = true;
        Freecam.mc.player.setVelocity(0.0, 0.0, 0.0);
        Freecam.mc.player.jumpMovementFactor = this.speed.getValue().floatValue();
        final double[] dir = MathUtil.directionSpeed(this.speed.getValue());
        if (Freecam.mc.player.movementInput.moveStrafe != 0.0f || Freecam.mc.player.movementInput.moveForward != 0.0f) {
            Freecam.mc.player.motionX = dir[0];
            Freecam.mc.player.motionZ = dir[1];
        }
        else {
            Freecam.mc.player.motionX = 0.0;
            Freecam.mc.player.motionZ = 0.0;
        }
        Freecam.mc.player.setSprinting(false);
        if (this.view.getValue() && !Freecam.mc.gameSettings.keyBindSneak.isKeyDown() && !Freecam.mc.gameSettings.keyBindJump.isKeyDown()) {
            Freecam.mc.player.motionY = this.speed.getValue() * -MathUtil.degToRad(Freecam.mc.player.rotationPitch) * Freecam.mc.player.movementInput.moveForward;
        }
        if (Freecam.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP player;
            final EntityPlayerSP player = player = Freecam.mc.player;
            player.motionY += this.speed.getValue();
        }
        if (Freecam.mc.gameSettings.keyBindSneak.isKeyDown()) {
            final EntityPlayerSP player2;
            final EntityPlayerSP player2 = player2 = Freecam.mc.player;
            player2.motionY -= this.speed.getValue();
        }
    }
    
    public void onLogout() {
        if (this.disable.getValue()) {
            this.disable();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.legit.getValue() && this.entity != null && event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packetPlayer = (CPacketPlayer)event.getPacket();
            packetPlayer.x = this.entity.posX;
            packetPlayer.y = this.entity.posY;
            packetPlayer.z = this.entity.posZ;
            return;
        }
        if (this.packet.getValue()) {
            if (event.getPacket() instanceof CPacketPlayer) {
                event.setCanceled(true);
            }
        }
        else if (!(event.getPacket() instanceof CPacketUseEntity) && !(event.getPacket() instanceof CPacketPlayerTryUseItem) && !(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) && !(event.getPacket() instanceof CPacketPlayer) && !(event.getPacket() instanceof CPacketVehicleMove) && !(event.getPacket() instanceof CPacketChatMessage) && !(event.getPacket() instanceof CPacketKeepAlive)) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSetPassengers) {
            final SPacketSetPassengers packet = (SPacketSetPassengers)event.getPacket();
            final Entity riding = Freecam.mc.world.getEntityByID(packet.getEntityId());
            if (riding != null && riding == this.riding) {
                this.riding = null;
            }
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet2 = (SPacketPlayerPosLook)event.getPacket();
            if (this.packet.getValue()) {
                if (this.entity != null) {
                    this.entity.setPositionAndRotation(packet2.getX(), packet2.getY(), packet2.getZ(), packet2.getYaw(), packet2.getPitch());
                }
                this.position = new Vec3d(packet2.getX(), packet2.getY(), packet2.getZ());
                Freecam.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(packet2.getTeleportId()));
                event.setCanceled(true);
            }
            else {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onPush(final PushEvent event) {
        if (event.getStage() == 1) {
            event.setCanceled(true);
        }
    }
    
    static {
        Freecam.INSTANCE = new Freecam();
    }
}
