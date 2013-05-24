package common.plugins;

import common.exception.PluginException;
import common.plugins.dto.QuickPayRequestData;

public interface QuickPayPlugin extends CommonPlugin {

	public Integer checkOrder(QuickPayRequestData request) throws PluginException;

	public Integer refundOrder(QuickPayRequestData request) throws PluginException;

}
