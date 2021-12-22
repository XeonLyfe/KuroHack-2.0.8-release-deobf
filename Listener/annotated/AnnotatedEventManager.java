//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated;

import Listener.*;
import Listener.handler.scan.*;
import Listener.dispatch.*;
import Listener.annotated.handler.scan.*;
import java.util.concurrent.*;
import Listener.annotated.dispatch.*;
import java.util.*;
import Listener.handler.*;

public final class AnnotatedEventManager implements EventManager
{
    private final EventHandlerScanner eventHandlerScanner;
    private final Map<Object, EventDispatcher> listenerDispatchers;
    
    public AnnotatedEventManager() {
        this.eventHandlerScanner = new MethodHandlerScanner();
        this.listenerDispatchers = new ConcurrentHashMap<Object, EventDispatcher>();
    }
    
    @Override
    public <E> E dispatchEvent(final E event) {
        for (final EventDispatcher dispatcher : this.listenerDispatchers.values()) {
            dispatcher.dispatch(event);
        }
        return event;
    }
    
    @Override
    public boolean isRegisteredListener(final Object listener) {
        return this.listenerDispatchers.containsKey(listener);
    }
    
    @Override
    public boolean addEventListener(final Object listenerContainer) {
        if (this.listenerDispatchers.containsKey(listenerContainer)) {
            return false;
        }
        final Map<Class<?>, Set<EventHandler>> eventHandlers = this.eventHandlerScanner.locate(listenerContainer);
        return !eventHandlers.isEmpty() && this.listenerDispatchers.put(listenerContainer, new MethodEventDispatcher(eventHandlers)) == null;
    }
    
    @Override
    public boolean removeEventListener(final Object listenerContainer) {
        return this.listenerDispatchers.remove(listenerContainer) != null;
    }
}
