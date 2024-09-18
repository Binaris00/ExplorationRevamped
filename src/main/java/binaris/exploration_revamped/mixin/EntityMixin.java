package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.entity.IronBoatEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {

    @Redirect(method = "getVelocityMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getVelocityMultiplier()F"))
    private float getVelocityMultiplier(Block target) {
        Entity entity = ((Entity) (Object) this);
        boolean isBoat = entity instanceof IronBoatEntity;
        if (isBoat) {
            return 1.05F;
        }
        else {
            return target.getVelocityMultiplier();
        }
    }
}
