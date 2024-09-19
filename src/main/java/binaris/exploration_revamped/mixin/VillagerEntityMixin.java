package binaris.exploration_revamped.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    @Unique
    VillagerEntity villager = (VillagerEntity) (Object) this;

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void ER$tick(CallbackInfo ci){
        var job = villager.getBrain().getOptionalRegisteredMemory(MemoryModuleType.JOB_SITE);
        if (job.isPresent() && villager.getExperience() == 0) villager.setExperience(1);
    }
}
