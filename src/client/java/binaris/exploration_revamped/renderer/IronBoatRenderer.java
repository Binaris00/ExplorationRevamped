package binaris.exploration_revamped.renderer;

import binaris.exploration_revamped.ERCommonMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

import java.util.Map;

public class IronBoatRenderer extends BoatEntityRenderer {
    public static final EntityModelLayer LOCATION = new EntityModelLayer(Identifier.of(ERCommonMod.MOD_ID, "iron_boat"), "main");
    private final Identifier texture;

    public IronBoatRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, false);
        this.texture = Identifier.of(ERCommonMod.MOD_ID, "textures/entity/iron_boat.png");
        this.texturesAndModels = Map.of(BoatEntity.Type.OAK, Pair.of(texture, new BoatEntityModel(ctx.getPart(LOCATION))));
    }


    public Identifier getTexture() {
        return texture;
    }
}
