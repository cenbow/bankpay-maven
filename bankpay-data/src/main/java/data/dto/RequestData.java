package data.dto;

import java.io.Serializable;

/**
 * 统一的请求数据，暂时没有用到
 * 
 * @author yanbin
 * 
 */
public class RequestData implements Serializable {

	private static final long serialVersionUID = -100781999980303665L;

	private String pluginName;

	private String signNo;

	private String orderNo;

	private String orderTime;

	public RequestData() {
	}

	public RequestData(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

}
