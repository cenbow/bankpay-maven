package common.plugins.factory;

import java.io.IOException;

import common.config.data.PluginMapperData;
import common.config.resolver.PluginDeployResolver;
import common.config.resolver.PluginMappingResolver;
import common.constants.CommonConstants;
import common.exception.PluginException;
import common.logger.PluginLogger;
import common.plugins.Plugin;
import common.utils.PropertiesUtil;

public class PluginFactory {

	private static final PluginLogger log = PluginLogger.getLogger(PluginFactory.class);

	private static PluginFactory factory;

	private PluginFactory() {
	}

	public static PluginFactory getInstance() {
		if (null == factory) {
			factory = new PluginFactory();
		}
		return factory;
	}

	public Plugin getPlugin(String pluginName) throws PluginException {
		final String methodName = "getPlugin";
		log.entering(methodName);

		Plugin plugin;
		try {
			String configPath = PropertiesUtil.resolve(CommonConstants.PROPERTIES_PATH_FILE).getProperty(
					CommonConstants.CONFIG_DIRECTORY);

			PluginMappingResolver mappResolver = new PluginMappingResolver();
			mappResolver.resolve(configPath);
			PluginMapperData mapper = mappResolver.getMappers().get(pluginName);

			PluginDeployResolver resolver = new PluginDeployResolver();
			resolver.resolve(configPath, mapper);
			String className = resolver.getPluginClassName();

			plugin = getPluginInstance(className);
			plugin.setProperties(resolver.getProperties());
		} catch (IOException e) {
			log.error(methodName, e);
			throw new PluginException(e);
		}

		log.exiting(methodName);
		return plugin;
	}

	private Plugin getPluginInstance(String pluginClassName) throws PluginException {
		try {
			Class<?> clazz = Class.forName(pluginClassName);
			Plugin payPlugin = (Plugin) clazz.newInstance();
			return payPlugin;
		} catch (Exception e) {
			throw new PluginException(e);
		}
	}

}
