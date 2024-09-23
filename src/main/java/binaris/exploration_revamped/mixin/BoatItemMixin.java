package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.entity.IronBoatEntity;
import binaris.exploration_revamped.item.IronBoatItem;
import binaris.exploration_revamped.registries.ItemRegistries;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @Inject(method = "createEntity", at = @At("HEAD"), cancellable = true)
    public void ER$createEntity(World world, HitResult hitResult, ItemStack stack, PlayerEntity player, CallbackInfoReturnable<BoatEntity> cir) {
        if(stack.getItem() == ItemRegistries.IRON_BOAT_ITEM) {
            NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
            int durability = nbt.contains("Durability") ? nbt.getInt("Durability") : IronBoatItem.DURABILITY_DEFAULT;

            cir.setReturnValue(new IronBoatEntity(world, hitResult.getPos().add(0.0D, 0.1D, 0.0D), durability));
        }
    }
}
