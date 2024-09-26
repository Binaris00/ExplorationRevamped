package binaris.exploration_revamped.util;

import binaris.exploration_revamped.registries.ItemRegistries;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class CustomLootTables {
    private static final Identifier ANCIENT_CITY = Identifier.of("chests/ancient_city");
    private static final Identifier WOODLAND_MANSION = Identifier.of("chests/woodland_mansion");

    public static void init() {
        LootTableEvents.MODIFY.register((key, original, source, registries) -> {
            if(key.getValue().equals(ANCIENT_CITY)) {
                LootPool pool = LootPool.builder()
                        .with(ItemEntry.builder(ItemRegistries.COPPER_COMPASS_ITEM).build())
                        .conditionally(RandomChanceLootCondition.builder(0.2f).build())
                        .build();

                original.pool(pool);

                LootPool pool1 = LootPool.builder()
                        .with(ItemEntry.builder(ItemRegistries.LOG_COMPASS_ITEM).build())
                        .conditionally(RandomChanceLootCondition.builder(0.2f).build())
                        .build();

                original.pool(pool1);
            }
        });

        LootTableEvents.MODIFY.register((key, original, source, registries) -> {
            if(key.getValue().equals(WOODLAND_MANSION)) {
                LootPool pool = LootPool.builder()
                        .with(ItemEntry.builder(ItemRegistries.SCULK_COMPASS_ITEM).build())
                        .conditionally(RandomChanceLootCondition.builder(0.2f).build())
                        .build();

                original.pool(pool);
            }
        });
    }
}
