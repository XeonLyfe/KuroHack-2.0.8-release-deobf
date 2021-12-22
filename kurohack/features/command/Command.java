//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.command;

import kurohack.features.*;
import kurohack.*;
import net.minecraft.util.text.*;
import java.util.regex.*;

public abstract class Command extends Feature
{
    protected String name;
    protected String[] commands;
    
    public Command(final String name) {
        super(name);
        this.name = name;
        this.commands = new String[] { "" };
    }
    
    public Command(final String name, final String[] commands) {
        super(name);
        this.name = name;
        this.commands = commands;
    }
    
    public static void sendMessage(final String message, final boolean notification) {
        sendSilentMessage(KuroHack.commandManager.getClientMessage() + " §r" + message);
        if (notification) {
            KuroHack.notificationManager.addNotification(message, 3000L);
        }
    }
    
    public static void sendMessage(final String message) {
        sendSilentMessage(KuroHack.commandManager.getClientMessage() + " §r" + message);
    }
    
    public static void sendSilentMessage(final String message) {
        if (nullCheck()) {
            return;
        }
        Command.mc.player.sendMessage((ITextComponent)new ChatMessage(message));
    }
    
    public static void sendOverwriteMessage(final String message, final int id, final boolean notification) {
        final TextComponentString component = new TextComponentString(message);
        Command.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, id);
        if (notification) {
            KuroHack.notificationManager.addNotification(message, 3000L);
        }
    }
    
    public static void sendRainbowMessage(final String message) {
        final StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.insert(0, "§+");
        Command.mc.player.sendMessage((ITextComponent)new ChatMessage(stringBuilder.toString()));
    }
    
    public static String getCommandPrefix() {
        return KuroHack.commandManager.getPrefix();
    }
    
    public abstract void execute(final String[] p0);
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public String[] getCommands() {
        return this.commands;
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        private final String text;
        
        public ChatMessage(final String text) {
            final Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher matcher = pattern.matcher(text);
            final StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                final String replacement = "§" + matcher.group().substring(1);
                matcher.appendReplacement(stringBuffer, replacement);
            }
            matcher.appendTail(stringBuffer);
            this.text = stringBuffer.toString();
        }
        
        public String getUnformattedComponentText() {
            return this.text;
        }
        
        public ITextComponent createCopy() {
            return (ITextComponent)new ChatMessage(this.text);
        }
    }
}
