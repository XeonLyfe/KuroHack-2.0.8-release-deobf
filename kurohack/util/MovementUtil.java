//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.util;

import kurohack.util.element.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class MovementUtil implements IUtil
{
    public static Vec3d calculateLine(final Vec3d x1, final Vec3d x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y) + multiply(x2.z - x1.z));
        final double unitSlopeX = (x2.x - x1.x) / length;
        final double unitSlopeY = (x2.y - x1.y) / length;
        final double unitSlopeZ = (x2.z - x1.z) / length;
        final double x3 = x1.x + unitSlopeX * distance;
        final double y = x1.y + unitSlopeY * distance;
        final double z = x1.z + unitSlopeZ * distance;
        return new Vec3d(x3, y, z);
    }
    
    public static Vec2f calculateLineNoY(final Vec2f x1, final Vec2f x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y));
        final double unitSlopeX = (x2.x - x1.x) / length;
        final double unitSlopeZ = (x2.y - x1.y) / length;
        final float x3 = (float)(x1.x + unitSlopeX * distance);
        final float z = (float)(x1.y + unitSlopeZ * distance);
        return new Vec2f(x3, z);
    }
    
    public static double multiply(final double one) {
        return one * one;
    }
    
    public static Vec3d extrapolatePlayerPositionWithGravity(final EntityPlayer player, final int ticks) {
        double totalDistance = 0.0;
        double extrapolatedMotionY = player.motionY;
        for (int i = 0; i < ticks; ++i) {
            totalDistance += multiply(player.motionX) + multiply(extrapolatedMotionY) + multiply(player.motionZ);
            extrapolatedMotionY -= 0.1;
        }
        final double horizontalDistance = multiply(player.motionX) + multiply(player.motionZ) * ticks;
        final Vec2f horizontalVec = calculateLineNoY(new Vec2f((float)player.lastTickPosX, (float)player.lastTickPosZ), new Vec2f((float)player.posX, (float)player.posZ), horizontalDistance);
        double addedY = player.motionY;
        double finalY = player.posY;
        final Vec3d tempPos = new Vec3d((double)horizontalVec.x, player.posY, (double)horizontalVec.y);
        for (int j = 0; j < ticks; ++j) {
            finalY += addedY;
            addedY -= 0.1;
        }
        final RayTraceResult result = MovementUtil.mc.world.rayTraceBlocks(player.getPositionVector(), new Vec3d(tempPos.x, finalY, tempPos.z));
        if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
            return new Vec3d(tempPos.x, finalY, tempPos.z);
        }
        return result.hitVec;
    }
    
    public static Vec3d extrapolatePlayerPosition(final EntityPlayer player, final int ticks) {
        final double totalDistance = 0.0;
        final double extrapolatedMotionY = player.motionY;
        for (int i = 0; i < ticks; ++i) {}
        final Vec3d lastPos = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
        final Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);
        final double distance = multiply(player.motionX) + multiply(player.motionY) + multiply(player.motionZ);
        double extrapolatedPosY = player.posY;
        if (!player.hasNoGravity()) {
            extrapolatedPosY -= 0.1;
        }
        final Vec3d tempVec = calculateLine(lastPos, currentPos, distance * ticks);
        final Vec3d finalVec = new Vec3d(tempVec.x, extrapolatedPosY, tempVec.z);
        final RayTraceResult result = MovementUtil.mc.world.rayTraceBlocks(player.getPositionVector(), finalVec);
        return new Vec3d(tempVec.x, player.posY, tempVec.z);
    }
}
