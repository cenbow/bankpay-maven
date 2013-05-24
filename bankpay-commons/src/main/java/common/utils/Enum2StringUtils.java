package common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import common.exception.PluginException;
import common.plugins.keys.EnumKeys;

/**
 * 
 * <b>枚举字段转换字符串工具类</b><BR>
 * <ul>
 * <li>formateEnum: 格式化处理枚举</li>
 * <li>parseString: 解析字符串为map</li>
 * <li>formateEnumBytes: 按照字节长度计算格式化处理枚举, 字符串后补空格</li>
 * <li>formateEnumBytesPadLeft: 按照字节长度计算格式化处理枚举, 字符串前补空格</li>
 * <li>parseByBytes: 按照字节长度计算解析字符串为map</li>
 * </ul>
 * 
 * @author mupeng
 * @see EnumKeys
 * @since 10.1
 */
public class Enum2StringUtils {

	/**
	 * int类型是32位 <Br>
	 * byte是8位 <Br>
	 * 补码是有符号数，所以从8位变为int需要有符号扩展，变为11111111111111111111111111111111（最终的值为-1）。
	 * 至于0xff
	 * ，这属于java的字面常量，他已经是int了，ff表示为11111111，java对这种字面常量，不把他前面的1看做符号位，虽然也是有符号扩展
	 * ，但是，扩展成德是00...ff.
	 * 
	 * 一般在有些编译器重，写ff，会把第一位1认为是符号位，所以可以这么写：0x0ff
	 */
	private static final int COMPLEMENT_CHAR = 0XFF;

	private static final int SPACE_CHAR = 32;

	/**
	 * 
	 * 功能描述: <br>
	 * 传入map中的key是实现了EnumKeys的枚举<br>
	 * 此方法将map中的值，按照枚举类中的length格式化为形如： %-4s%-8s%4d%-20s<br>
	 * 就是字符串是后补空格，数字是前补0<BR>
	 * <B>金额类型 声明为BigDecimal， 长度定义为如：10.2， 2位小数</B>
	 * 
	 * @param map
	 *            格式化字段列表
	 * @return
	 */
	public static String formateEnum(Map<? extends EnumKeys, Object> map) {
		Formatter fm = new Formatter();
		if (map.isEmpty()) {
			return "";
		}

		EnumKeys[] allEnums = null;

		// 获取所有枚举值
		if (null != map && map.entrySet().size() > 0) {
			allEnums = map.entrySet().iterator().next().getKey().getClass().getEnumConstants();
		}

		// 按序循环枚举值， 按照对应类型进行格式化
		for (EnumKeys item : allEnums) {
			String fms = "%";

			if (BigDecimal.class.isAssignableFrom(item.getType())) {
				// 枚举字段是带小数类型
				formatDecimal(map.get(item).toString(), fm, item);
			} else if (Number.class.isAssignableFrom(item.getType())) {
				// 其他数字类型
				fms += "0" + item.getLength().intValue() + "d";
				fm.format(fms, (Number) map.get(item));
			} else {
				// 字符串
				fms += "-" + item.getLength().intValue() + "s";
				String var = null == map.get(item) ? "" : (String) map.get(item);
				fm.format(fms, var);
			}
		}

		return fm.toString();
	}

	/**
	 * 
	 * 功能描述: <br>
	 * formateEnum方法的逆转换，就是将已经格式化的字符串分解到map中
	 * 
	 * @param enumType
	 *            解析成的字段枚举类
	 * @param resp
	 * @return
	 */
	public static Map<EnumKeys, Object> parseString(Class<? extends EnumKeys> enumType, String resp) {
		Map<EnumKeys, Object> result = new HashMap<EnumKeys, Object>();

		if (null == resp || "".equals(resp)) {
			return result;
		}

		EnumKeys[] all = enumType.getEnumConstants();
		int pos = 0;
		String var = "";
		int len = 0;
		for (EnumKeys item : all) {
			// 字符串占位长度
			len = (int) Math.floor(item.getLength());
			var = resp.substring(pos, pos + len);

			pos = pos + len;
			result.put(item, var.trim());
		}

		return result;
	}

