package binaris.exploration_revamped;

import binaris.exploration_revamped.registries.EntityRegistries;
import binaris.exploration_revamped.registries.ItemGroupRegistries;
import binaris.exploration_revamped.registries.ItemRegistries;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ERCommonMod implements ModInitializer {
	public static final String MOD_ID = "exploration_revamped";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Exploration Revamped");
		EREventsHandler.init();
		ItemRegistries.init();
		EntityRegistries.init();
		ItemGroupRegistries.init();
	}

	public static void logDebug(String message) {
		if (DEBUG) {
			LOGGER.info("====== Exploration Revamped Debug: ======");
			LOGGER.warn(message);
			LOGGER.info("=========================================");
		}
	}
}