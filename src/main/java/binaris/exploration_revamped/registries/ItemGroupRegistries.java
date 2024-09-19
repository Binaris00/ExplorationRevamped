package binaris.exploration_revamped.registries;

import binaris.exploration_revamped.ERCommonMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ItemGroupRegistries {
    public static final RegistryKey<ItemGroup> GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(ERCommonMod.MOD_ID, "group"));

    private ItemGroupRegistries() {
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup.exploration_revamped.group"))
                .icon(() -> new ItemStack(ItemRegistries.IRON_BOAT_ITEM))
                        .entries((displayContext, entries) -> {
                            entries.add(new ItemStack(ItemRegistries.REROLL_ITEM));
                            entries.add(new ItemStack(ItemRegistries.IRON_BOAT_ITEM));
                            entries.add(new ItemStack(ItemRegistries.SCULK_COMPASS_ITEM));
                            entries.add(new ItemStack(ItemRegistries.LOG_COMPASS_ITEM));
                            entries.add(new ItemStack(ItemRegistries.COPPER_COMPASS_ITEM));
                        })
                .build());
    }
}
