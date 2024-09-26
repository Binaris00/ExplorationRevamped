package binaris.exploration_revamped.registries;

import binaris.exploration_revamped.ERCommonMod;
import binaris.exploration_revamped.block.NormalPoweredRail;
import binaris.exploration_revamped.block.SuperPoweredRail;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public final class BlockRegistries {
    public static Block NORMAL_POWERED_RAIL = block("normal_powered_rail", new NormalPoweredRail(AbstractBlock.Settings.create().noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL)));
    public static Block SUPER_POWERED_RAIL = block("super_powered_rail", new SuperPoweredRail(AbstractBlock.Settings.create().noCollision().strength(0.7F).sounds(BlockSoundGroup.METAL)));


    private BlockRegistries() {
    }

    public static void init() {
    }

    private static Block block(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(ERCommonMod.MOD_ID, id), block);
    }

    private static Block blockItem(String id, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ERCommonMod.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        return block(id, block);
    }
}
