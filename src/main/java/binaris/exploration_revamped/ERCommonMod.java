package binaris.exploration_revamped;

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
	}

	public static void logDebug(String message) {
		if (DEBUG) {
			LOGGER.info("====== Exploration Revamped Debug: ======");
			LOGGER.warn(message);
			LOGGER.info("=========================================");
		}
	}
}