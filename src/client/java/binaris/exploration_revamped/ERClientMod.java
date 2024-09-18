package binaris.exploration_revamped;

import binaris.exploration_revamped.registries.EntityRegistries;
import binaris.exploration_revamped.renderer.IronBoatRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;

public class ERClientMod implements ClientModInitializer {

	public static float MULTIPLIER = .1f;

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityRegistries.IRON_BOAT_ENTITY, IronBoatRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(IronBoatRenderer.LOCATION, BoatEntityModel::getTexturedModelData);
	}
}