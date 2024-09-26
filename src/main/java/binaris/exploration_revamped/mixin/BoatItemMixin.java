package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.entity.IronBoatEntity;
import binaris.exploration_revamped.item.IronBoatItem;
import binaris.exploration_revamped.registries.ItemRegistries;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @Inject(method = "createEntity", at = @At("HEAD"), cancellable = true)
    public void ER$createEntity(World world, HitResult hitResult, ItemStack stack, PlayerEntity player, CallbackInfoReturnable<BoatEntity> cir) {
        if(stack.getItem() == ItemRegistries.IRON_BOAT_ITEM) {
            NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
            int durability = nbt.contains("Durability") ? nbt.getInt("Durability") : IronBoatItem.DURABILITY_DEFAULT;

            IronBoatEntity boatEntity = new IronBoatEntity(world, hitResult.getPos().add(0, 0.1D, 0), durability);

            if (world instanceof ServerWorld serverWorld) {
                EntityType.copier(serverWorld, stack, player).accept(boatEntity);
            }

            cir.setReturnValue(boatEntity);
        }
    }

    @Redirect(method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isSpaceEmpty(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Z"))
    public boolean ER$use(World instance, Entity entity, Box box){
        // I KNOW, this is a weird solution
        if(entity instanceof IronBoatEntity){
            // Create dummy boat entity to check if the space is empty
            BoatEntity boatEntity = new BoatEntity(instance, entity.getPos().x, entity.getPos().y, entity.getPos().z);
            boatEntity.setVariant(BoatEntity.Type.OAK);
            boatEntity.setYaw(entity.getYaw());

            return instance.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox());
        }
        return instance.isSpaceEmpty(entity, box);
    }
}
