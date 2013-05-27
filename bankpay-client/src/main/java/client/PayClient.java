package client;

import common.logger.PluginLogger;
import common.plugins.dto.RequestData;
import common.plugins.dto.ResultData;
import common.plugins.facade.PayFacade;


public class PayClient {

	private static final PluginLogger log = PluginLogger.getLogger(PayClient.class);

	private RequestData requestData;

	private String pluginName;

	public PayClient() {
		requestData = new RequestData();
	}

	public PayClient(String pluginName) {
		this.pluginName = pluginName;
		requestData = new RequestData();
	}

	public ResultData checkOrder(RequestData request) {
		final String methodName = "checkOrder";
		log.entering(methodName);

		PayFacade facade = new PayFacade();
		ResultData result = facade.checkOrder(request);

		log.exiting(methodName);
		return result;
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

}
