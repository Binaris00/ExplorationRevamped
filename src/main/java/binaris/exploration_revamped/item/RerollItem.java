package binaris.exploration_revamped.item;

import net.minecraft.item.Item;

public class RerollItem extends Item {

    public RerollItem() {
        super(new Settings().maxCount(1));
    }
}
