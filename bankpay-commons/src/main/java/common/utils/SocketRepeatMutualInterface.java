package common.utils;

import java.util.Map;

import common.exception.PluginException;

/**
 * @see socket 多次交付实现接口类.
 * 
 * @author jack_jiang
 * @since 2013-04-27
 * @version 1.0
 */
public interface SocketRepeatMutualInterface {

	/**
	 * @see 传入socket的返回信息 处理完成后. 返回Map<Sting,String>
	 * @see 此Map 只需定义两个Key值.
	 * @see key1 = sendMsg 需要发送给server段处理过的信息.
	 * @see key2 = endMark 结束标志. 可先约定好 value = "quitSocket" (最后一次不能为空).
	 * 
	 * @param socketMsg
	 * @return
	 */
	public Map<String, String> dealSocketMessage(String socketMsg) throws PluginException;

}
