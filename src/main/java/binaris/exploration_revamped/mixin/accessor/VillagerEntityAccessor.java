package binaris.exploration_revamped.mixin.accessor;

import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VillagerEntity.class)
public interface VillagerEntityAccessor {
    @Accessor("experience")
    int getExperience();

    @Accessor("experience")
    void setExperience(int experience);
}
