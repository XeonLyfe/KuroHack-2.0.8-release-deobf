//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated.handler.scan;

import Listener.handler.scan.*;
import Listener.filter.*;
import java.lang.reflect.*;
import Listener.annotated.filter.*;
import Listener.handler.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;
import Listener.annotated.handler.*;

public final class MethodHandlerScanner implements EventHandlerScanner
{
    private final AnnotatedListenerPredicate annotatedListenerPredicate;
    private final EventFilterScanner<Method> filterScanner;
    
    public MethodHandlerScanner() {
        this.annotatedListenerPredicate = new AnnotatedListenerPredicate();
        this.filterScanner = (EventFilterScanner<Method>)new MethodFilterScanner();
    }
    
    @Override
    public Map<Class<?>, Set<EventHandler>> locate(final Object listenerContainer) {
        final Map<Class<?>, Set<EventHandler>> eventHandlers = new HashMap<Class<?>, Set<EventHandler>>();
        Stream.of(listenerContainer.getClass().getDeclaredMethods()).filter((Predicate<? super Method>)this.annotatedListenerPredicate).forEach(method -> eventHandlers.computeIfAbsent(method.getParameterTypes()[0], obj -> new TreeSet()).add((EventHandler)new MethodEventHandler(listenerContainer, method, (Set)this.filterScanner.scan(method))));
        return eventHandlers;
    }
}
