package common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlConvertUtil {

	/**
	 * 利用注解，将注解的对象转换成String
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToXml(Object obj) {
		XStream xstream = new XStream();
		xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
		String xml = xstream.toXML(obj);
		return xml;
	}

	/**
	 * 将xml的值对应转换成object
	 * 
	 * @param xml
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToObj(String xml, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(clazz);
		Object obj = xStream.fromXML(xml);
		return (T) obj;
	}

	/**
	 * 将object转成xml的文件
	 * 
	 * @param obj
	 * @param filePath
	 * @param fileName
	 * @param charset
	 * @throws IOException
	 */
	public static void objToXmlFile(Object obj, String filePath, String fileName) throws IOException {
		String xmlStr = objToXml(obj);
		File file = new File(filePath + fileName);
		OutputStream out = null;

		try {
			// 如果文件不存在，先创建文件
			if (!file.exists()) {
				file.createNewFile();
			}
			// 写入内容
			out = new FileOutputStream(file);
			byte[] b = xmlStr.getBytes();
			out.write(b);
			out.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * 将xml文件读入内存，并转换成object
	 * 
	 * @param filePath
	 * @param fileName
	 * @param clazz
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlFileToObj(String filePath, String fileName, Class<T> clazz) throws IOException {
		File file = new File(filePath + fileName);

		InputStream is = null;
		Object obj = null;
		try {
			is = new FileInputStream(file);
			XStream xstream = new XStream(new DomDriver());
			xstream.processAnnotations(clazz);
			obj = xstream.fromXML(is);
		} catch (IOException e) {
			throw e;
		} finally {
			is.close();
		}
		return (T) obj;
	}

}
