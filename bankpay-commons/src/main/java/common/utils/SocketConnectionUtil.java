package common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import common.exception.PluginException;
import common.logger.PluginLogger;

/**
 * SSlSocket工厂类. 可扩展至包含 Socket
 * 
 * @author jack_jiang
 * @since 2013-04-24
 * @version 1.0
 */
public class SocketConnectionUtil implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = -1821395603871813740L;

	/** The logger object. */
	private static final PluginLogger LOGGER = PluginLogger.getLogger(SocketConnectionUtil.class);

	// private static final String CLASS_NAME =
	// "com.suning.commerce.payment.utils.SocketConnectionUtil";
	//
	// /** The send method : POST. */
	// private static final String SEND_METHOD_POST = "POST";
	//
	// /** The send method : GET. */
	// private static final String SEND_METHOD_GET = "GET";

	/** The char set : UTF-8. */
	private static final String CHARSET_UTF8 = "UTF-8";

	// /** The char set : GB2312. */
	// private static final String CHARSET_GB2312 = "GB2312";
	//
	// /** The char set : GBK. */
	// private static final String CHARSET_GBK = "GBK";

	/** HTTP request property : HTTP1.1 FORM. */
	private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

	// /** HTTP request property : HTTP1.1 TEXT XML. */
	// private static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	//
	// /** HTTP request property : HTTP1.1 APPLICATION XML. */
	// private static final String CONTENT_TYPE_APP_XML = "application/xml";
	//
	// /** HTTP request property : application/stream. */
	// private static final String CONTENT_TYPE_APP_STREAM =
	// "application/stream";

	/** The default connection time out seconds. */
	private static final String DEFAULT_SOCKET_CONNECT_TIME_OUT = "socketConnectTimeOutMilliSeconds";

	/** The default read time out seconds. */
	private static final String DEFAULT_SOCKET_READ_TIME_OUT = "socketReadTimeOutMilliSeconds";

	/** The default trust store path. */
	private static final String DEFAULT_TRUST_STORE_PATH = "trustStorePath";

	/** The default trust store password. */
	private static final String DEFAULT_TRUST_STORE_PSWD = "trustStorePswd";

	/** The end line symbol. */
	private static final String SYMBOL_END_LINE = "\n";

	/** The start line symbol. */
	private static final String SYMBOL_START_LINE = ":\n";

	/** The configuration for HTTP&SOCKET Connections. */
	private static Map<String, String> configs = new HashMap<String, String>();

	private final static int INADDR4SZ = 4;

	/** socket 多次交互时 发送的信息. */
	private final static String SEND_MESSAGE = "sendMsg";

	/** 结束标志 */
	private final static String END_MARK = "endMark";

	/** 结束值 */
	private final static String QUIT_SOCKET = "quitSocket";

	/** 缓存长度 */
	private final static int TRANSFER_BIT_SIZE = 1024;

	/**
	 * Return DEFAULT HTTP&SOCKET connection configurations.
	 * 
	 * @return Connection configurations map.
	 * @throws PluginException
	 */
	private static Map<String, String> getConfigs() throws PluginException {
		if (configs == null || configs.isEmpty()) {
			configs = HttpConnectionConfigParser.loadConfigs();
		}
		return configs;
	}

	/**
	 * Send the Socket request and return the response string using
	 * 
	 * @param host
	 * @param host
	 * @param msg
	 * @return
	 */
	public static String sendSocketConnection(String host, int port, String msg) throws PluginException {
		return sendSocketConnection(host, port, msg, CHARSET_UTF8, CHARSET_UTF8);
	}

	/**
	 * Send the Socket request and return the response string using
	 * 
	 * @param host
	 * @param host
	 * @param msg
	 * @param charset
	 *            输入输出统一编码格式.
	 * @return
	 */
	public static String sendSocketConnection(String host, int port, String msg, String charset)
			throws PluginException {
		return sendSocketConnection(host, port, msg, charset, charset);
	}

	/**
	 * @param host
	 * @param port
	 * @param msg
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketConnection(String host, int port, String msg, String sendOutCharset,
			String receiveInCharset) throws PluginException {
		Map<String, String> param = getConfigs();
		return sendSocketConnection(host, port, msg, sendOutCharset, receiveInCharset,
				Integer.valueOf(param.get(DEFAULT_SOCKET_CONNECT_TIME_OUT)),
				Integer.valueOf(param.get(DEFAULT_SOCKET_READ_TIME_OUT)));
	}

	/**
	 * Send the Socket request and return the response string using
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @param connectTimeOut
	 * @param readTimeOut
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketConnection(String host, int port, String msg, String sendOutCharset,
			String receiveInCharset, int connectTimeOut, int readTimeOut) throws PluginException {
		// validate sendMessage.理论上不为空
		if (msg == null || msg.length() == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}
		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		OutputStream outStrm = null;
		DataOutputStream out = null;
		try {
			InetAddress iNetaddr = null;
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOut);
			socket.setSoTimeout(readTimeOut);

			outStrm = socket.getOutputStream();
			out = new DataOutputStream(outStrm);
			out.write(msg.getBytes(sendOutCharset));
			out.flush();

			responseString = readInputStream(socket.getInputStream(), receiveInCharset);
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e + "\nURL = " + host + "\nParameter = " + msg.toString());
		} finally {

			try {
				if (socket != null) {
					socket.close();
				}
				if (out != null) {
					out.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
			} catch (IOException e) {
				// throw new PluginException("socket close exception: "
				// + e);
				socket = null;
				out = null;
				outStrm = null;
			}
		}

		return responseString;
	}

	/**
	 * keep a socket until receive a end mark or message have send over.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param endMark
	 * @param charset
	 * @param connectTimeOut
	 * @param readTimeOut
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketKeepConnection(String host, int port, String[] msg, String endMark,
			String sendOutCharset, String receiveInCharset, int connectTimeOut, int readTimeOut)
			throws PluginException {
		// validate sendMessage. 理论上不为空.
		if (msg == null || msg.length == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}

		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		DataOutputStream out = null;
		InputStream inStream = null;
		OutputStream outStrm = null;
		try {
			InetAddress iNetaddr = null;
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain Name
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOut);
			socket.setSoTimeout(readTimeOut);
			outStrm = socket.getOutputStream();
			out = new DataOutputStream(outStrm);
			inStream = socket.getInputStream();
			int msgLength = msg.length;
			int count = 0;
			StringBuffer sb = new StringBuffer();
			while (count == msgLength) {
				out.write(msg[count].getBytes(sendOutCharset));
				out.flush();
				String readStr = readInputStream(inStream, receiveInCharset);
				sb.append(readStr);
				if (readStr.contains(endMark)) { // if contains this word.
													// break.
					break;
				}
				count++;
			}
			responseString = sb.toString();
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e + "\nURL = " + host + "\nParameter = "
					+ msg.toString());
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
				if (out != null) {
					out.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				socket = null;
				out = null;
				inStream = null;
				outStrm = null;
				// throw new
				// PluginException("socket or IO close exception: " +
				// e);
			}
		}
		return responseString;
	}

	/**
	 * 重载 sendSocketRepeatMutualConnection 方法. 简化 传入参数.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketRepeat
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat) throws PluginException {
		return sendSocketRepeatMutualConnection(host, port, msg, socketRepeat, CHARSET_UTF8, CHARSET_UTF8);
	}

	/**
	 * 重载 sendSocketRepeatMutualConnection 方法. 简化 传入参数.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketRepeat
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat, String sendOutCharset, String receiveInCharset)
			throws PluginException {
		try {
			Map<String, String> param = getConfigs();
			return sendSocketRepeatMutualConnection(host, port, msg, socketRepeat, sendOutCharset, receiveInCharset,
					Integer.valueOf(param.get(DEFAULT_SOCKET_CONNECT_TIME_OUT)),
					Integer.valueOf(param.get(DEFAULT_SOCKET_READ_TIME_OUT)));
		} catch (PluginException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * keep a socket until receive a end mark . 实现多次交互处理. 返回每次交互后的接受到的所有数据.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param endMark
	 * @param charset
	 * @param connectTimeOut
	 * @param readTimeOut
	 * @return
	 * @throws PluginException
	 */
	public static String sendSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat, String sendOutCharset, String receiveInCharset,
			int connectTimeOut, int readTimeOut) throws PluginException {
		// validate sendMessage. 理论上不为空
		if (msg == null || msg.length() == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}

		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		DataOutputStream out = null;
		InputStream inStream = null;
		OutputStream outStrm = null;
		try {
			InetAddress iNetaddr = null;
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain Name
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOut);
			socket.setSoTimeout(readTimeOut);
			outStrm = socket.getOutputStream();
			out = new DataOutputStream(outStrm);
			inStream = socket.getInputStream();
			StringBuffer sb = new StringBuffer();
			String changgeStr = msg; // 中间处理参数
			int count = 1; // 提示请求的次数.
			while (true) {
				out.write(changgeStr.getBytes(sendOutCharset));
				out.flush();
				String readStr = readInputStream(inStream, receiveInCharset);
				Map<String, String> msgMap = socketRepeat.dealSocketMessage(readStr);
				sb.append(count + SYMBOL_START_LINE).append(readStr).append(SYMBOL_END_LINE);
				changgeStr = msgMap.get(SEND_MESSAGE);
				if (null != msgMap.get(END_MARK) || msgMap.get(END_MARK).equals(QUIT_SOCKET)) {
					break;
				}
				count++;
			}
			responseString = sb.toString();
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e + "\nURL = " + host + "\nParameter = "
					+ msg.toString());
		} catch (PluginException e) {
			throw new PluginException("交互数据处理异常  PluginException: " + e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
				if (out != null) {
					out.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				socket = null;
				out = null;
				inStream = null;
				outStrm = null;
				// throw new
				// PluginException("socket or IO close exception: " +
				// e);
			}
		}
		return responseString;
	}

	/**
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketType
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketConnection(String host, int port, String msg, SSLSocketType socketType)
			throws PluginException {
		return sendSSLSocketConnection(host, port, msg, socketType, CHARSET_UTF8, CHARSET_UTF8);
	}

	/**
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketType
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketConnection(String host, int port, String msg, SSLSocketType socketType,
			String sendOutCharset, String receiveInCharset) throws PluginException {
		try {
			Map<String, String> param = getConfigs();
			return sendSSLSocketConnection(host, port, msg, socketType, sendOutCharset, receiveInCharset,
					(String) param.get(DEFAULT_TRUST_STORE_PATH), (String) param.get(DEFAULT_TRUST_STORE_PSWD));
		} catch (PluginException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketType
	 * @param charset
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketConnection(String host, int port, String msg, SSLSocketType socketType,
			String sendOutCharset, String receiveInCharset, String trustStorePath, String trustStorePassword)
			throws PluginException {
		try {
			Map<String, String> param = getConfigs();
			return sendSSLSocketConnection(host, port, msg, socketType, CONTENT_TYPE_FORM, sendOutCharset,
					receiveInCharset, trustStorePath, trustStorePassword,
					Integer.valueOf(param.get(DEFAULT_SOCKET_CONNECT_TIME_OUT)),
					Integer.valueOf(param.get(DEFAULT_SOCKET_READ_TIME_OUT)));
		} catch (PluginException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * send a SSLScoket connection.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param contentType
	 *            // not use now,but maybe use further. send null now
	 * @param charset
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @param connectTimeOutSeconds
	 * @param readTimeOutSeconds
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketConnection(String host, int port, String msg, SSLSocketType socketType,
			String contentType, String sendOutCharset, String receiveInCharset, String trustStorePath,
			String trustStorePassword, int connectTimeOutSeconds, int readTimeOutSeconds)
			throws PluginException {

		// validate sendMessage.理论上不为空
		if (msg == null || msg.length() == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}
		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		DataOutputStream out = null;
		OutputStream outStrm = null;
		InetAddress iNetaddr = null;
		SSLSocketFactory factory = null;
		SSLSocket tSSLSocket = null;
		try {
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain Name
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOutSeconds); // 为了照顾兼容性,此版本的
																	// createSocket()创建空的SSLSocket时会抛异常,因为父类ScoketFactory
																	// 也为了设置超时时间,先建立一个socket实现.
			if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.JDK_Version_SUN)) {
				factory = initSUNSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.choose_Defined)) {
				factory = initDefinedSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else {
				factory = initIBMSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			}
			tSSLSocket = (SSLSocket) factory.createSocket(socket, host, port, true);
			tSSLSocket.setSoTimeout(readTimeOutSeconds);
			tSSLSocket.startHandshake();

			outStrm = tSSLSocket.getOutputStream();
			out = new DataOutputStream(outStrm);
			out.write(msg.getBytes(sendOutCharset));
			out.flush();

			responseString = readInputStream(tSSLSocket.getInputStream(), receiveInCharset);
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (out != null) {
					out.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
				if (tSSLSocket != null) {
					tSSLSocket.close();
				}
			} catch (IOException e) {
				socket = null;
				out = null;
				outStrm = null;
				tSSLSocket = null;
			}
		}
		return responseString;
	}

	/**
	 * keep a sslSocket until receive a end mark or message have send over.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param endMark
	 * @param contentType
	 * @param charset
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @param connectTimeOutSeconds
	 * @param readTimeOutSeconds
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketKeepConnection(String host, int port, String[] msg, String endMark,
			String contentType, String sendOutCharset, String receiveInCharset, String trustStorePath,
			String trustStorePassword, SSLSocketType socketType, int connectTimeOutSeconds, int readTimeOutSeconds)
			throws PluginException {

		// validate sendMessage.
		if (msg == null || msg.length == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}
		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		DataOutputStream out = null;
		OutputStream outStrm = null;
		InetAddress iNetaddr = null;
		SSLSocketFactory factory = null;
		SSLSocket tSSLSocket = null;
		try {
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain Name
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOutSeconds); // 为了照顾兼容性,此版本的
																	// createSocket()创建空的SSLSocket时会抛异常,因为父类ScoketFactory
																	// 也为了设置超时时间,先建立一个socket实现.
			if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.JDK_Version_SUN)) {
				factory = initSUNSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.choose_Defined)) {
				factory = initDefinedSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else {
				factory = initIBMSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			}
			tSSLSocket = (SSLSocket) factory.createSocket(socket, host, port, true);
			tSSLSocket.setSoTimeout(readTimeOutSeconds);
			tSSLSocket.startHandshake();
			StringBuffer sb = new StringBuffer();
			outStrm = socket.getOutputStream();
			out = new DataOutputStream(outStrm);

			int msgLength = msg.length;
			int count = 0;
			while (count == msgLength) {
				out.write(msg[count].getBytes(sendOutCharset));
				out.flush();
				String readStr = readInputStream(tSSLSocket.getInputStream(), receiveInCharset);
				sb.append(readStr);
				if (readStr.contains(endMark)) { // if contains this word.
													// break.
					break;
				}
				count++;
			}
			responseString = sb.toString();
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (out != null) {
					out.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
				if (tSSLSocket != null) {
					tSSLSocket.close();
				}
			} catch (IOException e) {
				socket = null;
				out = null;
				outStrm = null;
				tSSLSocket = null;
			}
		}
		return responseString;
	}

	/**
	 * 重载 sendSSLSocketRepeatMutualConnection 方法, 简化入参.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketRepeat
	 * @param socketType
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat, SSLSocketType socketType) throws PluginException {
		try {
			Map<String, String> param = getConfigs();
			return sendSSLSocketRepeatMutualConnection(host, port, msg, socketRepeat, socketType, CONTENT_TYPE_FORM,
					CHARSET_UTF8, CHARSET_UTF8, (String) param.get(DEFAULT_TRUST_STORE_PATH),
					(String) param.get(DEFAULT_TRUST_STORE_PSWD));
		} catch (PluginException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * 重载 sendSSLSocketRepeatMutualConnection 方法, 简化入参.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param socketRepeat
	 * @param socketType
	 * @param contentType
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat, SSLSocketType socketType, String contentType,
			String sendOutCharset, String receiveInCharset, String trustStorePath, String trustStorePassword)
			throws PluginException {

		try {
			Map<String, String> param = getConfigs();
			return sendSSLSocketRepeatMutualConnection(host, port, msg, socketRepeat, socketType, contentType,
					sendOutCharset, receiveInCharset, trustStorePath, trustStorePassword,
					Integer.valueOf(param.get(DEFAULT_SOCKET_CONNECT_TIME_OUT)),
					Integer.valueOf(param.get(DEFAULT_SOCKET_READ_TIME_OUT)));
		} catch (PluginException e) {
			throw new PluginException(e);
		}
	}

	/**
	 * 处理多次交互的请求结果. 并返回所有请求到的返回值.
	 * 
	 * @param host
	 * @param port
	 * @param msg
	 * @param endMark
	 * @param contentType
	 * @param sendOutCharset
	 * @param receiveInCharset
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @param connectTimeOutSeconds
	 * @param readTimeOutSeconds
	 * @return
	 * @throws PluginException
	 */
	public static String sendSSLSocketRepeatMutualConnection(String host, int port, String msg,
			SocketRepeatMutualInterface socketRepeat, SSLSocketType socketType, String contentType,
			String sendOutCharset, String receiveInCharset, String trustStorePath, String trustStorePassword,
			int connectTimeOutSeconds, int readTimeOutSeconds) throws PluginException {

		// validate sendMessage. 理论上不为空
		if (msg == null || msg.length() == 0) {
			throw new PluginException("Request param error : sendMessage is null.");
		}
		String responseString = null;
		Socket socket = new Socket(); // create a empty Socket.
		DataOutputStream out = null;
		OutputStream outStrm = null;
		InetAddress iNetaddr = null;
		SSLSocketFactory factory = null;
		SSLSocket tSSLSocket = null;
		try {
			byte[] strHost = textToNumericFormatV4(host);
			if (null == strHost) {// domain Name
				iNetaddr = InetAddress.getByName(host);
			} else {// IP Address
				iNetaddr = InetAddress.getByAddress(strHost);
			}
			InetSocketAddress iNetSocketaddr = new InetSocketAddress(iNetaddr, port);
			socket.connect(iNetSocketaddr, connectTimeOutSeconds); // 为了照顾兼容性,此版本的
																	// createSocket()创建空的SSLSocket时会抛异常,因为父类ScoketFactory
																	// 也为了设置超时时间,先建立一个socket实现.
			if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.JDK_Version_SUN)) {
				factory = initSUNSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else if (socketType.getJdkVersion().equals(SSLSocketUtilEnum.choose_Defined)) {
				factory = initDefinedSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			} else {
				factory = initIBMSSLSocketFactory(trustStorePath, trustStorePassword, socketType);
			}
			tSSLSocket = (SSLSocket) factory.createSocket(socket, host, port, true);
			tSSLSocket.setSoTimeout(readTimeOutSeconds);
			tSSLSocket.startHandshake();
			StringBuffer sb = new StringBuffer();
			outStrm = socket.getOutputStream();
			out = new DataOutputStream(outStrm);
			String changgeStr = msg; // 中间处理参数
			int count = 1; // 提示请求的次数.
			while (true) {
				out.write(changgeStr.getBytes(sendOutCharset));
				out.flush();
				String readStr = readInputStream(tSSLSocket.getInputStream(), receiveInCharset);
				Map<String, String> msgMap = socketRepeat.dealSocketMessage(readStr);
				sb.append(count + SYMBOL_START_LINE).append(readStr).append(SYMBOL_END_LINE);
				changgeStr = msgMap.get(SEND_MESSAGE);
				if (null != msgMap.get(END_MARK) || msgMap.get(END_MARK).equals(QUIT_SOCKET)) {
					break;
				}
				count++;
			}
			responseString = sb.toString();
		} catch (UnknownHostException e) {
			throw new PluginException("UnknownHostException: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e);
		} catch (PluginException e) {
			throw new PluginException("交互数据处理异常  PluginException: " + e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (out != null) {
					out.close();
				}
				if (outStrm != null) {
					outStrm.close();
				}
				if (tSSLSocket != null) {
					tSSLSocket.close();
				}
			} catch (IOException e) {
				socket = null;
				out = null;
				outStrm = null;
				tSSLSocket = null;
			}
		}
		return responseString;
	}

	/**
	 * init IBM lib
	 * 
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @return
	 * @throws PluginException
	 */
	private static SSLSocketFactory initIBMSSLSocketFactory(String trustStorePath, String trustStorePassword,
			SSLSocketType socketType) throws PluginException {
		FileInputStream in = null;
		SSLSocketFactory sslSocketFactory = null;
		try {
			// build send message.
			KeyStore ks = KeyStore.getInstance(socketType.getKeyStoreVersion().getCode());
			in = new FileInputStream(trustStorePath);
			ks.load(in, trustStorePassword.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(socketType.getKeyManagerVersion().getCode());
			kmf.init(ks, trustStorePassword.toCharArray());
			TrustManager[] tm;
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(socketType.getTrustManagerVersion().getCode());
			tmf.init(ks);
			tm = tmf.getTrustManagers();
			SSLContext sslContext = SSLContext.getInstance(socketType.getSslContextModle().getCode());
			sslContext.init(kmf.getKeyManagers(), tm, null); // 这两个null,
																// 一个是keymanage,一个是new
																// Random.
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (KeyStoreException e) {
			throw new PluginException("Key Store Exception: " + e);
		} catch (NoSuchAlgorithmException e) {
			throw new PluginException("No Such Algorithm Exception: " + e);
		} catch (CertificateException e) {
			throw new PluginException("Certificate Exception: " + e);
		} catch (FileNotFoundException e) {
			throw new PluginException("File Not Found Exception: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e);
		} catch (KeyManagementException e) {
			throw new PluginException("Key Management Exception: " + e);
		} catch (UnrecoverableKeyException e) {
			throw new PluginException("UnrecoverableKey Exception: " + e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				in = null;
			}
		}
		return sslSocketFactory;
	}

	/**
	 * init SUN lib
	 * 
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @return
	 * @throws PluginException
	 */
	private static SSLSocketFactory initSUNSSLSocketFactory(String trustStorePath, String trustStorePassword,
			SSLSocketType socketType) throws PluginException {
		FileInputStream in = null;
		SSLSocketFactory sslSocketFactory = null;
		try {
			// import com.sun.net.ssl.SSLContext;
			// import com.sun.net.ssl.TrustManager;
			// import com.sun.net.ssl.TrustManagerFactory;
			java.security.Provider tProvider = new com.sun.net.ssl.internal.ssl.Provider();
			in = new FileInputStream(trustStorePath);
			com.sun.net.ssl.SSLContext tSSLContext = com.sun.net.ssl.SSLContext.getInstance(socketType
					.getSslContextModle().getCode(), tProvider);
			com.sun.net.ssl.TrustManagerFactory tTrustManagerFactory = com.sun.net.ssl.TrustManagerFactory.getInstance(
					socketType.getTrustManagerVersion().getCode(), tProvider);
			KeyStore tKeyStore = KeyStore.getInstance(socketType.getKeyStoreVersion().getCode());
			tKeyStore.load(in, trustStorePassword.toCharArray());
			tTrustManagerFactory.init(tKeyStore);
			com.sun.net.ssl.TrustManager[] tTrustManager = tTrustManagerFactory.getTrustManagers();
			tSSLContext.init(null, tTrustManager, null); // 这两个null,
															// 一个是keymanage,一个是new
															// Random.
			sslSocketFactory = tSSLContext.getSocketFactory();
		} catch (KeyStoreException e) {
			throw new PluginException("Key Store Exception: " + e);
		} catch (NoSuchAlgorithmException e) {
			throw new PluginException("No Such Algorithm Exception: " + e);
		} catch (CertificateException e) {
			throw new PluginException("Certificate Exception: " + e);
		} catch (FileNotFoundException e) {
			throw new PluginException("File Not Found Exception: " + e);
		} catch (IOException e) {
			throw new PluginException("IOException: " + e);
		} catch (KeyManagementException e) {
			throw new PluginException("Key Management Exception: " + e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				in = null;
			}
		}
		return sslSocketFactory;
	}

	/**
	 * set defined SSLSocket Security
	 * 
	 * @param trustStorePath
	 * @param trustStorePassword
	 * @param socketType
	 * @return
	 * @throws PluginException
	 * @throws IOException
	 */
	private static SSLSocketFactory initDefinedSSLSocketFactory(String trustStorePath, String trustStorePassword,
			SSLSocketType socketType) throws PluginException, IOException {
		FileInputStream in = null;
		SSLSocketFactory sslSocketFactory = null;
		try {
			// TODO

		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sslSocketFactory;
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws IOException
	 * @throws Exception
	 */
	public static String readInputStream(InputStream inStream, String charset) {
		final String methodName = "readInputStream";
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[TRANSFER_BIT_SIZE];
		int len = -1;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}

			byte[] resB = outSteam.toByteArray();
			return new String(resB, charset);

		} catch (IOException e) {
			LOGGER.error(methodName, e.toString());
		} finally {
			if (null != outSteam) {
				try {
					outSteam.close();
				} catch (IOException e) {
					LOGGER.error(methodName, e.toString());
				}
			}
			if (null != inStream) {
				try {
					inStream.close();
				} catch (IOException e) {
					LOGGER.error(methodName, e.toString());
				}
			}
		}
		return null;
	}

	/**
	 * Read the input stream and build the message string.
	 * 
	 * @param inStream
	 * @param charset
	 * @return
	 * @throws PluginException
	 * @throws IOException
	 */
	/*
	 * private static String readInputStream(InputStream inputStream, String
	 * charset) throws PluginException, IOException { String response =
	 * null; // BufferedReader ins = new BufferedReader(new
	 * InputStreamReader(inputStream)); // System.out.println(ins.readLine());
	 * if (inputStream != null) { try { BufferedInputStream bufferedinputstream
	 * = new BufferedInputStream(inputStream); InputStreamReader isr = new
	 * InputStreamReader(bufferedinputstream, charset); BufferedReader inBuff =
	 * new BufferedReader(isr); StringBuffer buff = new StringBuffer(); String
	 * readLine = null; String endLine = SYMBOL_END_LINE; while ((readLine =
	 * inBuff.readLine()) != null) { buff.append(readLine).append(endLine); } if
	 * (buff.length() > 0) { response = buff.substring(0, buff.length() - 1); }
	 * else { response = buff.toString(); } } catch
	 * (UnsupportedEncodingException e) { throw new
	 * PluginException("Unsupported Encoding Exception: " + e); } catch
	 * (IOException e) { throw new PluginException("IOException: " + e);
	 * } } else { throw new
	 * PluginException("Error Input Stream Parameter"); } return
	 * response; }
	 */

	/**
	 * this have a bug to converts. so Toggle Comment some numbers to resolve.
	 * 
	 * Converts IPv4 address in its textual presentation form into its numeric
	 * binary form.
	 * 
	 * @param src
	 *            a String representing an IPv4 address in standard format
	 * @return a byte array representing the IPv4 numeric address
	 * @param src
	 * @return
	 */
	private static byte[] textToNumericFormatV4(String src) {
		if (src.length() == 0) {
			return null;
		}

		byte[] res = new byte[INADDR4SZ];
		String[] s = src.split("\\.", -1);
		long val;
		try {
			switch (s.length) {
			// case 1:
			// /*
			// * When only one part is given, the value is stored directly in
			// the network address without any byte
			// * rearrangement.
			// */
			//
			// val = Long.parseLong(s[0]);
			// if (val < 0 || val > 0xffffffffL)
			// return null;
			// res[0] = (byte) ((val >> 24) & 0xff);
			// res[1] = (byte) (((val & 0xffffff) >> 16) & 0xff);
			// res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
			// res[3] = (byte) (val & 0xff);
			// break;
			// case 2:
			// /*
			// * When a two part address is supplied, the last part is
			// interpreted as a 24-bit quantity and placed
			// * in the right most three bytes of the network address. This
			// makes the two part address format
			// * convenient for specifying Class A network addresses as
			// net.host.
			// */
			//
			// val = Integer.parseInt(s[0]);
			// if (val < 0 || val > 0xff)
			// return null;
			// res[0] = (byte) (val & 0xff);
			// val = Integer.parseInt(s[1]);
			// if (val < 0 || val > 0xffffff)
			// return null;
			// res[1] = (byte) ((val >> 16) & 0xff);
			// res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
			// res[3] = (byte) (val & 0xff);
			// break;
			// case 3:
			// /*
			// * When a three part address is specified, the last part is
			// interpreted as a 16-bit quantity and
			// * placed in the right most two bytes of the network address. This
			// makes the three part address
			// * format convenient for specifying Class B net- work addresses as
			// 128.net.host.
			// */
			// for (int i = 0; i < 2; i++) {
			// val = Integer.parseInt(s[i]);
			// if (val < 0 || val > 0xff)
			// return null;
			// res[i] = (byte) (val & 0xff);
			// }
			// val = Integer.parseInt(s[2]);
			// if (val < 0 || val > 0xffff)
			// return null;
			// res[2] = (byte) ((val >> 8) & 0xff);
			// res[3] = (byte) (val & 0xff);
			// break;
			case 4:
				/*
				 * When four parts are specified, each is interpreted as a byte
				 * of data and assigned, from left to right, to the four bytes
				 * of an IPv4 address.
				 */
				for (int i = 0; i < 4; i++) {
					val = Integer.parseInt(s[i]);
					if (val < 0 || val > 0xff)
						return null;
					res[i] = (byte) (val & 0xff);
				}
				break;
			default:
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return res;
	}

}
