package binaris.exploration_revamped.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;

public class BuildCompassItem extends Item{
    public static final String STRUCTURE_KEY = "structure_key";
    public static final String STRUCTURE_POS = "structure_pos_key";
    public static final String FIND_STRUCTURE_KEY = "find_structure_key";

    private Identifier structureKey;
    private BlockPos structurePos;
    private boolean findedStructure = false;

    public BuildCompassItem(Identifier identifier) {
        super(new Settings().maxCount(1));
        this.structureKey = identifier;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(findedStructure) return TypedActionResult.pass(stack);
        if(world.isClient) return TypedActionResult.success(stack);

        Registry<Structure> registry = world.getRegistryManager().get(RegistryKeys.STRUCTURE);
        RegistryEntryList<Structure> featureHolderSet = registry.getEntry(structureKey).map(RegistryEntryList::of).orElse(null);
        if(featureHolderSet != null){
            if(world instanceof ServerWorld) {
                Pair<BlockPos, RegistryEntry<Structure>> pair = findNearestMapStructure((ServerWorld) world, featureHolderSet, user.getBlockPos());
                if (pair != null) {
                    this.structurePos = pair.getFirst();
                    this.findedStructure = true;
                    NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).getNbt();
                    nbt.putBoolean(FIND_STRUCTURE_KEY, true);
                    nbt.putString(STRUCTURE_KEY, this.structureKey.toString());
                    nbt.putLong(STRUCTURE_POS, this.structurePos.asLong());

                    stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                }
            }
        }

        return TypedActionResult.success(stack);
    }


    public static Pair<BlockPos, RegistryEntry<Structure>> findNearestMapStructure(ServerWorld serverWorld, RegistryEntryList<Structure> registryEntryList, BlockPos pos) {
        return serverWorld.getChunkManager().getChunkGenerator().locateStructure(serverWorld, registryEntryList, pos, 100, false);
    }
}
