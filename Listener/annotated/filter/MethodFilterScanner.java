//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated.filter;

import java.lang.reflect.*;
import Listener.filter.*;
import Listener.annotated.handler.annotation.*;
import java.lang.annotation.*;
import java.util.*;

public final class MethodFilterScanner implements EventFilterScanner<Method>
{
    @Override
    public Set<EventFilter> scan(final Method listener) {
        if (!listener.isAnnotationPresent(Listener.class)) {
            return (Set<EventFilter>)Collections.emptySet();
        }
        final Set<EventFilter> filters = new HashSet<EventFilter>();
        for (final Class<? extends EventFilter> filter : listener.getDeclaredAnnotation(Listener.class).filters()) {
            try {
                filters.add((EventFilter)filter.newInstance());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return filters;
    }
}
