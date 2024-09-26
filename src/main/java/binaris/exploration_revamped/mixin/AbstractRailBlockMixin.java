package binaris.exploration_revamped.mixin;

import binaris.exploration_revamped.block.NormalPoweredRail;
import binaris.exploration_revamped.block.SuperPoweredRail;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractRailBlock.class)
public class AbstractRailBlockMixin {

    @Inject(method = "isRail(Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private static void ER$isRail(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(state.getBlock() instanceof NormalPoweredRail || state.getBlock() instanceof SuperPoweredRail) cir.setReturnValue(true);
    }
}
