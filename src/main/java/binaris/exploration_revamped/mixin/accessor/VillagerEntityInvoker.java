package binaris.exploration_revamped.mixin.accessor;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerEntity.class)
public interface VillagerEntityInvoker {
    @Invoker("fillRecipes")
    void invokeFillRecipes();

    @Invoker("prepareOffersFor")
    void invokePrepareOffersFor(PlayerEntity player);
}
