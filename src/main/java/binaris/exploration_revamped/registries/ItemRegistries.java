package binaris.exploration_revamped.registries;

import binaris.exploration_revamped.ERCommonMod;
import binaris.exploration_revamped.item.RerollItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

public final class ItemRegistries {

    public static Item REROLL_ITEM = item(Identifier.of(ERCommonMod.MOD_ID, "reroll_item"), new RerollItem());

    private ItemRegistries() {
    }

    public static void init() {
    }

    public static Item item(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
