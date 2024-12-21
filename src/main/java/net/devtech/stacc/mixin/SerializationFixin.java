package net.devtech.stacc.mixin;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * fixes ItemStack to serialize count as int instead of byte
 */
@Mixin (ItemStack.class)
public abstract class SerializationFixin {
	@Unique
	private static final NumberFormat FORMAT = NumberFormat.getNumberInstance(Locale.US);
//	@Shadow private int count;

	// Unsure if this needs to be kept, maintaining with new Data Components will just be a headache.

//	@Inject (at = @At ("TAIL"), method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V")
//	void onDeserialization(NbtCompound tag, CallbackInfo callbackInformation) {
//		if (tag.contains("countInteger")) {
//			this.count = tag.getInt("countInteger");
//		}
//	}
//
//	@Inject (at = @At ("TAIL"), method = "writeNbt")
//	void onSerialization(NbtCompound tag, CallbackInfoReturnable<NbtCompound> callbackInformationReturnable) {
//		if (this.count > Byte.MAX_VALUE) {
//			tag.putInt("countInteger", this.count);
//			// make downgrading less painful
//			tag.putByte("Count", Byte.MAX_VALUE);
//		}
//	}

	@Inject (method = "getTooltip", at = @At ("RETURN"), cancellable = true)
	private void addOverflowTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
		if (this.getCount() > 1000) {
			List<Text> texts = cir.getReturnValue();
			texts.add(1, Text.literal(FORMAT.format(this.getCount())).formatted(Formatting.GRAY));
		}
	}

	@Shadow
	public abstract int getCount();
}