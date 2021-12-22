//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated.handler;

import Listener.filter.*;
import Listener.annotated.handler.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import Listener.handler.*;

public final class MethodEventHandler implements EventHandler
{
    private final Object listenerParent;
    private final Method method;
    private final Set<EventFilter> eventFilters;
    private final Listener listenerAnnotation;
    
    public MethodEventHandler(final Object listenerParent, final Method method, final Set<EventFilter> eventFilters) {
        this.listenerParent = listenerParent;
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        this.method = method;
        this.eventFilters = eventFilters;
        this.listenerAnnotation = method.getAnnotation(Listener.class);
    }
    
    @Override
    public <E> void handle(final E event) {
        for (final EventFilter filter : this.eventFilters) {
            if (!filter.test(this, event)) {
                return;
            }
        }
        try {
            this.method.invoke(this.listenerParent, event);
        }
        catch (IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException exception = ex;
            exception.printStackTrace();
        }
    }
    
    @Override
    public Object getListener() {
        return this.method;
    }
    
    @Override
    public ListenerPriority getPriority() {
        return this.listenerAnnotation.priority();
    }
    
    @Override
    public Iterable<EventFilter> getFilters() {
        return this.eventFilters;
    }
    
    @Override
    public int compareTo(final EventHandler eventHandler) {
        return Integer.compare(eventHandler.getPriority().getPriorityLevel(), this.getPriority().getPriorityLevel());
    }
}
