//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import kurohack.features.modules.*;
import java.awt.*;
import kurohack.features.modules.HUD.*;
import kurohack.features.modules.client.*;
import kurohack.features.modules.combat.*;
import kurohack.features.modules.movement.*;
import kurohack.features.modules.player.*;
import kurohack.features.modules.misc.*;
import kurohack.features.modules.render.*;
import net.minecraftforge.common.*;
import kurohack.event.events.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import org.lwjgl.input.*;
import kurohack.features.gui.*;

public class ModuleManager extends Feature
{
    public ArrayList<Module> modules;
    public List<Module> sortedModules;
    public List<Module> alphabeticallySortedModules;
    public Map<Module, Color> moduleColorMap;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.sortedModules = new ArrayList<Module>();
        this.alphabeticallySortedModules = new ArrayList<Module>();
        this.moduleColorMap = new HashMap<Module, Color>();
    }
    
    public void init() {
        this.modules.add((Module)new WaterMarkNew());
        this.modules.add((Module)new HUD());
        this.modules.add((Module)new ModuleTools());
        this.modules.add((Module)new TargetHud());
        this.modules.add((Module)new Logo());
        this.modules.add((Module)new Inventory());
        this.modules.add((Module)new FontMod());
        this.modules.add((Module)new ClickGui());
        this.modules.add((Module)new Managers());
        this.modules.add((Module)new Colors());
        this.modules.add((Module)new ServerModule());
        this.modules.add((Module)new Notifications());
        this.modules.add((Module)new Media());
        this.modules.add((Module)new MainMenu());
        this.modules.add((Module)new Particles());
        this.modules.add((Module)new Flatten());
        this.modules.add((Module)new CevBreaker());
        this.modules.add((Module)new CityBoss());
        this.modules.add((Module)new PistonAura());
        this.modules.add((Module)new AntiCity());
        this.modules.add((Module)new AutoArmor());
        this.modules.add((Module)new Burrow());
        this.modules.add((Module)new BowSpam());
        this.modules.add((Module)new Offhand());
        this.modules.add((Module)new AutoTotem());
        this.modules.add((Module)new Surround());
        this.modules.add((Module)new AutoTrap());
        this.modules.add((Module)new AutoCrystal());
        this.modules.add((Module)new Criticals());
        this.modules.add((Module)new Killaura());
        this.modules.add((Module)new HoleFiller());
        this.modules.add((Module)new Selftrap());
        this.modules.add((Module)new BedAura());
        this.modules.add((Module)new Webaura());
        this.modules.add((Module)new GodModule());
        this.modules.add((Module)new AntiTrap());
        this.modules.add((Module)new Quiver());
        this.modules.add((Module)new PingSpoofer());
        this.modules.add((Module)new GreenText());
        this.modules.add((Module)new Gamemode());
        this.modules.add((Module)new ChorusPredict());
        this.modules.add((Module)new ThirdPerson());
        this.modules.add((Module)new Sex());
        this.modules.add((Module)new ExtraTab());
        this.modules.add((Module)new FriendSettings());
        this.modules.add((Module)new ChatModifier());
        this.modules.add((Module)new BuildHeight());
        this.modules.add((Module)new AutoRespawn());
        this.modules.add((Module)new ToolTips());
        this.modules.add((Module)new NoRotate());
        this.modules.add((Module)new AutoLog());
        this.modules.add((Module)new AutoReconnect());
        this.modules.add((Module)new Tracker());
        this.modules.add((Module)new Blink());
        this.modules.add((Module)new RPC());
        this.modules.add((Module)new AutoGG());
        this.modules.add((Module)new NoHandShake());
        this.modules.add((Module)new Suicide());
        this.modules.add((Module)new Anchor());
        this.modules.add((Module)new AirJump());
        this.modules.add((Module)new AutoWalk());
        this.modules.add((Module)new HoleTP());
        this.modules.add((Module)new IceSpeed());
        this.modules.add((Module)new WebTP());
        this.modules.add((Module)new AntiWeb());
        this.modules.add((Module)new YPort());
        this.modules.add((Module)new LongJump());
        this.modules.add((Module)new ReverseStep());
        this.modules.add((Module)new Strafe());
        this.modules.add((Module)new ElytraFlight());
        this.modules.add((Module)new NoSlowDown());
        this.modules.add((Module)new Speed());
        this.modules.add((Module)new Step());
        this.modules.add((Module)new Velocity());
        this.modules.add((Module)new AntiVoid());
        this.modules.add((Module)new Sprint());
        this.modules.add((Module)new VanillaSpeed());
        this.modules.add((Module)new Scaffold());
        this.modules.add((Module)new Phase());
        this.modules.add((Module)new PacketEat());
        this.modules.add((Module)new NoGlitchBlocks());
        this.modules.add((Module)new MiddleClick());
        this.modules.add((Module)new NoEntityTrace());
        this.modules.add((Module)new EntityNotifier());
        this.modules.add((Module)new FakePlayer());
        this.modules.add((Module)new StashLogger());
        this.modules.add((Module)new LiquidInteract());
        this.modules.add((Module)new TimerSpeed());
        this.modules.add((Module)new FastPlace());
        this.modules.add((Module)new Freecam());
        this.modules.add((Module)new Speedmine());
        this.modules.add((Module)new SpeedMineRewrite());
        this.modules.add((Module)new MultiTask());
        this.modules.add((Module)new XCarry());
        this.modules.add((Module)new Replenish());
        this.modules.add((Module)new SilentXP());
        this.modules.add((Module)new ShadowESP());
        this.modules.add((Module)new HellenKeller());
        this.modules.add((Module)new NoSmoothCamera());
        this.modules.add((Module)new PearlRender());
        this.modules.add((Module)new SelfParticles());
        this.modules.add((Module)new HitMarkers());
        this.modules.add((Module)new Zoom());
        this.modules.add((Module)new Crosshair());
        this.modules.add((Module)new Shaders());
        this.modules.add((Module)new Animations());
        this.modules.add((Module)new Nametags());
        this.modules.add((Module)new BreakingESP());
        this.modules.add((Module)new BurrowESP());
        this.modules.add((Module)new HandChams());
        this.modules.add((Module)new Aspect());
        this.modules.add((Module)new ItemPhysics());
        this.modules.add((Module)new ViewModel());
        this.modules.add((Module)new StorageESP());
        this.modules.add((Module)new CameraClip());
        this.modules.add((Module)new Chams());
        this.modules.add((Module)new PopChams());
        this.modules.add((Module)new ESP());
        this.modules.add((Module)new HoleESP());
        this.modules.add((Module)new BlockHighlight());
        this.modules.add((Module)new Trajectories());
        this.modules.add((Module)new LogoutSpots());
        this.modules.add((Module)new XRay());
        this.modules.add((Module)new VoidESP());
        this.modules.add((Module)new CrystalScale());
        this.modules.add((Module)new NoRender());
        this.modules.add((Module)new SmallShield());
        this.modules.add((Module)new Fullbright());
        this.modules.add((Module)new PenisESP());
        this.modules.add((Module)new FogColor());
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Flatten.class), new Color(99, 255, 241));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Phase.class), new Color(99, 255, 241));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)WebTP.class), new Color(99, 255, 241));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)IceSpeed.class), new Color(248, 126, 126));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)HandChams.class), new Color(118, 215, 181));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)PistonAura.class), new Color(255, 71, 71));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)CityBoss.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)CevBreaker.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Zoom.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)PacketEat.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Crosshair.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Scaffold.class), new Color(255, 147, 147, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)YPort.class), new Color(255, 255, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AntiWeb.class), new Color(255, 255, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)NoGlitchBlocks.class), new Color(51, 192, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)MiddleClick.class), new Color(51, 192, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)FogColor.class), new Color(159, 225, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoArmor.class), new Color(79, 203, 255, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)LongJump.class), new Color(128, 255, 225, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Animations.class), new Color(255, 108, 108));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)BurrowESP.class), new Color(139, 255, 130));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AntiTrap.class), new Color(255, 130, 157));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoCrystal.class), new Color(255, 116, 133));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoTrap.class), new Color(221, 122, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Criticals.class), new Color(255, 117, 202));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)HoleFiller.class), new Color(255, 118, 186));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Killaura.class), new Color(255, 137, 118));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Offhand.class), new Color(201, 255, 119));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Selftrap.class), new Color(123, 236, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Surround.class), new Color(209, 123, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Webaura.class), new Color(102, 241, 202));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoGG.class), new Color(255, 118, 162));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoLog.class), new Color(255, 171, 171));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AutoReconnect.class), new Color(133, 194, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)BuildHeight.class), new Color(126, 194, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ChatModifier.class), new Color(255, 117, 229));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)NoRotate.class), new Color(74, 85, 227));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)RPC.class), new Color(55, 106, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ToolTips.class), new Color(255, 78, 142));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Tracker.class), new Color(79, 253, 230));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)BlockHighlight.class), new Color(96, 188, 238));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)CameraClip.class), new Color(255, 162, 92));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Chams.class), new Color(89, 238, 89));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ESP.class), new Color(236, 85, 170));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Fullbright.class), new Color(225, 153, 110));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)HoleESP.class), new Color(135, 112, 199));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)LogoutSpots.class), new Color(111, 229, 228));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Nametags.class), new Color(120, 107, 222));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)NoRender.class), new Color(222, 149, 103));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)SmallShield.class), new Color(145, 223, 187));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)StorageESP.class), new Color(97, 81, 223));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Trajectories.class), new Color(98, 18, 223));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)VoidESP.class), new Color(68, 178, 142));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)XRay.class), new Color(217, 118, 37));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ElytraFlight.class), new Color(55, 161, 201));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)NoSlowDown.class), new Color(89, 217, 107));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Speed.class), new Color(55, 161, 196));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)AntiVoid.class), new Color(201, 117, 232));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Step.class), new Color(144, 212, 203));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Strafe.class), new Color(0, 204, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Velocity.class), new Color(115, 134, 140));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ReverseStep.class), new Color(1, 134, 140));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)FakePlayer.class), new Color(37, 192, 170));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)FastPlace.class), new Color(217, 118, 37));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Freecam.class), new Color(206, 232, 128));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)LiquidInteract.class), new Color(85, 223, 235));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)MultiTask.class), new Color(17, 223, 235));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Replenish.class), new Color(153, 223, 235));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Speedmine.class), new Color(195, 227, 111));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)TimerSpeed.class), new Color(227, 167, 110));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)XCarry.class), new Color(222, 166, 98));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ClickGui.class), new Color(94, 156, 218));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Colors.class), new Color(232, 229, 112));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)FontMod.class), new Color(236, 103, 179));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)HUD.class), new Color(110, 26, 135));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Managers.class), new Color(26, 90, 135));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Notifications.class), new Color(170, 153, 255));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)ServerModule.class), new Color(60, 110, 175));
        this.moduleColorMap.put(this.getModuleByClass((Class<Module>)Media.class), new Color(231, 139, 107));
        for (final Module module : this.modules) {
            module.animation.start();
        }
    }
    
    public Module getModuleByName(final String name) {
        for (final Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public <T extends Module> T getModuleByClass(final Class<T> clazz) {
        for (final Module module : this.modules) {
            if (!clazz.isInstance(module)) {
                continue;
            }
            return (T)module;
        }
        return null;
    }
    
    public void enableModule(final Class clazz) {
        final Object module = this.getModuleByClass((Class<Object>)clazz);
        if (module != null) {
            ((Module)module).enable();
        }
    }
    
    public void disableModule(final Class clazz) {
        final Object module = this.getModuleByClass((Class<Object>)clazz);
        if (module != null) {
            ((Module)module).disable();
        }
    }
    
    public void enableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }
    
    public boolean isModuleEnabled(final String name) {
        final Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }
    
    public boolean isModuleEnabled(final Class clazz) {
        final Object module = this.getModuleByClass((Class<Object>)clazz);
        return module != null && ((Module)module).isOn();
    }
    
    public Module getModuleByDisplayName(final String displayName) {
        for (final Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public ArrayList<Module> getEnabledModules() {
        final ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (final Module module : this.modules) {
            if (!module.isEnabled() && !module.isSliding()) {
                continue;
            }
            enabledModules.add(module);
        }
        return enabledModules;
    }
    
    public ArrayList<Module> getModulesByCategory(final Module.Category category) {
        final ArrayList<Module> modulesCategory = new ArrayList<Module>();
        final ArrayList<Module> list;
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                list.add(module);
            }
            return;
        });
        return modulesCategory;
    }
    
    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }
    
    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }
    
    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }
    
    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }
    
    public void onRender2D(final Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }
    
    public void onRender3D(final Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }
    
    public void sortModules(final boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public void alphabeticallySortModules() {
        this.alphabeticallySortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)Module::getDisplayName)).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public void onLogout() {
        this.modules.forEach(Module::onLogout);
    }
    
    public void onLogin() {
        this.modules.forEach(Module::onLogin);
    }
    
    public void onUnload() {
        this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }
    
    public void onUnloadPost() {
        for (final Module module : this.modules) {
            module.enabled.setValue((Object)false);
        }
    }
    
    public void onKeyPressed(final int eventKey) {
        if (eventKey == 0 || !Keyboard.getEventKeyState() || ModuleManager.mc.currentScreen instanceof KuroHackGui) {
            return;
        }
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }
    
    public List<Module> getAnimationModules(final Module.Category category) {
        final ArrayList<Module> animationModules = new ArrayList<Module>();
        for (final Module module : this.getEnabledModules()) {
            if (module.getCategory() == category && !module.isDisabled() && module.isSliding()) {
                if (!module.isDrawn()) {
                    continue;
                }
                animationModules.add(module);
            }
        }
        return animationModules;
    }
}
