package plugins;

import java.util.Map;

import common.constants.CommonConstants;
import common.plugins.Plugin;

import data.dto.RequestData;

public abstract class AbstractPlugin implements Plugin {

	private String pluginName;

	private String pluginGroup;

	private String catagory;

	private String rootPath;

	private Map<String, String> properties;

	private Map<String, Object> response;

	public Integer checkOrder(RequestData request) {
		return null;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginGroup() {
		return pluginGroup;
	}

	public void setPluginGroup(String pluginGroup) {
		this.pluginGroup = pluginGroup;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

	/**
	 * 
	 * 保存输出结果
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void setResponseProperty(String key, Object value) {
		this.response.put(key, value);
	}

	/**
	 * 设置错误/异常消息<BR>
	 * 
	 * 
	 * @param errorMsg
	 */
	public void setBankErrorMsg(String respMsg) {
		setResponseProperty(CommonConstants.RESPONSE_BANK_ERROR_MSG, respMsg);
	}

	/**
	 * 设置errorcode
	 * 
	 * @param errorCode
	 */
	public void setBankErrorCode(String errorCode) {
		setResponseProperty(CommonConstants.RESPONSE_BANK_ERROR_CODE, errorCode);
	}

}
