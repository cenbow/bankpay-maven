package common.config.data;

import java.io.Serializable;

public class PluginMapperData implements Serializable {

	private static final long serialVersionUID = -5034645134922275720L;

	private String pluginName;
	private String pluginGroup;
	private String catagory;

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

}
