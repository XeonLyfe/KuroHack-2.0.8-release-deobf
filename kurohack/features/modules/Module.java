//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules;

import kurohack.features.*;
import kurohack.features.setting.*;
import net.minecraftforge.common.*;
import kurohack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.*;
import kurohack.features.command.*;
import kurohack.features.modules.HUD.*;
import kurohack.util.element.*;
import java.util.concurrent.*;

public class Module extends Feature
{
    private final String description;
    private final Category category;
    public Setting<Boolean> enabled;
    public Setting<Boolean> drawn;
    public Setting<Bind> bind;
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;
    public float arrayListOffset;
    public float arrayListVOffset;
    public float offset;
    public float vOffset;
    public boolean sliding;
    public Animation animation;
    
    public Module(final String name, final String description, final Category category, final boolean hasListener, final boolean hidden, final boolean alwaysListening) {
        super(name);
        this.enabled = (Setting<Boolean>)this.register(new Setting("Enabled", (T)false));
        this.drawn = (Setting<Boolean>)this.register(new Setting("Drawn", (T)true));
        this.bind = (Setting<Bind>)this.register(new Setting("Bind", (T)new Bind(-1)));
        this.arrayListOffset = 0.0f;
        this.arrayListVOffset = 0.0f;
        this.displayName = (Setting<String>)this.register(new Setting("DisplayName", (T)name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
        this.animation = new Animation(this);
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onToggle() {
    }
    
    public void onLoad() {
    }
    
    public void onTick() {
    }
    
    public void onLogin() {
    }
    
    public void onLogout() {
    }
    
    public void onUpdate() {
    }
    
    public void onRender2D(final Render2DEvent event) {
    }
    
    public void onRender3D(final Render3DEvent event) {
    }
    
    public void onUnload() {
    }
    
    public String getDisplayInfo() {
        return null;
    }
    
    public boolean isOn() {
        return this.enabled.getValue();
    }
    
    public boolean isOff() {
        return !this.enabled.getValue();
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            this.enable();
        }
        else {
            this.disable();
        }
    }
    
    public void enable() {
        this.enabled.setValue(true);
        this.onToggle();
        this.onEnable();
        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
        this.enabled.setValue(false);
        this.onToggle();
        this.onDisable();
    }
    
    public final boolean isNull() {
        return Module.mc.player == null || Module.mc.world == null;
    }
    
    public void toggle() {
        final ClientEvent event = new ClientEvent((int)(this.isEnabled() ? 0 : 1), (Feature)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.setEnabled(!this.isEnabled());
        }
    }
    
    public String getDisplayName() {
        return this.displayName.getValue();
    }
    
    public void setDisplayName(final String name) {
        final Module module = KuroHack.moduleManager.getModuleByDisplayName(name);
        final Module originalModule = KuroHack.moduleManager.getModuleByName(name);
        if (module == null && originalModule == null) {
            Command.sendMessage(this.getDisplayName() + ", Original name: " + this.getName() + ", has been renamed to: " + name);
            this.displayName.setValue(name);
            return;
        }
        Command.sendMessage("�cA module of this name already exists.");
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean isSliding() {
        return this.sliding;
    }
    
    public boolean isDrawn() {
        return this.drawn.getValue();
    }
    
    public void setDrawn(final boolean drawn) {
        this.drawn.setValue(drawn);
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public String getInfo() {
        return null;
    }
    
    public Bind getBind() {
        return this.bind.getValue();
    }
    
    public void setBind(final int key) {
        this.bind.setValue(new Bind(key));
    }
    
    public boolean listening() {
        return (this.hasListener && this.isOn()) || this.alwaysListening;
    }
    
    public String getFullArrayString() {
        return this.getDisplayName() + "�8" + ((this.getDisplayInfo() != null) ? (" [�r" + this.getDisplayInfo() + "�8]") : "");
    }
    
    public enum Category
    {
        COMBAT("Combat"), 
        RENDER("Render"), 
        MISC("Misc"), 
        PLAYER("Player"), 
        MOVEMENT("Movement"), 
        CLIENT("Client"), 
        HUD("HUD");
        
        private final String name;
        
        private Category(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public class Animation extends Thread
    {
        public Module module;
        public float offset;
        public float vOffset;
        public String lastText;
        public boolean shouldMetaSlide;
        ScheduledExecutorService service;
        
        public Animation(final Module module) {
            super("Animation");
            this.service = Executors.newSingleThreadScheduledExecutor();
            this.module = module;
        }
        
        @Override
        public void run() {
            final String text = this.module.getDisplayName() + "�7" + ((this.module.getDisplayInfo() != null) ? (" [�f" + this.module.getDisplayInfo() + "�7]") : "");
            this.module.offset = Module.this.renderer.getStringWidth(text) / (float)HUD.getInstance().animationHorizontalTime.getValue();
            this.module.vOffset = Module.this.renderer.getFontHeight() / (float)HUD.getInstance().animationVerticalTime.getValue();
            if (this.module.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                if (this.module.arrayListOffset > this.module.offset && IUtil.mc.world != null) {
                    final Module module = this.module;
                    module.arrayListOffset -= this.module.offset;
                    this.module.sliding = true;
                }
            }
            else if (this.module.isDisabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                if (this.module.arrayListOffset < Module.this.renderer.getStringWidth(text) && IUtil.mc.world != null) {
                    final Module module2 = this.module;
                    module2.arrayListOffset += this.module.offset;
                    this.module.sliding = true;
                }
                else {
                    this.module.sliding = false;
                }
            }
        }
        
        @Override
        public void start() {
            System.out.println("Starting animation thread for " + this.module.getName());
            this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
        }
    }
}
