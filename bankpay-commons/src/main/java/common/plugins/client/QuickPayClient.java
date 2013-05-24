package common.plugins.client;

import common.logger.PluginLogger;
import common.plugins.dto.QuickPayRequestData;
import common.plugins.dto.ResultData;
import common.plugins.facade.QuickPayFacade;

public class QuickPayClient {

	private static final PluginLogger log = PluginLogger.getLogger(QuickPayClient.class);

	private QuickPayRequestData requestData;

	public QuickPayClient() {
		requestData = new QuickPayRequestData();
	}

	public QuickPayClient(String pluginName) {
		requestData = new QuickPayRequestData();
		requestData.setPluginName(pluginName);
	}

	public ResultData checkOrder(QuickPayRequestData request) {
		final String methodName = "checkOrder";
		log.entering(methodName);

		QuickPayFacade facade = new QuickPayFacade();
		ResultData result = facade.checkOrder(request);

		log.exiting(methodName);
		return result;
	}

	public QuickPayRequestData getRequestData() {
		return requestData;
	}

	public void setRequestData(QuickPayRequestData requestData) {
		this.requestData = requestData;
	}

}