	/**
	 * 功能描述: <br>
	 * 格式化金额类型,带小数位
	 * 
	 * @param value
	 *            待格式化值
	 * @param fm
	 *            格式化模板
	 * @param item
	 */
	private static void formatDecimal(String value, Formatter fm, EnumKeys item) {
		// 金额类型
		BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(value));
		// 保留2位小数 四舍五入
		BigDecimal bs = bd == null ? BigDecimal.ZERO : bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		String fms = "%0" + item.getLength() + "f";
		fm.format(fms, bs);
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 按照字节长度计算格式化枚举字符串
	 * 
	 * @see {@link Enum2StringUtils#formateEnum(Map)}; 逆转换：
	 *      {@link Enum2StringUtils#parseByBytes(Class, String, String)}
	 * @param map
	 *            待处理map
	 * @param charset
	 *            编码类型
	 * @return 格式化结果
	 * @throws PluginException
	 */
	public static String formateEnumBytes(Map<? extends EnumKeys, Object> map, String charset) throws PluginException {
		return formateEnumBytes(map, charset, false);
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 同formateEnumBytes 字符串左补空格
	 * 
	 * @see Enum2StringUtils#formateEnumBytes(Map, String)
	 * @param map
	 * @param charset
	 * @return
	 * @throws PluginException
	 */
	public static String formateEnumBytesPadLeft(Map<? extends EnumKeys, Object> map, String charset)
			throws PluginException {
		return formateEnumBytes(map, charset, true);
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 按照字节长度计算格式化枚举字符串
	 * 
	 * @see {@link Enum2StringUtils#formateEnum(Map)}; 逆转换：
	 *      {@link Enum2StringUtils#parseByBytes(Class, String, String)}
	 * @param map
	 *            待处理map
	 * @param charset
	 *            编码类型
	 * @param isLeft
	 *            是否左补空格
	 * @return
	 * @throws PluginException
	 */
	private static String formateEnumBytes(Map<? extends EnumKeys, Object> map, String charset, boolean isLeft)
			throws PluginException {
		Formatter fm = new Formatter();
		if (map.isEmpty()) {
			return "";
		}

		EnumKeys[] allEnums = null;
		// 获取所有枚举值
		if (null != map && map.entrySet().size() > 0) {
			allEnums = map.entrySet().iterator().next().getKey().getClass().getEnumConstants();
		}

		// 按序循环枚举值， 按照对应类型进行格式化
		for (EnumKeys item : allEnums) {
			String fms = "%";
			if (BigDecimal.class.isAssignableFrom(item.getType())) {
				BigDecimal bd = null == map.get(item) ? BigDecimal.valueOf(0) : (BigDecimal) map.get(item);
				// 保留2位小数 四舍五入
				BigDecimal bs = bd == null ? BigDecimal.ZERO : bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				fms = "%0" + item.getLength() + "f";
				fm.format(fms, bs);
			} else if (Number.class.isAssignableFrom(item.getType())) {
				// 其他数字类型
				fms += "0" + item.getLength().intValue() + "d";
				fm.format(fms, (Number) map.get(item));
			} else {
				fms += "1s";
				String var = null == map.get(item) ? "" : (String) map.get(item);
				fm.format(fms, padSpace(var, charset, item.getLength().intValue(), isLeft));
			}
		}

		return fm.toString();
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 字符串按照字节长度右补空格
	 * 
	 * @param s
	 * @param charset
	 *            编码
	 * @param length
	 *            总字节长
	 * @param isLeft
	 *            是否左补
	 * @return
	 * @throws PluginException
	 * @throws UnsupportedEncodingException
	 */
	private static String padSpace(String s, String charset, int length, boolean isLeft) throws PluginException {
		int len = s.length() > length ? s.length() : length;
		byte[] bs = new byte[len];
		byte[] ss;
		try {
			ss = s.getBytes(charset);
			Arrays.fill(bs, (byte) (SPACE_CHAR & COMPLEMENT_CHAR));
			int start = 0;
			// 是否需要左补空格
			if (isLeft) {
				start = len - ss.length;
			}
			System.arraycopy(ss, 0, bs, start, ss.length);
			return new String(bs, charset);
		} catch (UnsupportedEncodingException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 按照编码类型的字节进行解析分割字符串
	 * 
	 * @see Enum2StringUtils#formateEnumBytes(Map, String)
	 * @param enumType
	 * @param resp
	 * @param charset
	 *            编码
	 * @return
	 * @throws PluginException
	 */
	public static Map<EnumKeys, Object> parseByBytes(Class<? extends EnumKeys> enumType, String resp, String charset)
			throws PluginException {
		Map<EnumKeys, Object> result = new HashMap<EnumKeys, Object>();

		if (null == resp || "".equals(resp)) {
			return result;
		}

		EnumKeys[] all = enumType.getEnumConstants();
		int pos = 0;
		String var = "";
		int len = 0;
		try {
			byte[] srcBytes = resp.getBytes(charset);
			byte[] tmpBytes = null;
			// 按序循环枚举，按照枚举的声明长度从srcBytes截取字节数组转换为对应编码的字符串
			for (EnumKeys item : all) {
				// 字符串占位长度
				len = (int) Math.floor(item.getLength());
				tmpBytes = new byte[len];
				// 从srcBytes中在pos位置截取len长度的字节到tmpBytes
				System.arraycopy(srcBytes, pos, tmpBytes, 0, len);
				var = new String(tmpBytes, charset);
				pos = pos + len;
				result.put(item, var.trim());
			}
		} catch (UnsupportedEncodingException e) {
			throw new PluginException(e);
		}

		return result;
	}
}
