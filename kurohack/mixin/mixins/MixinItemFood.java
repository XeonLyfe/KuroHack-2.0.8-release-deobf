//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import kurohack.features.modules.combat.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemFood.class })
public class MixinItemFood
{
    @Inject(method = { "onItemUseFinish" }, at = { @At("RETURN") }, cancellable = true)
    public void onItemUseFinishHook(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final CallbackInfoReturnable<ItemStack> info) {
        Offhand.getInstance().onItemFinish(stack, entityLiving);
    }
}
