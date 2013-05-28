package facade;

import plugins.quickpay.QuickPayPlugin;

import common.logger.PluginLogger;
import common.plugins.factory.PluginFactory;

import data.dto.QuickPayRequestData;
import data.dto.ResultData;

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
