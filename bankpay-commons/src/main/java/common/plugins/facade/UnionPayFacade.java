package common.plugins.facade;

import common.logger.PluginLogger;
import common.plugins.UnionPayPlugin;
import common.plugins.dto.ResultData;
import common.plugins.dto.UnionPayRequestData;
import common.plugins.factory.PluginFactory;

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
