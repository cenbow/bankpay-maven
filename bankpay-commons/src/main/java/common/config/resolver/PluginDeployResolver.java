package common.config.resolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.config.data.PluginMapperData;
import common.logger.PluginLogger;

/**
 * 解析插件依赖的配置属性的文件
 * 
 * @author yanbin
 * 
 */
public class PluginDeployResolver {

	private static final PluginLogger log = PluginLogger.getLogger(PluginDeployResolver.class);

	/** 组建链接的分隔符 */
	private static final String SEPERATOR = "/";
	/** XML为后缀 */
	private static final String XML_SUFFIX = ".xml";
	private static final String DEPLOY = "deploy";
	/** 节点名称：PluginInformation */
	private static final String TAG_PLUGININFORMATION = "PluginInformation";
	/** 节点名称：PluginProperty */
	private static final String TAG_PLUGINPROPERTY = "PluginProperty";

	/** 节点属性名：class */
	private static final String TAG_ATTR_CLASS = "class";
	/** 节点属性名：name */
	private static final String TAG_ATTR_NAME = "name";
	/** 节点属性名：value */
	private static final String TAG_ATTR_VALUE = "value";

	private String pluginClassName;

	private Map<String, String> properties = new HashMap<String, String>();

	/**
	 * 解析配置文件
	 * 
	 * @param fileName
	 *            文件名称
	 */
	public void resolve(String configPath, PluginMapperData mapper) throws ResolverException {
		final String methodName = "resolve";
		log.entering(methodName);

		try {
			File file = new File(configPath + SEPERATOR + DEPLOY + SEPERATOR + mapper.getPluginGroup() + SEPERATOR
					+ mapper.getPluginName() + XML_SUFFIX);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			NodeList nodes = document.getElementsByTagName(TAG_PLUGININFORMATION);
			pluginClassName = nodes.item(0).getAttributes().getNamedItem(TAG_ATTR_CLASS).getNodeValue();

			NodeList propertiesNodes = document.getElementsByTagName(TAG_PLUGINPROPERTY);

			for (int i = 0; i < propertiesNodes.getLength(); i++) {
				Node node = propertiesNodes.item(i);
				String name = node.getAttributes().getNamedItem(TAG_ATTR_NAME).getNodeValue();
				String value = node.getAttributes().getNamedItem(TAG_ATTR_VALUE).getNodeValue();
				properties.put(name, value);
			}
		} catch (Exception e) {
			log.error(methodName, e);
			throw new ResolverException(e);
		}

		log.exiting(methodName);
	}

	public String getPluginClassName() {
		return pluginClassName;
	}

	public void setPluginClassName(String pluginClassName) {
		this.pluginClassName = pluginClassName;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
