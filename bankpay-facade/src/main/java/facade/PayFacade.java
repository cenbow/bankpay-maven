package facade;

import plugins.CommonPlugin;

import common.logger.PluginLogger;
import common.plugins.factory.PluginFactory;

import data.dto.RequestData;
import data.dto.ResultData;

public class PayFacade {

	private static final PluginLogger log = PluginLogger.getLogger(PayFacade.class);

	public ResultData checkOrder(RequestData request) {
		final String methodName = "checkOrder";
		log.entering(methodName);

		ResultData result;
		String pluginName = request.getPluginName();

		CommonPlugin plugin = (CommonPlugin) PluginFactory.getInstance().getPlugin(pluginName);
		Integer status = plugin.checkOrder(request);
		result = new ResultData();
		result.setSuccess(Boolean.valueOf(true));
		result.setResultObject(status);
		result.setResponse(plugin.getResponse());

		log.exiting(methodName);
		return result;
	}

}
