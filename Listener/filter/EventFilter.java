//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package Listener.filter;

import Listener.handler.*;

public interface EventFilter<E>
{
    boolean test(final EventHandler p0, final E p1);
}
