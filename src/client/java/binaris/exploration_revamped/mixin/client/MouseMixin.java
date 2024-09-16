package binaris.exploration_revamped.mixin.client;

import binaris.exploration_revamped.ERClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Smoother;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow
    @Final
    private Smoother cursorXSmoother;

    @Shadow @Final private Smoother cursorYSmoother;

    @Shadow private double cursorDeltaX;

    @Shadow private double cursorDeltaY;

    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        float d = (float) ((MinecraftClient.getInstance().options.discreteMouseScroll.getValue() ? Math.signum(vertical) : vertical) * MinecraftClient.getInstance().options.mouseWheelSensitivity.getValue());
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null && player.isSwimming()) {
            d *= 0.5;
            player.playSound(SoundEvents.ENTITY_DOLPHIN_SWIM, 1.0f, 1.0f);
        } else if (player != null && player.isUsingSpyglass() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
            ERClientMod.MULTIPLIER = MathHelper.clamp(ERClientMod.MULTIPLIER - (d * .1f), .1f, .8f);
            player.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0f, 1.0f + (1 * (1 - ERClientMod.MULTIPLIER) * (1 - ERClientMod.MULTIPLIER)));
            ci.cancel();
        }
    }

    @ModifyVariable(method = "updateMouse", at = @At("STORE"), ordinal = 1)
    public double modifyDisplacementX(double value, double d) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player.isFallFlying()) {
            double flySensitivity = value * 0.75;
            cursorXSmoother.clear();
            return flySensitivity;
        } else if (player.isUsingSpyglass()) {
            double sensitivity = MinecraftClient.getInstance().options.mouseSensitivity.getValue() * .6 + .2;
            double spyglassSensitivity = sensitivity * ERClientMod.MULTIPLIER;
            cursorXSmoother.clear();
            return cursorDeltaX * spyglassSensitivity;
        }
        return value;
    }

    @ModifyVariable(method = "updateMouse", at = @At("STORE"), ordinal = 2)
    public double modifyDisplacementY(double value, double d) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player.isFallFlying()) {
            double flySensitivity = value * 0.75;
            cursorYSmoother.clear();
            return flySensitivity;
        } else if (player.isUsingSpyglass()) {
            double sensitivity = MinecraftClient.getInstance().options.mouseSensitivity.getValue() * .6 + .2;
            double spyglassSensitivity = sensitivity * ERClientMod.MULTIPLIER;
            cursorYSmoother.clear();
            return cursorDeltaY * spyglassSensitivity;
        }
        return value;
    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Smoother;clear()V", ordinal = 0))
    public void cancelSmoothXReset(Smoother instance) {
        if (!MinecraftClient.getInstance().player.isFallFlying()) {
            instance.clear();
        }
    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Smoother;clear()V", ordinal = 1))
    public void cancelSmoothYReset(Smoother instance) {
        if (!MinecraftClient.getInstance().player.isFallFlying()) {
            instance.clear();
        }
    }
}