package common.constants;

public class CommonConstants {

	public static final String PROPERTIES_PATH_FILE = "properties/system-environment.properties";

	public static final String HTTP_CONNECTION_CONFIGS_FILE = "HttpConnectionConfigs.xml";

	public static final String CONFIG_DIRECTORY = "config_directory";

	/** 银行返回的错误编码 */
	public static final String RESPONSE_BANK_ERROR_CODE = "bankErrorCode";
	/** 银行返回的错误消息 */
	public static final String RESPONSE_BANK_ERROR_MSG = "bankErrorMsg";

	/** Result of refund order : success */
	public static final Integer REFUND_RESULT_SUCCESS = Integer.valueOf(0);

	/** Result of refund order : failed */
	public static final Integer REFUND_RESULT_FAILED = Integer.valueOf(1);

	/** Result of refund order : pending, wait for call back */
	public static final Integer REFUND_RESULT_PENDING = Integer.valueOf(2);

	/** Result of refund order : exception */
	public static final Integer REFUND_RESULT_EXCEPTION = Integer.valueOf(3);

	/** 退款中等待对账处理 */
	public static final Integer REFUND_RESULT_PENDING_TOCHECK = Integer.valueOf(4);

	/** Reault of plugin check order : success */
	public static final Integer CHECK_RESULT_SUCCESS = Integer.valueOf(0);

	/** Reault of plugin check order : failed */
	public static final Integer CHECK_RESULT_FAILED = Integer.valueOf(1);

	/** Reault of plugin check order : no pay */
	public static final Integer CHECK_RESULT_NOPAY = Integer.valueOf(2);

	/** Result of plugin check order : refund success. */
	public static final Integer CHECK_RESULT_REFUND_SUCCESS = Integer.valueOf(3);

	/** Result of plugin check order : refund fail. */
	public static final Integer CHECK_RESULT_REFUND_FAILED = Integer.valueOf(4);

	/** Result of plugin check order : exception. */
	public static final Integer CHECK_RESULT_EXCEPTION = Integer.valueOf(5);

	/** 未支付成功-需要返回状态 */
	public static final Integer CHECK_RESULT_NOPAY_TOBACK = Integer.valueOf(6);

}
