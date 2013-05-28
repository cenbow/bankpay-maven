package facade;

import plugins.unionpay.UnionPayPlugin;

import common.logger.PluginLogger;
import common.plugins.factory.PluginFactory;

import data.dto.ResultData;
import data.dto.UnionPayRequestData;

public class UnionPayFacade {

	private static final PluginLogger log = PluginLogger.getLogger(UnionPayFacade.class);

	public ResultData checkOrder(UnionPayRequestData request) {
		final String methodName = "checkOrder";
		log.entering(methodName);
		UnionPayPlugin plugin = (UnionPayPlugin) PluginFactory.getInstance().getPlugin(request.getPluginName());
		Integer result = plugin.checkOrder(request);
		ResultData resultData = new ResultData();
		resultData.setSuccess(true);
		resultData.setResultObject(result);
		log.exiting(methodName);
		return resultData;
	}

}
