package binaris.exploration_revamped.item;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;

public class IronBoatItem extends BoatItem {
    public static final int DURABILITY_DEFAULT = 24000; // 20 minutes - 24000 ticks

    public IronBoatItem() {
        super(false, BoatEntity.Type.OAK, new Settings().maxCount(1).maxDamage(DURABILITY_DEFAULT));
    }
}
