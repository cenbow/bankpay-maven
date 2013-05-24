package common.config.resolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.logger.PluginLogger;

public class HttpConnectionResolver {

	private static final PluginLogger log = PluginLogger.getLogger(HttpConnectionResolver.class);

	private static final String TAG_CONFIG = "Config";

	private static final String TAG_ATTR_NAME = "name";

	private static final String TAG_ATTR_VALUE = "value";

	private Map<String, String> properties = new HashMap<String, String>();

	public void resolver(String filePath, String fileName) throws ResolverException {
		final String methodName = "resolve";
		log.entering(methodName);

		try {
			File file = new File(filePath + fileName);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			NodeList nodes = document.getElementsByTagName(TAG_CONFIG);

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
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

}
