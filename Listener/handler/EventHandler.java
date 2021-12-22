//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.handler;

import Listener.filter.*;

public interface EventHandler extends Comparable<EventHandler>
{
     <E> void handle(final E p0);
    
    Object getListener();
    
    ListenerPriority getPriority();
    
    Iterable<EventFilter> getFilters();
}
