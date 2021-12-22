//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated.dispatch;

import Listener.dispatch.*;
import Listener.handler.*;
import java.util.*;

public final class MethodEventDispatcher implements EventDispatcher
{
    private final Map<Class<?>, Set<EventHandler>> eventHandlers;
    
    public MethodEventDispatcher(final Map<Class<?>, Set<EventHandler>> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }
    
    @Override
    public <E> void dispatch(final E event) {
        for (final EventHandler eventHandler : this.eventHandlers.getOrDefault(event.getClass(), Collections.emptySet())) {
            eventHandler.handle(event);
        }
    }
}
