package net.devtech.stacc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    @Inject(at = @At("RETURN"), method = "getItemBarStep", cancellable = true)
    private void getItemOccupancyFix(ItemStack stack, CallbackInfoReturnable<Integer> c) {
        if (c.getReturnValue() <= 0) {
            c.setReturnValue(1);
        }
    }
}