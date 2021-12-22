//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import java.util.concurrent.atomic.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import kurohack.*;
import kurohack.features.modules.client.*;
import kurohack.features.modules.misc.*;
import net.minecraftforge.fml.common.gameevent.*;
import kurohack.features.modules.combat.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import com.google.common.base.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import kurohack.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.nio.*;
import kurohack.event.events.*;
import net.minecraftforge.client.event.*;
import kurohack.features.command.*;

public class EventManager extends Feature
{
    private final Timer timer;
    private final Timer logoutTimer;
    private final Timer switchTimer;
    private boolean keyTimeout;
    private final AtomicBoolean tickOngoing;
    
    public EventManager() {
        this.timer = new Timer();
        this.logoutTimer = new Timer();
        this.switchTimer = new Timer();
        this.tickOngoing = new AtomicBoolean(false);
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onUnload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!fullNullCheck() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals((Object)EventManager.mc.player)) {
            KuroHack.potionManager.update();
            KuroHack.totemPopManager.onUpdate();
            KuroHack.inventoryManager.update();
            KuroHack.holeManager.update();
            KuroHack.safetyManager.onUpdate();
            KuroHack.moduleManager.onUpdate();
            KuroHack.timerManager.update();
            if (this.timer.passedMs((int)Managers.getInstance().moduleListUpdates.getValue())) {
                KuroHack.moduleManager.sortModules(true);
                KuroHack.moduleManager.alphabeticallySortModules();
                this.timer.reset();
            }
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && EventManager.mc.getConnection() != null && ServerModule.getInstance().isConnected() && EventManager.mc.world != null) {
            final String command = "@Server" + ServerModule.getInstance().getServerPrefix() + "module " + event.getSetting().getFeature().getName() + " set " + event.getSetting().getName() + " " + event.getSetting().getPlannedValue().toString();
            final CPacketChatMessage cPacketChatMessage = new CPacketChatMessage(command);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTickHighest(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            this.tickOngoing.set(true);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTickLowest(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            this.tickOngoing.set(false);
            AutoCrystal.getInstance().postTick();
        }
    }
    
    public boolean ticksOngoing() {
        return this.tickOngoing.get();
    }
    
    @SubscribeEvent
    public void onClientConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.logoutTimer.reset();
        KuroHack.moduleManager.onLogin();
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        KuroHack.moduleManager.onLogout();
        KuroHack.totemPopManager.onLogout();
        KuroHack.potionManager.onLogout();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (fullNullCheck()) {
            return;
        }
        KuroHack.moduleManager.onTick();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getStage() == 0) {
            KuroHack.baritoneManager.onUpdateWalkingPlayer();
            KuroHack.speedManager.updateValues();
            KuroHack.rotationManager.updateRotations();
            KuroHack.positionManager.updatePosition();
        }
        if (event.getStage() == 1) {
            KuroHack.rotationManager.restoreRotations();
            KuroHack.positionManager.restorePosition();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketHeldItemChange) {
            this.switchTimer.reset();
        }
    }
    
    public boolean isOnSwitchCoolDown() {
        return !this.switchTimer.passedMs(500L);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() != 0) {
            return;
        }
        KuroHack.serverManager.onPacketReceived();
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity((World)EventManager.mc.world) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)packet.getEntity((World)EventManager.mc.world);
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player, player.entityId));
                KuroHack.totemPopManager.onTotemPop(player);
                KuroHack.potionManager.onTotemPop(player);
            }
        }
        else if (event.getPacket() instanceof SPacketPlayerListItem && !fullNullCheck() && this.logoutTimer.passedS(1.0)) {
            final SPacketPlayerListItem packet2 = (SPacketPlayerListItem)event.getPacket();
            if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals((Object)packet2.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals((Object)packet2.getAction())) {
                return;
            }
            final UUID id;
            final SPacketPlayerListItem sPacketPlayerListItem;
            final String name;
            final EntityPlayer entity;
            String logoutName;
            packet2.getEntries().stream().filter(Objects::nonNull).filter(data -> !Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null).forEach(data -> {
                id = data.getProfile().getId();
                switch (sPacketPlayerListItem.getAction()) {
                    case ADD_PLAYER: {
                        name = data.getProfile().getName();
                        MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(0, id, name));
                        break;
                    }
                    case REMOVE_PLAYER: {
                        entity = EventManager.mc.world.getPlayerEntityByUUID(id);
                        if (entity != null) {
                            logoutName = entity.getName();
                            MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(1, entity, id, logoutName));
                            break;
                        }
                        else {
                            MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(2, id, (String)null));
                            break;
                        }
                        break;
                    }
                }
            });
        }
        else if (event.getPacket() instanceof SPacketTimeUpdate) {
            KuroHack.serverManager.update();
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        EventManager.mc.profiler.startSection("creepyware");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        final GLUProjection projection = GLUProjection.getInstance();
        final IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projectionPort);
        GL11.glGetInteger(2978, viewPort);
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        projection.updateMatrices(viewPort, modelView, projectionPort, scaledResolution.getScaledWidth() / (double)Minecraft.getMinecraft().displayWidth, scaledResolution.getScaledHeight() / (double)Minecraft.getMinecraft().displayHeight);
        KuroHack.moduleManager.onRender3D(render3dEvent);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        EventManager.mc.profiler.endSection();
    }
    
    @SubscribeEvent
    public void renderHUD(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            KuroHack.textManager.updateResolution();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            final ScaledResolution resolution = new ScaledResolution(EventManager.mc);
            final Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
            KuroHack.moduleManager.onRender2D(render2DEvent);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(final ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                EventManager.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    KuroHack.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                }
                else {
                    Command.sendMessage("Please enter a command.");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage("§cAn error occurred while running this command. Check the log!");
            }
            event.setMessage("");
        }
    }
}
