//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.player;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import kurohack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mojang.realmsclient.gui.*;
import kurohack.features.command.*;

public class StashLogger extends Module
{
    private final Setting<Boolean> chests;
    private final Setting<Integer> chestsValue;
    private final Setting<Boolean> Shulkers;
    private final Setting<Integer> shulkersValue;
    private final Setting<Boolean> writeToFile;
    File mainFolder;
    final Iterator<NBTTagCompound> iterator;
    
    public StashLogger() {
        super("StashLogger", "Logs stashes", Module.Category.MISC, true, false, false);
        this.chests = (Setting<Boolean>)this.register(new Setting("Speed", "Chests", 0.0, 0.0, (T)true, 0));
        this.chestsValue = (Setting<Integer>)this.register(new Setting("ChestsValue", (T)4, (T)1, (T)30, v -> this.chests.getValue()));
        this.Shulkers = (Setting<Boolean>)this.register(new Setting("Speed", "Shulkers", 0.0, 0.0, (T)true, 0));
        this.shulkersValue = (Setting<Integer>)this.register(new Setting("ShulkersValue", (T)4, (T)1, (T)30, v -> this.Shulkers.getValue()));
        this.writeToFile = (Setting<Boolean>)this.register(new Setting("Speed", "CoordsSaver", 0.0, 0.0, (T)true, 0));
        this.mainFolder = new File(Minecraft.getMinecraft().gameDir + File.separator + "Kurohack");
        this.iterator = null;
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        if (nullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChunkData) {
            final SPacketChunkData l_Packet = (SPacketChunkData)event.getPacket();
            int l_ChestsCount = 0;
            int shulkers = 0;
            for (final NBTTagCompound l_Tag : l_Packet.getTileEntityTags()) {
                final String l_Id = l_Tag.getString("id");
                if (l_Id.equals("minecraft:chest") && this.chests.getValue()) {
                    ++l_ChestsCount;
                }
                else {
                    if (!l_Id.equals("minecraft:shulker_box")) {
                        continue;
                    }
                    if (!this.Shulkers.getValue()) {
                        continue;
                    }
                    ++shulkers;
                }
            }
            if (l_ChestsCount >= this.chestsValue.getValue()) {
                this.SendMessage(String.format("%s chests located at X: %s, Z: %s", l_ChestsCount, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
            }
            if (shulkers >= this.shulkersValue.getValue()) {
                this.SendMessage(String.format("%s shulker boxes at X: %s, Z: %s", shulkers, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
            }
        }
    }
    
    private void SendMessage(final String message, final boolean save) {
        final String string;
        final String server = string = (Minecraft.getMinecraft().isSingleplayer() ? "singleplayer".toUpperCase() : StashLogger.mc.getCurrentServerData().serverIP);
        if (this.writeToFile.getValue() && save) {
            try {
                final FileWriter writer = new FileWriter(this.mainFolder + "/stashes.txt", true);
                writer.write("[" + server + "]: " + message + "\n");
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        StashLogger.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
        Command.sendMessage(ChatFormatting.GREEN + message);
    }
}
