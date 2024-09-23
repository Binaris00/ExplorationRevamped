package binaris.exploration_revamped;

import binaris.exploration_revamped.registries.EntityRegistries;
import binaris.exploration_revamped.registries.ItemRegistries;
import binaris.exploration_revamped.renderer.IronBoatRenderer;
import binaris.exploration_revamped.util.CompassUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public class ERClientMod implements ClientModInitializer {

	public static float MULTIPLIER = .1f;

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityRegistries.IRON_BOAT_ENTITY, IronBoatRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(IronBoatRenderer.LOCATION, BoatEntityModel::getTexturedModelData);

		registerCompassModel(ItemRegistries.SCULK_COMPASS_ITEM);
		registerCompassModel(ItemRegistries.COPPER_COMPASS_ITEM);
		registerCompassModel(ItemRegistries.LOG_COMPASS_ITEM);
	}

	private void registerCompassModel(Item compassItem) {
		ModelPredicateProviderRegistry.register(compassItem, Identifier.of("angle"),
				new ClampedModelPredicateProvider() {
					private double rotation;
					private double rota;
					private long lastUpdateTick;

					@Override
					public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
						if (entity == null && !stack.hasGlint()) {
							return 0.0F;
						} else {
							boolean entityExists = entity != null;
							Entity entityRef = entityExists ? entity : stack.getFrame();
							if (world == null && entityRef.getWorld() instanceof ClientWorld) {
								world = (ClientWorld) entityRef.getWorld();
							}

							double rotationValue;
							BlockPos globalPos = CompassUtil.getSavedPos(stack);
							if (globalPos != null) {
								double entityRotation = entityExists ? entity.getYaw() : getFrameRotation((ItemFrameEntity) entityRef);
								entityRotation = MathHelper.floorMod(entityRotation / 360.0D, 1.0D);
								double angle = getSpawnToAngle(entityRef, globalPos) / (double) (Math.PI * 2F);
								rotationValue = 0.5D - (entityRotation - 0.25D - angle);
							} else {
								rotationValue = Math.random();
							}

							if (entityExists) {
								rotationValue = wobble(world, rotationValue);
							}

							return MathHelper.floorMod((float) rotationValue, 1.0F);
						}
					}

					private double wobble(ClientWorld world, double angle) {
						if (world.getTime() != this.lastUpdateTick) {
							this.lastUpdateTick = world.getTime();
							double diff = angle - this.rotation;
							diff = MathHelper.floorMod(diff + 0.5D, 1.0D) - 0.5D;
							this.rota += diff * 0.1D;
							this.rota *= 0.8D;
							this.rotation = MathHelper.floorMod(this.rotation + this.rota, 1.0D);
						}
						return this.rotation;
					}

					private double getFrameRotation(ItemFrameEntity itemFrame) {
						Direction direction = itemFrame.getHorizontalFacing();
						int offset = direction.getAxis().isVertical() ? 90 * direction.getDirection().offset() : 0;
						return MathHelper.wrapDegrees(180 + direction.getHorizontal() * 90 + itemFrame.getRotation() * 45 + offset);
					}

					private double getSpawnToAngle(Entity entity, BlockPos pos) {
						return Math.atan2(pos.getZ() - entity.getZ(), pos.getX() - entity.getX());
					}
				}
		);
	}
}
