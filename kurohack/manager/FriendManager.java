//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.features.*;
import net.minecraft.entity.player.*;
import kurohack.features.setting.*;
import java.util.*;
import kurohack.util.*;

public class FriendManager extends Feature
{
    private final Map<String, UUID> friends;
    
    public FriendManager() {
        super("Friends");
        this.friends = new HashMap<String, UUID>();
    }
    
    public boolean isFriend(final String name) {
        return this.friends.get(name) != null;
    }
    
    public boolean isFriend(final EntityPlayer player) {
        return this.isFriend(player.getName());
    }
    
    public void addFriend(final String name) {
        final Friend friend = this.getFriendByName(name);
        if (friend != null) {
            this.friends.put(friend.getUsername(), friend.getUuid());
        }
    }
    
    public void removeFriend(final String name) {
        this.friends.remove(name);
    }
    
    public void onLoad() {
        this.friends.clear();
        this.clearSettings();
    }
    
    public void saveFriends() {
        this.clearSettings();
        for (final Map.Entry<String, UUID> entry : this.friends.entrySet()) {
            this.register(new Setting("Speed", entry.getValue().toString(), 0.0, 0.0, (Object)entry.getKey(), 0));
        }
    }
    
    public Map<String, UUID> getFriends() {
        return this.friends;
    }
    
    public Friend getFriendByName(final String input) {
        final UUID uuid = PlayerUtil.getUUIDFromName(input);
        if (uuid != null) {
            return new Friend(input, uuid);
        }
        return null;
    }
    
    public void addFriend(final Friend friend) {
        this.friends.put(friend.getUsername(), friend.getUuid());
    }
    
    public static class Friend
    {
        private final String username;
        private final UUID uuid;
        
        public Friend(final String username, final UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }
        
        public String getUsername() {
            return this.username;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        @Override
        public boolean equals(final Object other) {
            return other instanceof Friend && ((Friend)other).getUsername().equals(this.username) && ((Friend)other).getUuid().equals(this.uuid);
        }
        
        @Override
        public int hashCode() {
            return this.username.hashCode() + this.uuid.hashCode();
        }
    }
}
