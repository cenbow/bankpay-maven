package common.plugins.client;

import common.logger.PluginLogger;
import common.plugins.dto.ResultData;
import common.plugins.dto.UnionPayRequestData;
import common.plugins.facade.UnionPayFacade;

public class UnionPayClient {

	private static final PluginLogger log = PluginLogger.getLogger(QuickPayClient.class);

	private UnionPayRequestData requestData;

	public UnionPayClient() {
		requestData = new UnionPayRequestData();
	}

	public UnionPayClient(String pluginName) {
		requestData = new UnionPayRequestData();
		requestData.setPluginName(pluginName);
	}

	public ResultData checkOrder(UnionPayRequestData request) {
		final String methodName = "checkOrder";
		log.entering(methodName);

		UnionPayFacade facade = new UnionPayFacade();
		ResultData result = facade.checkOrder(request);

		log.exiting(methodName);
		return result;
	}

	public UnionPayRequestData getRequestData() {
		return requestData;
	}

	public void setRequestData(UnionPayRequestData requestData) {
		this.requestData = requestData;
	}

}
