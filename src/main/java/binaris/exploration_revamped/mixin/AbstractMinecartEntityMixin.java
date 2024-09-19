package binaris.exploration_revamped.mixin;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin {
    @Unique
    private static final double VANILLA_MAX_SPEED = 8.0 / 20.0;
    @Unique
    private final AbstractMinecartEntity minecart = (AbstractMinecartEntity) (Object) this;
    @Unique
    private static final double SQRT_TWO = 1.414213;

    @Unique
    private BlockPos lastPos = null;
    @Unique
    private double currentMaxSpeed = VANILLA_MAX_SPEED;
    @Unique
    private double lastMaxSpeed = VANILLA_MAX_SPEED;
    @Unique
    private Vec3d lastSpeedPos = null;
    @Unique
    private long lastSpeedTime = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        clampVelocity();
    }

    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", ordinal = 0, target = "java/lang/Math.min(DD)D"))
    public double speedClamp(double d1, double d2) {
        final double maxSpeed = getModifiedMaxSpeed();
        return maxSpeed == VANILLA_MAX_SPEED ? Math.min(d1, d2)
                : Math.min(maxSpeed * SQRT_TWO, d2);
    }

    @Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
    protected void getMaxSpeed(CallbackInfoReturnable<Double> cir) {
        final double maxSpeed = getModifiedMaxSpeed();
        if (maxSpeed != VANILLA_MAX_SPEED) {
            cir.setReturnValue(maxSpeed);
        }
    }

    @Unique
    private double getModifiedMaxSpeed() {
        final BlockPos currentPos = BlockPos.ofFloored(minecart.getPos());
        if (currentPos.equals(lastPos)) return currentMaxSpeed;
        lastPos = currentPos;

        final Vec3d v = minecart.getVelocity();
        final BlockPos nextPos = new BlockPos(
                currentPos.getX() + MathHelper.sign(v.x),
                currentPos.getY(),
                currentPos.getZ() + MathHelper.sign(v.z)
        );

        final BlockState nextState = minecart.getWorld().getBlockState(nextPos);
        if (nextState.getBlock() instanceof AbstractRailBlock rail) {
            final RailShape shape = nextState.get(rail.getShapeProperty());
            if (shape == RailShape.NORTH_EAST || shape == RailShape.NORTH_WEST || shape == RailShape.SOUTH_EAST || shape == RailShape.SOUTH_WEST) {
                return currentMaxSpeed = VANILLA_MAX_SPEED;
            } else {
                final BlockState underState = minecart.getWorld().getBlockState(currentPos.down());
                final Identifier underBlockId = Registries.BLOCK.getId(underState.getBlock());
                Integer speedLimit = 10;
                if(nextState.getBlock() instanceof RailBlock) speedLimit = minecart instanceof FurnaceMinecartEntity ? 17 : 12;
                if(nextState.getBlock() instanceof PoweredRailBlock) speedLimit = minecart instanceof FurnaceMinecartEntity ? 25 : 16;

                if (speedLimit != null) {
                    return currentMaxSpeed = speedLimit / 20.0;
                } else {
                    return currentMaxSpeed = VANILLA_MAX_SPEED;
                }
            }
        } else {
            return currentMaxSpeed = VANILLA_MAX_SPEED;
        }
    }

    @Unique
    private void clampVelocity() {
        if (getModifiedMaxSpeed() != lastMaxSpeed) {
            double smaller = Math.min(getModifiedMaxSpeed(), lastMaxSpeed);
            final Vec3d vel = minecart.getVelocity();
            minecart.setVelocity(new Vec3d(MathHelper.clamp(vel.x, -smaller, smaller), 0.0,
                    MathHelper.clamp(vel.z, -smaller, smaller)));
        }
        lastMaxSpeed = currentMaxSpeed;
    }
}
