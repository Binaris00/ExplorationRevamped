package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.entity.IronBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {

    @Unique
    public BoatEntity boat = (BoatEntity) (Object) this;

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(method = "checkLocation", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/vehicle/BoatEntity;getNearbySlipperiness()F"))
    protected float limitIceSlipperiness(float original) {
        boolean isBoat = boat instanceof IronBoatEntity;

        return isBoat ? original * 0.952f : original;
    }
}
