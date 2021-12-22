//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import kurohack.util.*;
import net.minecraft.util.math.*;

public class AntiCity extends Module
{
    public Setting<Boolean> packet;
    public Setting<Boolean> rotate;
    
    public AntiCity() {
        super("AntiCity", "", Category.COMBAT, true, false, false);
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
    }
    
    @Override
    public void onUpdate() {
        final BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)AntiCity.mc.player);
        final int preSlot = AutoTrap.mc.player.inventory.currentItem;
        final int whileSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (EntityUtil.isSafe((Entity)AntiCity.mc.player)) {
            if (AntiCity.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN && AntiCity.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR && AntiCity.mc.world.getBlockState(pos.north().north().up()).getBlock() == Blocks.AIR && (AntiCity.mc.world.getBlockState(pos.north().north().down()).getBlock() == Blocks.OBSIDIAN || AntiCity.mc.world.getBlockState(pos.north().north().down()).getBlock() == Blocks.BEDROCK) && whileSlot > -1) {
                AutoTrap.mc.player.inventory.currentItem = whileSlot;
                AutoTrap.mc.playerController.updateController();
                BlockUtil.placeBlock(pos.north().north(), EnumHand.MAIN_HAND, this.rotate.getCurrentState(), this.packet.getCurrentState(), false);
                AutoTrap.mc.player.inventory.currentItem = preSlot;
                AutoTrap.mc.playerController.updateController();
            }
            if (AntiCity.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN && AntiCity.mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR && AntiCity.mc.world.getBlockState(pos.east().east().up()).getBlock() == Blocks.AIR && (AntiCity.mc.world.getBlockState(pos.east().east().down()).getBlock() == Blocks.OBSIDIAN || AntiCity.mc.world.getBlockState(pos.east().east().down()).getBlock() == Blocks.BEDROCK) && whileSlot > -1) {
                AutoTrap.mc.player.inventory.currentItem = whileSlot;
                AutoTrap.mc.playerController.updateController();
                BlockUtil.placeBlock(pos.east().east(), EnumHand.MAIN_HAND, this.rotate.getCurrentState(), this.packet.getCurrentState(), false);
                AutoTrap.mc.player.inventory.currentItem = preSlot;
                AutoTrap.mc.playerController.updateController();
            }
            if (AntiCity.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN && AntiCity.mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR && AntiCity.mc.world.getBlockState(pos.south().south().up()).getBlock() == Blocks.AIR && (AntiCity.mc.world.getBlockState(pos.south().south().down()).getBlock() == Blocks.OBSIDIAN || AntiCity.mc.world.getBlockState(pos.south().south().down()).getBlock() == Blocks.BEDROCK) && whileSlot > -1) {
                AutoTrap.mc.player.inventory.currentItem = whileSlot;
                AutoTrap.mc.playerController.updateController();
                BlockUtil.placeBlock(pos.south().south(), EnumHand.MAIN_HAND, this.rotate.getCurrentState(), this.packet.getCurrentState(), false);
                AutoTrap.mc.player.inventory.currentItem = preSlot;
                AutoTrap.mc.playerController.updateController();
            }
            if (AntiCity.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN && AntiCity.mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.AIR && AntiCity.mc.world.getBlockState(pos.west().west().up()).getBlock() == Blocks.AIR && (AntiCity.mc.world.getBlockState(pos.west().west().down()).getBlock() == Blocks.OBSIDIAN || AntiCity.mc.world.getBlockState(pos.west().west().down()).getBlock() == Blocks.BEDROCK) && whileSlot > -1) {
                AutoTrap.mc.player.inventory.currentItem = whileSlot;
                AutoTrap.mc.playerController.updateController();
                BlockUtil.placeBlock(pos.west().west(), EnumHand.MAIN_HAND, this.rotate.getCurrentState(), this.packet.getCurrentState(), false);
                AutoTrap.mc.player.inventory.currentItem = preSlot;
                AutoTrap.mc.playerController.updateController();
            }
        }
    }
}
