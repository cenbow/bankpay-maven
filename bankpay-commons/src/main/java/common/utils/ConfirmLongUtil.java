package common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Formatter;

import common.plugins.annotation.ConfirmLongField;

public class ConfirmLongUtil {

	private static final int COMPLEMENT_CHAR = 0XFF;

	private static final int SPACE_CHAR = 32;

	/**
	 * 利用反射，根据注解的标示解析。给不明确的类私有变量进行赋值
	 * 
	 * @param clazz
	 * @param resp
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T format2Object(Class<T> clazz, String resp) throws InstantiationException,
			IllegalAccessException {
		T t = clazz.newInstance();

		if (null == resp || "".equals(resp)) {
			return t;
		}

		Field[] fields = clazz.getDeclaredFields();

		int pos = 0;
		int len = 0;
		for (Field field : fields) {
			// 用于取消字段访问控制，可以访问private的字段等
			field.setAccessible(true);
			ConfirmLongField confirmLong = field.getAnnotation(ConfirmLongField.class);
			len = Integer.valueOf(confirmLong.length());
			field.set(t, resp.substring(pos, len));
			pos += len;
		}
		return t;
	}

	/**
	 * 将对象按一定的格式，转换成string类型。<br>
	 * 字符串候补空格，数字前补0：%-4s%-8s%4d%-20s<br>
	 * 
	 * @param object
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String format2String(Object object) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = object.getClass().getDeclaredFields();
		// Formatter 类用于格式化字段
		Formatter formatter = new Formatter();
		int len = 0;
		for (Field field : fields) {
			// 用于取消字段访问控制，可以访问private的字段等
			field.setAccessible(true);
			ConfirmLongField confirmLong = field.getAnnotation(ConfirmLongField.class);
			len = Integer.valueOf(confirmLong.length());

			if (BigDecimal.class.isAssignableFrom(field.getType())) {
				BigDecimal bd = (BigDecimal) field.get(object);
				// BigDecimal的四舍五入
				bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				formatter.format("%0" + len + "f", bd);
			} else if (Number.class.isAssignableFrom(field.getType())) {
				// 以java指定的格式化字符串方式
				formatter.format("%0" + len + "d", field.get(object));
			} else {
				// 最后是字符串
				formatter.format("%-" + len + "s", field.get(object));
			}
		}
		return formatter.toString();
	}

	/**
	 * 重载方法，解决字节问题
	 * 
	 * @param clazz
	 * @param resp
	 * @param charset
	 * @return
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	public static <T> T format2ObjectByBytes(Class<T> clazz, String resp, String charset)
			throws IllegalArgumentException, UnsupportedEncodingException, IllegalAccessException,
			InstantiationException {
		T t = clazz.newInstance();

		if (null == resp || "".equals(resp)) {
			return t;
		}

		Field[] fields = clazz.getDeclaredFields();
		// 将字符串转换成字节
		byte[] srcBytes = resp.getBytes(charset);

		int pos = 0;
		int len = 0;
		byte[] bytes;
		for (Field field : fields) {
			// 用于取消字段访问控制，可以访问private的字段等
			field.setAccessible(true);
			ConfirmLongField confirmLong = field.getAnnotation(ConfirmLongField.class);
			len = Integer.valueOf(confirmLong.length());
			bytes = new byte[len];
			System.arraycopy(srcBytes, pos, bytes, 0, len);
			field.set(t, new String(bytes, charset));
			pos += len;
		}
		return t;
	}

	/**
	 * 利用字符集类型进行转换。通过字节数组转换。这样可以控制中文多字节的问题
	 * 
	 * @param object
	 * @param charset
	 *            字符集
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String format2StringByBytes(Object object, String charset) throws IllegalArgumentException,
			IllegalAccessException, UnsupportedEncodingException {
		Field[] fields = object.getClass().getDeclaredFields();
		// Formatter 类用于格式化字段
		Formatter formatter = new Formatter();
		int len = 0;
		for (Field field : fields) {
			// 用于取消字段访问控制，可以访问private的字段等
			field.setAccessible(true);
			ConfirmLongField confirmLong = field.getAnnotation(ConfirmLongField.class);
			len = Integer.valueOf(confirmLong.length());

			if (BigDecimal.class.isAssignableFrom(field.getType())) {
				BigDecimal bd = (BigDecimal) field.get(object);
				// BigDecimal的四舍五入
				bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				formatter.format("%0" + len + "f", bd);
			} else if (Number.class.isAssignableFrom(field.getType())) {
				// 以java指定的格式化字符串方式
				formatter.format("%0" + len + "d", field.get(object));
			} else {
				// 最后是字符串，需要将字符串以字符集的编码转成字节
				formatter.format("%1s", padBytesSpace(field.get(object).toString(), charset, len));
			}
		}
		return formatter.toString();
	}

	/**
	 * 将字符串通过编码进行补全空格
	 * 
	 * @param content
	 * @param charset
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String padBytesSpace(String content, String charset, int len) throws UnsupportedEncodingException {
		byte[] bytes = new byte[len];
		byte[] contentBytes = content.getBytes(charset);
		// 将bs的数组中全部填充为空格
		Arrays.fill(bytes, (byte) (SPACE_CHAR & COMPLEMENT_CHAR));
		// 将字符串转化的字节数组中的内容copy到指定的字符字节数组中
		System.arraycopy(contentBytes, 0, bytes, 0, contentBytes.length);
		return new String(bytes, charset);
	}

}
