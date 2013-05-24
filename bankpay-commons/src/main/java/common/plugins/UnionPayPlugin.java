package common.plugins;

import common.plugins.dto.UnionPayRequestData;

public interface UnionPayPlugin extends CommonPlugin {

	public Integer checkOrder(UnionPayRequestData request);

}
