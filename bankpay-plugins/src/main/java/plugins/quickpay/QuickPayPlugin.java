package plugins.quickpay;

import plugins.CommonPlugin;

import common.exception.PluginException;

import data.dto.QuickPayRequestData;

public interface QuickPayPlugin extends CommonPlugin {

	public Integer checkOrder(QuickPayRequestData request) throws PluginException;

	public Integer refundOrder(QuickPayRequestData request) throws PluginException;

}
