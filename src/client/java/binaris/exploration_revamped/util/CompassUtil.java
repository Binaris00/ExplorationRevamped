package binaris.exploration_revamped.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public final class CompassUtil {
    private CompassUtil() {
    }

    public static BlockPos getSavedPos(ItemStack stack) {
        NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        if(nbt.contains("structure_pos_key")){
            return BlockPos.fromLong(nbt.getLong("structure_pos_key"));
        }

        return null;
    }
}
