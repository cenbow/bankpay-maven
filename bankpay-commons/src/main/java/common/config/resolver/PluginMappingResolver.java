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

public class PluginMappingResolver {

	private PluginLogger log = PluginLogger.getLogger(PluginMappingResolver.class);

	/** 配置文件名 */
	private static final String PLUGIN_MAPPING_CONFIG = "PluginMapping.xml";
	/** 节点名：Mapping */
	private static final String TAG_MAPPING = "Mapping";
	/** 属性名：pluginGroup */
	private static final String TAG_ATTR_PLUGINGROUP = "pluginGroup";
	/** 属性名：catagory */
	private static final String TAG_ATTR_CATAGORY = "catagory";
	/** 属性名：pluginName */
	private static final String TAG_ATTR_PLUGINNAME = "pluginName";

	private Map<String, PluginMapperData> mappers = new HashMap<String, PluginMapperData>();

	public void resolve(String configPath) throws ResolverException {
		final String methodName = "resolve";
		log.entering(methodName);

		try {
			File file = new File(configPath + PLUGIN_MAPPING_CONFIG);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			NodeList nodes = document.getElementsByTagName(TAG_MAPPING);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				String pluginName = node.getAttributes().getNamedItem(TAG_ATTR_PLUGINNAME).getNodeValue();
				String pluginGroup = node.getAttributes().getNamedItem(TAG_ATTR_PLUGINGROUP).getNodeValue();
				String catagory = node.getAttributes().getNamedItem(TAG_ATTR_CATAGORY).getNodeValue();
				PluginMapperData mapper = new PluginMapperData();
				mapper.setPluginName(pluginName);
				mapper.setPluginGroup(pluginGroup);
				mapper.setCatagory(catagory);
				mappers.put(pluginName, mapper);
			}
		} catch (Exception e) {
			log.error(methodName, e);
			throw new ResolverException(e);
		}

		log.exiting(methodName);
	}

	public Map<String, PluginMapperData> getMappers() {
		return mappers;
	}

	public void setMappers(Map<String, PluginMapperData> mappers) {
		this.mappers = mappers;
	}

}
