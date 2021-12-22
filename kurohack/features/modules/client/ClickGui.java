//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.client;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.client.settings.*;
import kurohack.event.events.*;
import kurohack.*;
import kurohack.features.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.features.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class ClickGui extends Module
{
    private static ClickGui INSTANCE;
    public Setting<Boolean> colorSync;
    public Setting<Boolean> outline;
    public Setting<Boolean> rainbowRolling;
    public Setting<String> prefix;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> hoverAlpha;
    public Setting<Integer> alpha;
    public Setting<Boolean> blurEffect;
    public Setting<Boolean> dark;
    public Setting<Boolean> snowing;
    public Setting<Boolean> gradiant;
    public Setting<Integer> backgroundAlpha;
    public Setting<Boolean> gear;
    public Setting<Boolean> customFov;
    public Setting<Float> fov;
    public Setting<Boolean> openCloseChange;
    public Setting<String> open;
    public Setting<String> close;
    public Setting<String> moduleButton;
    public Setting<Boolean> devSettings;
    public Setting<Integer> topRed;
    public Setting<Integer> topGreen;
    public Setting<Integer> topBlue;
    public Setting<Integer> topAlpha;
    public Setting<Boolean> frameSettings;
    public Setting<Integer> frameRed;
    public Setting<Integer> frameGreen;
    public Setting<Integer> frameBlue;
    public Setting<Integer> frameAlpha;
    public Setting<Integer> newred;
    public Setting<Integer> newgreen;
    public Setting<Integer> newblue;
    
    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Category.CLIENT, true, false, false);
        this.colorSync = (Setting<Boolean>)this.register(new Setting("Sync", (T)false));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.rainbowRolling = (Setting<Boolean>)this.register(new Setting("RollingRainbow", (T)false, v -> this.colorSync.getValue() && Colors.INSTANCE.rainbow.getValue()));
        this.prefix = (Setting<String>)this.register((Setting)new Setting<String>("Prefix", ".").setRenderName(true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.hoverAlpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)180, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("HoverAlpha", (T)200, (T)0, (T)255));
        this.blurEffect = (Setting<Boolean>)this.register(new Setting("Blur", (T)false));
        this.dark = (Setting<Boolean>)this.register(new Setting("Darken", (T)false));
        this.snowing = (Setting<Boolean>)this.register(new Setting("Snowing", (T)false));
        this.gradiant = (Setting<Boolean>)this.register(new Setting("gradiant", (T)true));
        this.backgroundAlpha = (Setting<Integer>)this.register(new Setting("BackgroundAlpha", (T)145, (T)0, (T)255));
        this.gear = (Setting<Boolean>)this.register(new Setting("Gear", (T)false, "draws gear like future"));
        this.customFov = (Setting<Boolean>)this.register(new Setting("CustomFov", (T)false));
        this.fov = (Setting<Float>)this.register(new Setting("Fov", (T)150.0f, (T)(-180.0f), (T)180.0f, v -> this.customFov.getValue()));
        this.openCloseChange = (Setting<Boolean>)this.register(new Setting("Open/Close", (T)false));
        this.open = (Setting<String>)this.register((Setting)new Setting<String>("Open:", "", v -> this.openCloseChange.getValue()).setRenderName(true));
        this.close = (Setting<String>)this.register((Setting)new Setting<String>("Close:", "", v -> this.openCloseChange.getValue()).setRenderName(true));
        this.moduleButton = (Setting<String>)this.register((Setting)new Setting<String>("Buttons:", "", v -> !this.openCloseChange.getValue()).setRenderName(true));
        this.devSettings = (Setting<Boolean>)this.register(new Setting("TopSetting", (T)true));
        this.topRed = (Setting<Integer>)this.register(new Setting("TopRed", (T)0, (T)0, (T)255, v -> this.devSettings.getValue()));
        this.topGreen = (Setting<Integer>)this.register(new Setting("TopGreen", (T)255, (T)0, (T)255, v -> this.devSettings.getValue()));
        this.topBlue = (Setting<Integer>)this.register(new Setting("TopBlue", (T)255, (T)0, (T)255, v -> this.devSettings.getValue()));
        this.topAlpha = (Setting<Integer>)this.register(new Setting("TopAlpha", (T)180, (T)0, (T)255, v -> this.devSettings.getValue()));
        this.frameSettings = (Setting<Boolean>)this.register(new Setting("FrameSetting", (T)true));
        this.frameRed = (Setting<Integer>)this.register(new Setting("FrameRed", (T)255, (T)0, (T)255, v -> this.frameSettings.getValue()));
        this.frameGreen = (Setting<Integer>)this.register(new Setting("FrameGreen", (T)255, (T)0, (T)255, v -> this.frameSettings.getValue()));
        this.frameBlue = (Setting<Integer>)this.register(new Setting("FrameBlue", (T)255, (T)0, (T)255, v -> this.frameSettings.getValue()));
        this.frameAlpha = (Setting<Integer>)this.register(new Setting("FrameAlpha", (T)255, (T)0, (T)255, v -> this.frameSettings.getValue()));
        this.newred = (Setting<Integer>)this.register(new Setting("SideRed", (T)255, (T)0, (T)255));
        this.newgreen = (Setting<Integer>)this.register(new Setting("SideGreen", (T)255, (T)0, (T)255));
        this.newblue = (Setting<Integer>)this.register(new Setting("SideBlue", (T)255, (T)0, (T)255));
        this.setInstance();
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    private void setInstance() {
        ClickGui.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.customFov.getValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.fov.getValue());
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                KuroHack.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to §a" + KuroHack.commandManager.getPrefix());
            }
            KuroHack.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }
    
    @Override
    public void onEnable() {
        ClickGui.mc.displayGuiScreen((GuiScreen)KuroHackGui.getClickGui());
        if (this.blurEffect.getValue()) {
            ClickGui.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    @Override
    public void onLoad() {
        if (this.colorSync.getValue()) {
            KuroHack.colorManager.setColor(Colors.INSTANCE.getCurrentColor().getRed(), Colors.INSTANCE.getCurrentColor().getGreen(), Colors.INSTANCE.getCurrentColor().getBlue(), this.hoverAlpha.getValue());
        }
        else {
            KuroHack.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        }
        KuroHack.commandManager.setPrefix(this.prefix.getValue());
    }
    
    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof KuroHackGui)) {
            this.disable();
            if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
                ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (ClickGui.mc.currentScreen instanceof KuroHackGui) {
            ClickGui.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
}
