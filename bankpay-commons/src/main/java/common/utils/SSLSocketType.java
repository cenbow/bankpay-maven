package common.utils;

/**
 * @see 该类是SSLSocket 初始化的包装类.默认都是提供的IBM的环境. 提供了三个构造方法来实现不同的业务需求
 * @see 该类目前暂不提供 证书库路径和密码的属性.
 * 
 * @author jack_jiang
 * @since 2013-04-25
 * @version 1.0
 */
public class SSLSocketType {

	/** 是否选用 JDK库内容还是自定义内容, 默认选缺省. */
	private SSLSocketUtilEnum chooseDefaultFlag = SSLSocketUtilEnum.choose_Default;

	/**************************** 默认的SSLSocket配置的两套环境 *******************/

	/** 默认选IBM的jdk */
	private SSLSocketUtilEnum jdkVersion = SSLSocketUtilEnum.JDK_Version_IBM;

	/** 默认是JKS的 */
	private SSLSocketUtilEnum keyStoreVersion = SSLSocketUtilEnum.KeyStore_Version_JKS;

	/** 默认是keyManager 类型的 */
	private SSLSocketUtilEnum keyManagerVersion = SSLSocketUtilEnum.KeyManager_Version_IbmX509;

	/** 安全协定 默认 SSl */
	private SSLSocketUtilEnum sslContextModle = SSLSocketUtilEnum.SSlContext_SSL;

	/** 信任库版本 默认IBM PKIX */
	private SSLSocketUtilEnum trustManagerVersion = SSLSocketUtilEnum.TrustManager_Version_IBM_PKIX;

	/**************************** end **********************************************/

	/**************************** 下面是自定义 SSLSocket 环境, 需要提供类名全路径 *******************/

	/** 这里的provider 第三方安全验证的提供者 */
	private String definedProvider;

	/** SSLSocket Factory and SSLEngine Factory */
	private String definedSslContext;

	/** keyStore lib */
	// private String definedKeyStore;

	/** keyManagerFactory , allow empty */
	// private String definedKeyManagerFactory;

	/** trustManagerFactory */
	private String definedTrustManagerFactory;

	/** trustManager */
	private String definedTrustManager;

	/**
	 * 选择JDK的版本
	 * 
	 * @param jdkVersion
	 */
	public SSLSocketType(SSLSocketUtilEnum jdkVersion) {
		super();
		this.jdkVersion = jdkVersion;
	}

	/**
	 * 选择JDK的版本,并自定义相关参数.
	 * 
	 * @param jdkVersion
	 * @param keyStoreVersion
	 * @param keyManagerVersion
	 * @param sslContextModle
	 * @param trustManagerVersion
	 */
	public SSLSocketType(SSLSocketUtilEnum jdkVersion, SSLSocketUtilEnum keyStoreVersion,
			SSLSocketUtilEnum keyManagerVersion, SSLSocketUtilEnum sslContextModle,
			SSLSocketUtilEnum trustManagerVersion) {
		super();
		this.jdkVersion = jdkVersion;
		this.keyStoreVersion = keyStoreVersion;
		this.keyManagerVersion = keyManagerVersion;
		this.sslContextModle = sslContextModle;
		this.trustManagerVersion = trustManagerVersion;
	}

	/**
	 * 这个是选用自定义的SSLSocket安全认证,为了防止chooseDefaultFlag 这个参数传错. 直接定义死.
	 * 
	 * @param definedProvider
	 * @param definedSslContext
	 * @param definedKeyStore
	 * @param definedKeyManagerFactory
	 * @param definedTrustManagerFactory
	 * @param definedTrustManager
	 */
	public SSLSocketType(String definedProvider, String definedSslContext, String definedTrustManagerFactory,
			String definedTrustManager) {
		super();
		this.chooseDefaultFlag = SSLSocketUtilEnum.choose_Defined;
		this.definedProvider = definedProvider;
		this.definedSslContext = definedSslContext;
		this.definedTrustManagerFactory = definedTrustManagerFactory;
		this.definedTrustManager = definedTrustManager;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getChooseDefaultFlag() {
		return chooseDefaultFlag;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getJdkVersion() {
		return jdkVersion;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getKeyStoreVersion() {
		return keyStoreVersion;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getKeyManagerVersion() {
		return keyManagerVersion;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getSslContextModle() {
		return sslContextModle;
	}

	/**
	 * @return
	 */
	public SSLSocketUtilEnum getTrustManagerVersion() {
		return trustManagerVersion;
	}

	/**
	 * @return
	 */
	public String getDefinedProvider() {
		return definedProvider;
	}

	/**
	 * @return
	 */
	public String getDefinedSslContext() {
		return definedSslContext;
	}

	/**
	 * @return
	 */
	public String getDefinedTrustManagerFactory() {
		return definedTrustManagerFactory;
	}

	/**
	 * @return
	 */
	public String getDefinedTrustManager() {
		return definedTrustManager;
	}

	public void setChooseDefaultFlag(SSLSocketUtilEnum chooseDefaultFlag) {
		this.chooseDefaultFlag = chooseDefaultFlag;
	}

	public void setJdkVersion(SSLSocketUtilEnum jdkVersion) {
		this.jdkVersion = jdkVersion;
	}

	public void setKeyStoreVersion(SSLSocketUtilEnum keyStoreVersion) {
		this.keyStoreVersion = keyStoreVersion;
	}

	public void setKeyManagerVersion(SSLSocketUtilEnum keyManagerVersion) {
		this.keyManagerVersion = keyManagerVersion;
	}

	public void setSslContextModle(SSLSocketUtilEnum sslContextModle) {
		this.sslContextModle = sslContextModle;
	}

	public void setTrustManagerVersion(SSLSocketUtilEnum trustManagerVersion) {
		this.trustManagerVersion = trustManagerVersion;
	}

	public void setDefinedProvider(String definedProvider) {
		this.definedProvider = definedProvider;
	}

	public void setDefinedSslContext(String definedSslContext) {
		this.definedSslContext = definedSslContext;
	}

	public void setDefinedTrustManagerFactory(String definedTrustManagerFactory) {
		this.definedTrustManagerFactory = definedTrustManagerFactory;
	}

	public void setDefinedTrustManager(String definedTrustManager) {
		this.definedTrustManager = definedTrustManager;
	}

}
