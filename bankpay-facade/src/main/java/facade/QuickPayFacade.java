package facade;

import client.request.dto.QuickPayRequestData;

import common.logger.PluginLogger;
import common.plugins.QuickPayPlugin;
import common.plugins.factory.PluginFactory;
import dao.dto.ResultData;

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
