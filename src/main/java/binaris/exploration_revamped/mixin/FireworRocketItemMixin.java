package binaris.exploration_revamped.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public abstract class FireworRocketItemMixin {

    @Inject(method = "use", at = @At("HEAD"))
    public void er$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(user.isFallFlying()){
            ItemStack stack = user.getEquippedStack(EquipmentSlot.CHEST);
            if (stack.isOf(Items.ELYTRA) && ElytraItem.isUsable(stack)){
                stack.damage(5, user, EquipmentSlot.CHEST);
            }

            user.getItemCooldownManager().set((FireworkRocketItem)(Object)this, 100);
        }
    }


}
