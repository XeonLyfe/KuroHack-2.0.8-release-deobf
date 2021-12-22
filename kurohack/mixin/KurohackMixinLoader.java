//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin;

import net.minecraftforge.fml.relauncher.*;
import kurohack.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;

public class KurohackMixinLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public KurohackMixinLoader() {
        KuroHack.LOGGER.info("kurohack mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.kurohack.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        KuroHack.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        KurohackMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        KurohackMixinLoader.isObfuscatedEnvironment = false;
    }
}
