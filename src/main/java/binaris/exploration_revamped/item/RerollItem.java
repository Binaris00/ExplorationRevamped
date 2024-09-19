package binaris.exploration_revamped.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RerollItem extends Item {

    public RerollItem() {
        super(new Settings().maxCount(16));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.exploration_revamped.reroll.tooltip").formatted(Formatting.GRAY));
    }
}
