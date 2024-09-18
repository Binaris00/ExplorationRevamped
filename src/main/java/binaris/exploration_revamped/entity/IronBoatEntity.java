package binaris.exploration_revamped.entity;

import binaris.exploration_revamped.registries.EntityRegistries;
import binaris.exploration_revamped.registries.ItemRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IronBoatEntity extends BoatEntity {
    public IronBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
        this.intersectionChecked = true;
    }

    public IronBoatEntity(World world, Vec3d vec3d){
        super(EntityRegistries.IRON_BOAT_ENTITY, world);
        this.setPos(vec3d.x, vec3d.y, vec3d.z);
        this.prevX = vec3d.x;
        this.prevY = vec3d.y;
        this.prevZ = vec3d.z;
    }

    @Override
    public Item asItem() {
        return ItemRegistries.IRON_BOAT_ITEM;
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ItemRegistries.IRON_BOAT_ITEM);
    }
}
