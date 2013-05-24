package common.plugins.facade;

import common.logger.PluginLogger;
import common.plugins.QuickPayPlugin;
import common.plugins.dto.QuickPayRequestData;
import common.plugins.dto.ResultData;
import common.plugins.factory.PluginFactory;

public class QuickPayFacade {

	private static final PluginLogger log = PluginLogger.getLogger(QuickPayFacade.class);

	public ResultData checkOrder(QuickPayRequestData request) {

		final String methodName = "checkOrder";
		log.entering(methodName);

		String pluginName = request.getPluginName();
		QuickPayPlugin plugin = (QuickPayPlugin) PluginFactory.getInstance().getPlugin(pluginName);
		Integer status = plugin.checkOrder(request);
		ResultData result = new ResultData();
		result.setSuccess(Boolean.valueOf(true));
		result.setResultObject(status);
		result.setResponse(plugin.getResponse());

		log.exiting(methodName);
		return result;
	}

}
