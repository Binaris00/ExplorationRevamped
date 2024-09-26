package binaris.exploration_revamped.entity;

import binaris.exploration_revamped.item.IronBoatItem;
import binaris.exploration_revamped.registries.EntityRegistries;
import binaris.exploration_revamped.registries.ItemRegistries;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class IronBoatEntity extends BoatEntity {
    private int durability;

    public IronBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
        this.intersectionChecked = true;
        this.durability = IronBoatItem.DURABILITY_DEFAULT;
    }

    public IronBoatEntity(World world, Vec3d vec3d, int durability){
        super(EntityRegistries.IRON_BOAT_ENTITY, world);
        this.setPos(vec3d.x, vec3d.y, vec3d.z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = vec3d.x;
        this.prevY = vec3d.y;
        this.prevZ = vec3d.z;
        this.durability = durability;
    }

    @Override
    public Item asItem() {
        return ItemRegistries.IRON_BOAT_ITEM;
    }

    @Override
    protected void killAndDropSelf(DamageSource source) {
        this.kill();
        if (this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack stack = new ItemStack(ItemRegistries.IRON_BOAT_ITEM);
            NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
            nbt.putInt("Durability", durability);
            stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
            this.dropStack(stack);
        }
    }

    @Override
    public ItemStack getPickBlockStack() {
        ItemStack stack = new ItemStack(ItemRegistries.IRON_BOAT_ITEM);
        NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        nbt.putInt("Durability", durability);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        return stack;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("Durability", durability);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        durability = nbt.getInt("Durability");
    }

    @Override
    public void tick() {
        super.tick();

        if(this.age <= 10) {
            this.refreshPositionAndAngles(this.getPos(), this.getYaw(), this.getPitch());
        }

        if (this.isAlive() && !this.getWorld().isClient) {
            if (durability > 0) {
                if (this.hasPassengers()) {
                    durability--;
                    updateItemDurability();

                    // Notify player about remaining durability
                    Entity passenger = this.getFirstPassenger();
                    if (passenger instanceof PlayerEntity player && durability % 1200 == 0) {
                        player.sendMessage(Text.translatable("boat.entity.exploration_revamped.durability_message", durability / 1200), true);
                    }
                }
            } else {
                if (this.hasPassengers()) {
                    Entity passenger = this.getFirstPassenger();
                    if (passenger instanceof PlayerEntity player) {
                        player.sendMessage(Text.of("The Iron Boat has broken!"), true);
                    }
                }
                this.discard();
            }
        }
    }

    private void updateItemDurability() {
//        if (this.hasPassengers()) {
//            Entity passenger = this.getFirstPassenger();
//            if (passenger instanceof PlayerEntity player) {
//                ItemStack boatItem = player.getMainHandStack();
//
//                if (boatItem.getItem() == ItemRegistries.IRON_BOAT_ITEM) {
//                    NbtCompound nbt = boatItem.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
//                    nbt.putInt("Durability", durability);
//                    boatItem.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
//                    boatItem.setDamage(IronBoatItem.DURABILITY_DEFAULT - durability); // Update durability display
//                }
//            }
//        }
    }
}
