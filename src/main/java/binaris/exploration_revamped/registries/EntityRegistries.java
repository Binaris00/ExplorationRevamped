package binaris.exploration_revamped.registries;

import binaris.exploration_revamped.ERCommonMod;
import binaris.exploration_revamped.entity.IronBoatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class EntityRegistries {
    public static final EntityType<IronBoatEntity> IRON_BOAT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(ERCommonMod.MOD_ID, "iron_boat"),
            FabricEntityTypeBuilder.<IronBoatEntity>create(SpawnGroup.MISC, IronBoatEntity::new)
                    .dimensions(EntityDimensions.fixed(1.375F, 0.5625F))
                    .trackRangeBlocks(10)
                    .build());

    private EntityRegistries() {
    }

    public static void init() {
    }
}
