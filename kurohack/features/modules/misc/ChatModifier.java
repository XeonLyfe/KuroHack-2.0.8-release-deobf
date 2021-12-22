//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.misc;

import kurohack.features.modules.*;
import kurohack.util.*;
import com.mojang.realmsclient.gui.*;
import kurohack.features.setting.*;
import kurohack.util.render.*;
import net.minecraft.entity.player.*;
import kurohack.features.command.*;
import kurohack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import java.text.*;
import java.util.*;
import kurohack.features.modules.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;

public class ChatModifier extends Module
{
    private static ChatModifier INSTANCE;
    private final Timer timer;
    public static ChatFormatting reset;
    public Setting<Suffix> suffix;
    public Setting<Boolean> clean;
    public Setting<Boolean> infinite;
    public Setting<Boolean> autoQMain;
    public Setting<Boolean> qNotification;
    public Setting<Integer> qDelay;
    public Setting<TextUtil.Color> timeStamps;
    public Setting<Boolean> rainbowTimeStamps;
    public Setting<TextUtil.Color> bracket;
    public Setting<Boolean> space;
    public Setting<Boolean> all;
    public Setting<Boolean> shrug;
    
    public ChatModifier() {
        super("ChatModifier", "Modifies your chat", Category.MISC, true, false, false);
        this.timer = new Timer();
        this.suffix = (Setting<Suffix>)this.register(new Setting("Suffix", (T)Suffix.NONE, "Your Suffix."));
        this.clean = (Setting<Boolean>)this.register(new Setting("CleanChat", (T)false, "Cleans your chat"));
        this.infinite = (Setting<Boolean>)this.register(new Setting("Infinite", (T)false, "Makes your chat infinite."));
        this.autoQMain = (Setting<Boolean>)this.register(new Setting("AutoQMain", (T)false, "Spams AutoQMain"));
        this.qNotification = (Setting<Boolean>)this.register(new Setting("QNotification", (T)false, v -> this.autoQMain.getValue()));
        this.qDelay = (Setting<Integer>)this.register(new Setting("QDelay", (T)9, (T)1, (T)90, v -> this.autoQMain.getValue()));
        this.timeStamps = (Setting<TextUtil.Color>)this.register(new Setting("Time", (T)TextUtil.Color.NONE));
        this.rainbowTimeStamps = (Setting<Boolean>)this.register(new Setting("RainbowTimeStamps", (T)false, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.bracket = (Setting<TextUtil.Color>)this.register(new Setting("Bracket", (T)TextUtil.Color.WHITE, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.space = (Setting<Boolean>)this.register(new Setting("Space", (T)true, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.all = (Setting<Boolean>)this.register(new Setting("All", (T)false, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.shrug = (Setting<Boolean>)this.register(new Setting("Shrug", (T)false));
        this.setInstance();
    }
    
    public static ChatModifier getInstance() {
        if (ChatModifier.INSTANCE == null) {
            ChatModifier.INSTANCE = new ChatModifier();
        }
        return ChatModifier.INSTANCE;
    }
    
    private void setInstance() {
        ChatModifier.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.shrug.getValue()) {
            ChatModifier.mc.player.sendChatMessage(TextUtil.shrug);
            this.shrug.setValue(false);
        }
        if (this.autoQMain.getValue()) {
            if (!this.shouldSendMessage((EntityPlayer)ChatModifier.mc.player)) {
                return;
            }
            if (this.qNotification.getValue()) {
                Command.sendMessage("<AutoQueueMain> Sending message: /queue main");
            }
            ChatModifier.mc.player.sendChatMessage("/queue main");
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
            String s = packet.getMessage();
            if (s.startsWith("/") || s.startsWith("!")) {
                return;
            }
            switch (this.suffix.getValue()) {
                case KUROHACK: {
                    s = s + " \u23d0" + this.toUnicode(" kurohack");
                    break;
                }
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            packet.message = s;
        }
    }
    
    @SubscribeEvent
    public void onChatPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() != 0 || event.getPacket() instanceof SPacketChat) {}
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() == 0 && this.timeStamps.getValue() != TextUtil.Color.NONE && event.getPacket() instanceof SPacketChat) {
            if (!((SPacketChat)event.getPacket()).isSystem()) {
                return;
            }
            final String originalMessage = ((SPacketChat)event.getPacket()).chatComponent.getFormattedText();
            final String message = this.getTimeString(originalMessage) + originalMessage;
            ((SPacketChat)event.getPacket()).chatComponent = (ITextComponent)new TextComponentString(message);
        }
    }
    
    public String getTimeString(final String message) {
        final String date = new SimpleDateFormat("k:mm").format(new Date());
        if (this.rainbowTimeStamps.getValue()) {
            final String timeString = "<" + date + ">" + (this.space.getValue() ? " " : "");
            final StringBuilder builder = new StringBuilder(timeString);
            builder.insert(0, "§+");
            if (!message.contains(Managers.getInstance().getRainbowCommandMessage())) {
                builder.append("§r");
            }
            return builder.toString();
        }
        return ((this.bracket.getValue() == TextUtil.Color.NONE) ? "" : TextUtil.coloredString("<", this.bracket.getValue())) + TextUtil.coloredString(date, this.timeStamps.getValue()) + ((this.bracket.getValue() == TextUtil.Color.NONE) ? "" : TextUtil.coloredString(">", this.bracket.getValue())) + (this.space.getValue() ? " " : "") + "§r";
    }
    
    private boolean shouldSendMessage(final EntityPlayer player) {
        return player.dimension == 1 && this.timer.passedS(this.qDelay.getValue()) && player.getPosition().equals((Object)new Vec3i(0, 240, 0));
    }
    
    public String toUnicode(final String s) {
        return s.toLowerCase().replace("a", "\u1d00").replace("b", "\u0299").replace("c", "\u1d04").replace("d", "\u1d05").replace("e", "\u1d07").replace("f", "\ua730").replace("g", "\u0262").replace("h", "\u029c").replace("i", "\u026a").replace("j", "\u1d0a").replace("k", "\u1d0b").replace("l", "\u029f").replace("m", "\u1d0d").replace("n", "\u0274").replace("o", "\u1d0f").replace("p", "\u1d18").replace("q", "\u01eb").replace("r", "\u0280").replace("s", "\ua731").replace("t", "\u1d1b").replace("u", "\u1d1c").replace("v", "\u1d20").replace("w", "\u1d21").replace("x", "\u02e3").replace("y", "\u028f").replace("z", "\u1d22");
    }
    
    static {
        ChatModifier.INSTANCE = new ChatModifier();
        ChatModifier.reset = ChatFormatting.RESET;
    }
    
    public enum Suffix
    {
        NONE, 
        KUROHACK;
    }
}
