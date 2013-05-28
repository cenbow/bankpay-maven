package plugins.unionpay.impl;

import plugins.AbstractPlugin;
import plugins.unionpay.UnionPayPlugin;

import common.exception.PluginException;
import common.logger.PluginLogger;

import data.dto.UnionPayRequestData;

public class ICBCUnionPayPlugin extends AbstractPlugin implements UnionPayPlugin {

	private static final PluginLogger log = PluginLogger.getLogger(ICBCUnionPayPlugin.class);

	public Integer checkOrder(UnionPayRequestData request) throws PluginException {
		final String methodName = "checkOrder";
		log.entering(methodName);

		System.out.println(getProperties().get("host"));
		System.out.println(getProperties().get("port"));

		System.out.println(request.getOrderId());

		log.exiting(methodName);
		return 1;
	}

}
