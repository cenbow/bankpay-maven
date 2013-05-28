package plugins.unionpay;

import plugins.CommonPlugin;
import data.dto.UnionPayRequestData;

public interface UnionPayPlugin extends CommonPlugin {

	public Integer checkOrder(UnionPayRequestData request);

}
