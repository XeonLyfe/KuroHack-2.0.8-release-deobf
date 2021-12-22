//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import net.minecraft.client.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.lwjgl.input.*;
import kurohack.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import kurohack.features.modules.client.*;
import kurohack.features.gui.custom.*;
import net.minecraft.crash.*;
import kurohack.features.modules.render.*;
import net.minecraft.client.entity.*;
import kurohack.features.modules.player.*;
import net.minecraft.client.multiplayer.*;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft
{
    @Shadow
    public abstract void displayGuiScreen(@Nullable final GuiScreen p0);
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", ordinal = 0) }, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRunTickKeyboard(final CallbackInfo ci, final int i) {
        if (Keyboard.getEventKeyState() && KuroHack.moduleManager != null) {
            KuroHack.moduleManager.onKeyPressed(i);
        }
    }
    
    @Inject(method = { "getLimitFramerate" }, at = { @At("HEAD") }, cancellable = true)
    public void getLimitFramerateHook(final CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if ((boolean)Managers.getInstance().unfocusedCpu.getValue() && !Display.isActive()) {
                callbackInfoReturnable.setReturnValue(Managers.getInstance().cpuFPS.getValue());
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @Redirect(method = { "runGameLoop" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;sync(I)V", remap = false))
    public void syncHook(final int maxFps) {
        if (Managers.getInstance().betterFrames.getValue()) {
            Display.sync((int)Managers.getInstance().betterFPS.getValue());
        }
        else {
            Display.sync(maxFps);
        }
    }
    
    @Inject(method = { "runTick()V" }, at = { @At("RETURN") })
    private void runTick(final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && (boolean)MainMenu.getInstance().mainScreen.getValue()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiCustomMainScreen());
        }
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    private void displayGuiScreen(final GuiScreen screen, final CallbackInfo ci) {
        if (screen instanceof GuiMainMenu) {
            this.displayGuiScreen((GuiScreen)new GuiCustomMainScreen());
        }
    }
    
    @Redirect(method = { "run" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReportHook(final Minecraft minecraft, final CrashReport crashReport) {
        this.unload();
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;doVoidFogParticles(III)V"))
    public void doVoidFogParticlesHook(final WorldClient world, final int x, final int y, final int z) {
        NoRender.getInstance().doVoidFogParticles(x, y, z);
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    public void shutdownHook(final CallbackInfo info) {
        this.unload();
    }
    
    private void unload() {
        System.out.println("Shutting down: saving configuration");
        KuroHack.onUnload();
        System.out.println("Configuration saved.");
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(final EntityPlayerSP playerSP) {
        return !MultiTask.getInstance().isOn() && playerSP.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
    private boolean isHittingBlockHook(final PlayerControllerMP playerControllerMP) {
        return !MultiTask.getInstance().isOn() && playerControllerMP.getIsHittingBlock();
    }
}
