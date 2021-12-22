//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import net.minecraft.util.math.*;
import kurohack.util.*;
import kurohack.features.modules.client.*;

public class NoStopManager extends Feature
{
    private final Timer timer;
    private String prefix;
    private boolean running;
    private boolean sentMessage;
    private BlockPos pos;
    private BlockPos lastPos;
    private boolean stopped;
    
    public NoStopManager() {
        this.timer = new Timer();
    }
    
    public void onUpdateWalkingPlayer() {
        if (fullNullCheck()) {
            this.stop();
            return;
        }
        if (this.running && this.pos != null) {
            final BlockPos currentPos = NoStopManager.mc.player.getPosition();
            if (currentPos.equals((Object)this.pos)) {
                BlockUtil.debugPos("<Baritone> Arrived at Position: ", this.pos);
                this.running = false;
                return;
            }
            if (currentPos.equals((Object)this.lastPos)) {
                if (this.stopped && this.timer.passedS((int)Managers.getInstance().baritoneTimeOut.getValue())) {
                    this.sendMessage();
                    this.stopped = false;
                    return;
                }
                if (!this.stopped) {
                    this.stopped = true;
                    this.timer.reset();
                }
            }
            else {
                this.lastPos = currentPos;
                this.stopped = false;
            }
            if (!this.sentMessage) {
                this.sendMessage();
                this.sentMessage = true;
            }
        }
    }
    
    public void sendMessage() {
        NoStopManager.mc.player.sendChatMessage(this.prefix + "goto " + this.pos.getX() + " " + this.pos.getY() + " " + this.pos.getZ());
    }
    
    public void start(final int x, final int y, final int z) {
        this.pos = new BlockPos(x, y, z);
        this.sentMessage = false;
        this.running = true;
    }
    
    public void stop() {
        if (this.running) {
            if (NoStopManager.mc.player != null) {
                NoStopManager.mc.player.sendChatMessage(this.prefix + "stop");
            }
            this.running = false;
        }
    }
    
    public void setPrefix(final String prefixIn) {
        this.prefix = prefixIn;
    }
}
