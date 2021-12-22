//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import kurohack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import kurohack.event.events.*;
import net.minecraft.util.math.*;
import java.awt.*;
import kurohack.util.render.*;

public class ChorusPredict extends Module
{
    private final Setting<Integer> time;
    private final Setting<Boolean> box;
    private final Setting<Boolean> outline;
    private final Setting<Integer> boxR;
    private final Setting<Integer> boxG;
    private final Setting<Integer> boxB;
    private final Setting<Integer> boxA;
    private final Setting<Float> lineWidth;
    private final Setting<Integer> outlineR;
    private final Setting<Integer> outlineG;
    private final Setting<Integer> outlineB;
    private final Setting<Integer> outlineA;
    private final PhobosTimer timer;
    private double x;
    private double y;
    private double z;
    
    public ChorusPredict() {
        super("ChorusPredict", "", Category.MISC, true, false, false);
        this.time = (Setting<Integer>)this.register(new Setting("Duration", (T)500, (T)50, (T)3000));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.boxR = (Setting<Integer>)this.register(new Setting("BoxR", (T)255, (T)0, (T)255, v -> this.box.getCurrentState()));
        this.boxG = (Setting<Integer>)this.register(new Setting("BoxG", (T)255, (T)0, (T)255, v -> this.box.getCurrentState()));
        this.boxB = (Setting<Integer>)this.register(new Setting("BoxB", (T)255, (T)0, (T)255, v -> this.box.getCurrentState()));
        this.boxA = (Setting<Integer>)this.register(new Setting("BoxA", (T)120, (T)0, (T)255, v -> this.box.getCurrentState()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getCurrentState()));
        this.outlineR = (Setting<Integer>)this.register(new Setting("OutlineR", (T)255, (T)0, (T)255, v -> this.outline.getCurrentState()));
        this.outlineG = (Setting<Integer>)this.register(new Setting("OutlineG", (T)255, (T)0, (T)255, v -> this.outline.getCurrentState()));
        this.outlineB = (Setting<Integer>)this.register(new Setting("OutlineB", (T)255, (T)0, (T)255, v -> this.outline.getCurrentState()));
        this.outlineA = (Setting<Integer>)this.register(new Setting("OutlineA", (T)255, (T)0, (T)255, v -> this.outline.getCurrentState()));
        this.timer = new PhobosTimer();
    }
    
    @SubscribeEvent
    public void onChorus(final ChorusEvent event) {
        this.x = event.getChorusX();
        this.y = event.getChorusY();
        this.z = event.getChorusZ();
        this.timer.reset();
    }
    
    @Override
    public void onRender3D(final Render3DEvent render3DEvent) {
        if (this.timer.passedMs(this.time.getCurrentState())) {
            return;
        }
        final AxisAlignedBB pos = RenderUtil.interpolateAxis(new AxisAlignedBB(this.x - 0.3, this.y, this.z - 0.3, this.x + 0.3, this.y + 1.8, this.z + 0.3));
        if (this.outline.getCurrentState()) {
            RenderUtil.drawBlockOutline(pos, new Color(this.outlineR.getCurrentState(), this.outlineG.getCurrentState(), this.outlineB.getCurrentState(), this.outlineA.getCurrentState()), this.lineWidth.getCurrentState());
        }
        if (this.box.getCurrentState()) {
            RenderUtil.drawFilledBox(pos, ColorUtil.toRGBA(this.boxR.getCurrentState(), this.boxG.getCurrentState(), this.boxB.getCurrentState(), this.boxA.getCurrentState()));
        }
    }
}
