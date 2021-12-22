//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.annotated.handler.annotation;

import java.lang.annotation.*;
import Listener.filter.*;
import Listener.handler.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Listener {
    Class<? extends EventFilter>[] filters() default {};
    
    ListenerPriority priority() default ListenerPriority.NORMAL;
}
