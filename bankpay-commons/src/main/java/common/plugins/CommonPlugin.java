package common.plugins;

import common.plugins.dto.RequestData;

public interface CommonPlugin extends Plugin {

	Integer checkOrder(RequestData parameters);

}
