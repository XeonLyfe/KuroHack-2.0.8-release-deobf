//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.features.modules.combat;

import kurohack.features.modules.*;
import kurohack.features.setting.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import kurohack.util.*;
import kurohack.*;

public class Flatten extends Module
{
    private final Setting<Float> placerange;
    private final Setting<Integer> blocksPerTick;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> autoDisable;
    private final Vec3d[] offsetsDefault;
    private int offsetStep;
    private int oldSlot;
    
    public Flatten() {
        super("Flatten", "Flatter then zprestige_s 9 yr gf.", Category.COMBAT, true, false, false);
        this.placerange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)6.0f, (T)1.0f, (T)10.0f));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("Block/Place", (T)8, (T)1, (T)20));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.offsetsDefault = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0) };
        this.offsetStep = 0;
        this.oldSlot = -1;
    }
    
    @Override
    public void onEnable() {
        this.oldSlot = Flatten.mc.player.inventory.currentItem;
    }
    
    @Override
    public void onDisable() {
        this.oldSlot = -1;
    }
    
    @Override
    public void onUpdate() {
        final EntityPlayer closest_target = this.findClosestTarget();
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (closest_target == null) {
            this.disable();
            return;
        }
        final List<Vec3d> place_targets = new ArrayList<Vec3d>();
        Collections.addAll(place_targets, this.offsetsDefault);
        int blocks_placed = 0;
        while (blocks_placed < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= place_targets.size()) {
                this.offsetStep = 0;
                break;
            }
            final BlockPos offset_pos = new BlockPos((Vec3d)place_targets.get(this.offsetStep));
            final BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = Flatten.mc.world.getBlockState(target_pos).getMaterial().isReplaceable();
            for (final Entity entity : Flatten.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(target_pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    should_try_place = false;
                    break;
                }
            }
            if (should_try_place) {
                this.place(target_pos, obbySlot, this.oldSlot);
                ++blocks_placed;
            }
            ++this.offsetStep;
        }
        if (this.autoDisable.getValue()) {
            this.disable();
        }
    }
    
    private void place(final BlockPos pos, final int slot, final int oldSlot) {
        Flatten.mc.player.inventory.currentItem = slot;
        Flatten.mc.playerController.updateController();
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), Flatten.mc.player.isSneaking());
        Flatten.mc.player.inventory.currentItem = oldSlot;
        Flatten.mc.playerController.updateController();
    }
    
    private EntityPlayer findClosestTarget() {
        if (Flatten.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : Flatten.mc.world.playerEntities) {
            if (target != Flatten.mc.player) {
                if (!target.isEntityAlive()) {
                    continue;
                }
                if (KuroHack.friendManager.isFriend(target.getName())) {
                    continue;
                }
                if (target.getHealth() <= 0.0f) {
                    continue;
                }
                if (Flatten.mc.player.getDistance((Entity)target) > this.placerange.getValue()) {
                    continue;
                }
                if (closestTarget != null && Flatten.mc.player.getDistance((Entity)target) > Flatten.mc.player.getDistance((Entity)closestTarget)) {
                    continue;
                }
                closestTarget = target;
            }
        }
        return closestTarget;
    }
}
