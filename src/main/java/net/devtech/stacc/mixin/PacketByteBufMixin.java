package net.devtech.stacc.mixin;

import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.PacketByteBuf;

@Mixin (PacketByteBuf.class)
public abstract class PacketByteBufMixin {
	@Inject (method = "writeItemStack", at = @At (value = "INVOKE", target = "Lnet/minecraft/util/PacketByteBuf;writeCompoundTag(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/util/PacketByteBuf;"))
	private void write(ItemStack itemStack, CallbackInfoReturnable<PacketByteBuf> cir) {
		this.writeInt(itemStack.getCount());
	}

	@Shadow
	public abstract ByteBuf writeInt(int i);

	@ModifyArg (method = "readItemStack", at = @At (value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/ItemConvertible;I)V"), index = 1)
	private int doThing(int amount) {
		return this.readInt();
	}

	@Shadow
	public abstract int readInt();
}
