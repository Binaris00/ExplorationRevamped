package binaris.exploration_revamped.registries;

import binaris.exploration_revamped.ERCommonMod;
import binaris.exploration_revamped.item.BuildCompassItem;
import binaris.exploration_revamped.item.IronBoatItem;
import binaris.exploration_revamped.item.RerollItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

public final class ItemRegistries {

    public static Item REROLL_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "reroll_item"), new RerollItem());
    public static Item IRON_BOAT_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "iron_boat"), new IronBoatItem());

    public static Item SCULK_COMPASS_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "sculk_compass"), new BuildCompassItem(Identifier.of("minecraft:ancient_city")));
    public static Item LOG_COMPASS_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "log_compass"), new BuildCompassItem(Identifier.of("minecraft:mansion")));
    public static Item COPPER_COMPASS_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "copper_compass"), new BuildCompassItem(Identifier.of("minecraft:trial_chambers")));

    private ItemRegistries() {
    }

    public static void init() {
    }

    public static Item item(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
