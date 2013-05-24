package common.utils;

/**
 * SSLSocket 参数的枚举值集合类,如果有新增的.可直接扩展.
 * 
 * @author jack_jiang
 * @since 2013-04-25
 * @version 1.0
 */
public enum SSLSocketUtilEnum {

	/** 默认缺省 */
	choose_Default("Default"),

	/** 自定义 */
	choose_Defined("Defined"),

	/** IBM */
	JDK_Version_IBM("IBM"),

	/** Sun */
	JDK_Version_SUN("SUN"),

	/** JKS */
	KeyStore_Version_JKS("JKS"),

	/** IBM KeyManager */
	KeyManager_Version_IbmX509("IbmX509"),

	/** IBM KeyManager */
	KeyManager_Version_SunX509("SunX509"),

	/** SSL Security */
	SSlContext_SSL("SSL"),

	/** TLS Security */
	SSlContext_TLS("TLS"),

	/** IBM TrustManager */
	TrustManager_Version_IBM_PKIX("IbmPKIX"),

	/** IBM TrustManager */
	TrustManager_Version_Ibm_X509("IbmX509"),

	/** SUN TrustManager */
	TrustManager_Version_SUN_X509("SunX509");

	private String code;

	SSLSocketUtilEnum(String var) {
		code = var;
	}

	public String getCode() {
		return code;
	}

}
