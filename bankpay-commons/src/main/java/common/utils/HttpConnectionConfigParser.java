package common.utils;

/*
 *-----------------------------------------------------------------
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * WebSphere Commerce
 *
 * (C) Copyright IBM Corp. 2011
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 *-----------------------------------------------------------------
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.constants.CommonConstants;
import common.exception.PluginException;

/**
 * @author qizhen.lu
 * 
 *         Created on Dec 31, 2010
 */
public class HttpConnectionConfigParser {
	public static final String CLASS_NAME = HttpConnectionConfigParser.class.getName();

	/** The Tag of configs name. */
	private static final String TAG_CONFIGS_NAME = "Config";

	/** The tag of config name. */
	private static final String TAG_ATTR_NAME = "name";

	/** The tag of config value. */
	private static final String TAG_VALUE_NAME = "value";

	/**
	 * Load http connection configs from xml files.
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Map<String, String> loadConfigs() throws PluginException {
		Map<String, String> configs = null;
		try {
			String configPath = PropertiesUtil.resolve(CommonConstants.PROPERTIES_PATH_FILE).getProperty(
					CommonConstants.CONFIG_DIRECTORY);
			File f = new File(configPath + CommonConstants.HTTP_CONNECTION_CONFIGS_FILE);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName(TAG_CONFIGS_NAME);
			configs = new HashMap<String, String>();
			for (int i = 0; i < nl.getLength(); i++) {
				String name = nl.item(i).getAttributes().getNamedItem(TAG_ATTR_NAME).getNodeValue();
				String value = nl.item(i).getAttributes().getNamedItem(TAG_VALUE_NAME).getNodeValue();
				configs.put(name, value);
			}
		} catch (IOException e) {
			throw new PluginException(e);
		} catch (ParserConfigurationException e) {
			throw new PluginException(e);
		} catch (SAXException e) {
			throw new PluginException(e);
		}
		return configs;
	}
	
}
