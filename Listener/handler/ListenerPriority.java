//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.handler;

public enum ListenerPriority
{
    LOWEST(-750), 
    LOWER(-500), 
    LOW(-250), 
    NORMAL(0), 
    HIGH(250), 
    HIGHER(500), 
    HIGHEST(750);
    
    private final int priorityLevel;
    
    private ListenerPriority(final int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
    public int getPriorityLevel() {
        return this.priorityLevel;
    }
}
