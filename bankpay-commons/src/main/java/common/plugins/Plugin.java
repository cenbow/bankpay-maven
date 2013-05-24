package common.plugins;

import java.util.Map;

public interface Plugin {

	String getPluginName();

	void setPluginName(String pluginName);

	String getPluginGroup();

	void setPluginGroup(String pluginGroup);

	String getCatagory();

	void setCatagory(String catagory);

	String getRootPath();

	void setRootPath(String rootPath);

	Map<String, String> getProperties();

	void setProperties(Map<String, String> properties);

	Map<String, Object> getResponse();

	void setResponse(Map<String, Object> response);

}
