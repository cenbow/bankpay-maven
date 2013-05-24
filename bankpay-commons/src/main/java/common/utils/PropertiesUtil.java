package common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties resolve(String fileName) throws IOException {
		return resolve("", fileName);
	}

	public static Properties resolve(String filePath, String fileName) throws IOException {
		String fullName = filePath + fileName;
		InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(fullName);
		Properties properties = new Properties();
		properties.load(input);
		return properties;
	}

}
