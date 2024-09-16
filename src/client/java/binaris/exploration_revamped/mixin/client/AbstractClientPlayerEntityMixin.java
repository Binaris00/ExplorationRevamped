package binaris.exploration_revamped.mixin.client;

import binaris.exploration_revamped.ERClientMod;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyReturnValue(method = "getFovMultiplier", at = @At("RETURN"))
    public float fovMultiplier(float original){
        if(MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && isUsingSpyglass()){
            return ERClientMod.MULTIPLIER;
        }
        return original;
    }
}
