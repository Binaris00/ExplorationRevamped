package binaris.exploration_revamped.item;

import binaris.exploration_revamped.entity.IronBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.function.Predicate;

public class IronBoatItem extends Item{
    private static final Predicate<Entity> ENTITY_PREDICATE =  EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);


    public IronBoatItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        Vec3d vec3d = hitResult.getPos().add(0, 2, 0);  // Offset the position slightly above the ground
        if (hitResult.getType() == BlockHitResult.Type.MISS) {
            return TypedActionResult.pass(itemstack);
        } else {
            Vec3d vector3d = user.getRotationVec(1.0F);
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vector3d.multiply(5.0)).expand(1.0), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getEyePos();

                for (Entity entity : list) {
                    Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemstack);
                    }
                }
            }

            if (hitResult.getType() == BlockHitResult.Type.BLOCK) {
                IronBoatEntity entity = createEntity(world, vec3d, itemstack, user);
                entity.setYaw(user.getYaw());

                if (!world.isSpaceEmpty(entity, entity.getBoundingBox())) {
                    return TypedActionResult.fail(itemstack);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(entity);
                        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, vec3d);
                        itemstack.decrementUnlessCreative(1, user);
                    }
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemstack, world.isClient());
                }
            } else {
                return TypedActionResult.pass(itemstack);
            }
        }
    }

    private IronBoatEntity createEntity(World world, Vec3d vec3d, ItemStack stack, PlayerEntity player) {
        IronBoatEntity boatEntity = new IronBoatEntity(world, vec3d);
        if (world instanceof ServerWorld serverWorld) {
            EntityType.copier(serverWorld, stack, player).accept(boatEntity);
        }

        return boatEntity;
    }
}
