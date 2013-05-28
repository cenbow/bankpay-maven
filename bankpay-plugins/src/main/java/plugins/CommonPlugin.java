package plugins;

import common.plugins.Plugin;

import data.dto.RequestData;

public interface CommonPlugin extends Plugin {

	Integer checkOrder(RequestData parameters);

}
