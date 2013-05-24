package common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import common.exception.PluginException;

/**
 * Encryption and Decryption Utility.
 * 
 * @author qizhen.lu
 */
public class EncryptionDecryptionUtil {

	/** The charset : utf-8. */
	public static final String CHARSET_UTF8 = "UTF-8";

	/** The charset : gb2312. */
	public static final String CHARSET_GB2312 = "GB2312";

	/** The charset : gbk. */
	public static final String CHARSET_GBK = "GBK";

	/**
	 * 功能描述: <br>
	 * 对URL进行编码
	 * 
	 * @param content
	 * @param charset
	 * @return
	 * @throws PluginException
	 */
	public static String encodeByURL(String content, String charset) throws PluginException {
		String result = null;
		if (content != null) {
			try {
				result = URLEncoder.encode(content, charset);
			} catch (UnsupportedEncodingException e) {
				throw new PluginException(e);
			}
		}
		return result;
	}

	/**
	 * 功能描述: <br>
	 * 对编码后的URL进行解码
	 * 
	 * @param content
	 * @param charset
	 * @return
	 * @throws PluginException
	 */
	public static String decodeByURL(String content, String charset) throws PluginException {
		String result = null;
		if (content != null) {
			try {
				result = URLDecoder.decode(content, charset);
			} catch (UnsupportedEncodingException e) {
				throw new PluginException(e);
			}
		}
		return result;
	}

	/**
	 * 功能描述: <br>
	 * 使用MD5算法进行散列运算
	 * 
	 * @param content
	 * @param charset
	 * @return
	 * @throws EncryptionException
	 */
	public static String encryptByMD5(String content, String charset) throws PluginException {
		return encryptByHash(content, charset, "MD5");
	}

	/**
	 * 
	 * 功能描述: <br>
	 * MD5加密，并且将加密的返回字符串转换为大写
	 * 
	 * @param content
	 * @param charset
	 * @return
	 */
	public static String encryptByMD5ToUpCase(String content, String charset) throws PluginException {
		String mac = encryptByHash(content, charset, "MD5");
		if (null == mac) {
			return "";
		}
		return mac.toUpperCase();
	}

	/**
	 * 功能描述: <br>
	 * 字节数组转换为十六进制编码字符数组
	 * 
	 * @param content
	 * @return
	 */
	public static char[] encodeHex(byte[] content) {
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] out = new char[content.length << 1];
		int i = 0;
		int j = 0;
		final int maxLength = 240;
		final int byteLength = 4;
		final int lineLength = 15;
		for (; i < content.length; i++) {
			out[j++] = digits[(maxLength & content[i]) >>> byteLength];
			out[j++] = digits[lineLength & content[i]];
		}
		return out;
	}

	/**
	 * 功能描述: <br>
	 * 字符串进行十六进制解码，返回字节数组
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] decodeHex(String s) {
		byte[] bytes = new byte[s.length() / 2];
		final int hexLength = 16;
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), hexLength);
		}
		return bytes;
	}

	/**
	 * RSA公钥加密
	 * 
	 * @param publickeyPath
	 * @param sourceMessage
	 * @param charset
	 * @return
	 * @throws EncryptionException
	 */
	public static String encryptByRSA(String publickeyPath, String sourceMessage, String charset)
			throws PluginException {
		try {
			FileInputStream f = new FileInputStream(publickeyPath);
			ObjectInputStream b = new ObjectInputStream(f);
			RSAPublicKey pbk = (RSAPublicKey) b.readObject();
			// RSA算法是使用整数进行加密的，在RSA公钥中包含有两个整数信息：e和n。
			// 对于明文数字m,计算密文的公式是m的e次方再与n求模。
			BigInteger e = pbk.getPublicExponent();
			BigInteger n = pbk.getModulus();
			// 获取明文的大整数
			byte[] ptext = sourceMessage.getBytes(charset);
			BigInteger m = new BigInteger(ptext);
			// 加密明文
			BigInteger c = m.modPow(e, n);
			final int hexLength = 16;
			// 将密文转化成字符串
			String cs = c.toString(hexLength);
			// 返回密文
			return cs;
		} catch (FileNotFoundException e) {
			throw new PluginException(e);
		} catch (IOException e) {
			throw new PluginException(e);
		} catch (ClassNotFoundException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * RSA私钥解密
	 * 
	 * @param privatekeyPath
	 * @param encryptMessage
	 * @param charset
	 * @return
	 * @throws EncryptionException
	 */
	public static String decryptByRSA(String privatekeyPath, String encryptMessage, String charset)
			throws PluginException {
		try {
			final int hexLength = 16;
			BigInteger c = new BigInteger(encryptMessage, hexLength);
			// 获取私钥
			FileInputStream f = new FileInputStream(privatekeyPath);
			ObjectInputStream b = new ObjectInputStream(f);
			RSAPrivateKey prk = (RSAPrivateKey) b.readObject();
			// 获取私钥的参数d,n
			BigInteger d = prk.getPrivateExponent();
			BigInteger n = prk.getModulus();
			// 解密明文
			BigInteger m = c.modPow(d, n);
			// 计算明文对应的字符串并输出。
			byte[] mt = m.toByteArray();
			return new String(mt, charset);
		} catch (FileNotFoundException e) {
			throw new PluginException(e);
		} catch (IOException e) {
			throw new PluginException(e);
		} catch (ClassNotFoundException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * 功能描述: <br>
	 * 使用SHA-1算法进行散列运算
	 * 
	 * @param content
	 * @param charset
	 * @return
	 * @throws EncryptionException
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encryptBySHA1(String content, String charset) throws PluginException {
		return encryptByHash(content, charset, "SHA-1");
	}

	/**
	 * 功能描述: <br>
	 * 使用JDK提供的MD5或者SHA-1等哈希算法提供单向散列运算
	 * 
	 * @param content
	 * @param charset
	 * @param algorithm
	 * @return
	 * @throws EncryptionException
	 */
	public static String encryptByHash(String content, String charset, String algorithm) throws PluginException {
		String message = null;
		if (content != null) {
			try {
				MessageDigest msgDigest = MessageDigest.getInstance(algorithm);
				msgDigest.update(content.getBytes(charset));
				byte[] bytes = msgDigest.digest();
				message = new String(encodeHex(bytes));
			} catch (Exception e) {
				throw new PluginException(e);
			}
		}
		return message;
	}
}
