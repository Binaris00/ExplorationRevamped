package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.entity.IronBoatEntity;
import binaris.exploration_revamped.item.IronBoatItem;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @Unique
    private BoatItem boatItem = (BoatItem)(Object)this;

//    @Redirect(method = "createEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;<init>(Lnet/minecraft/world/World;DDD)V"))
//    private void ER$createBoat(BoatEntity instance, World world, double x, double y, double z){
//        return boatItem.asItem() instanceof IronBoatItem ? new IronBoatEntity(world, new Vec3d(x, y, z)) : new BoatEntity(world, x, y, z);
//    }
}
