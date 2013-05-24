package plugins.quickpay.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import plugins.quickpay.dto.JSCCBQuickReqBody;
import plugins.quickpay.dto.JSCCBQuickReqHead;
import plugins.quickpay.dto.JSCCBQuickResHead;
import plugins.quickpay.dto.JSCCBQuickRespBody;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import common.constants.CommonConstants;
import common.exception.PluginException;
import common.logger.PluginLogger;
import common.plugins.AbstractPlugin;
import common.plugins.QuickPayPlugin;
import common.plugins.dto.QuickPayRequestData;
import common.plugins.keys.EnumKeys;
import common.utils.ConfirmLongUtil;
import common.utils.EncryptionDecryptionUtil;
import common.utils.Enum2StringUtils;
import common.utils.SocketConnectionUtil;
import common.utils.TimestampUtil;
import common.utils.XmlConvertUtil;

public class JSCCBQuickPayPlugin extends AbstractPlugin implements QuickPayPlugin {

	private static final PluginLogger LOGGER = PluginLogger.getLogger(JSCCBQuickPayPlugin.class);
	/** 客户号. */
	private static final String CUST_ID = "custID";
	/** 操作员号. */
	private static final String USER_ID = "userID";
	/** 密码. */
	private static final String PASSWORD = "password";
	/** 交易码. */
	private static final String TX_CODE = "txCode";
	/** 语言. */
	private static final String LANGUAGE = "language";
	/** 本方企业账户. */
	private static final String ACCOUNT_NO = "accountNo";
	/** 支付代发代扣编号. */
	private static final String PAYMENT_BILL_CODE = "payBillCode";
	/** 支付用途编号. */
	private static final String PAYMENT_USEOF_CODE = "payUseCode";
	/** 退款代发代扣编号. */
	private static final String REFUND_BILL_CODE = "refundBillCode";
	/** 退款用途编号. */
	private static final String REFUND_USEOF_CODE = "refundUseCode";
	/** 网银审批标识. */
	private static final String FLOW_FLAG = "flowFlag";
	/** 支付代码. */
	private static final String PAY_CODE = "payCode";
	/** 退款代码. */
	private static final String REFUND_CODE = "refundCode";
	/** 成功的响应码. */
	private static final String SUCCESS_CODE = "000000";
	/** 签约、支付查询成功响应码. */
	private static final String OPER_SUCCESS_CODE = "ZF00";
	/** 签约交易代码. */
	private static final String SIGN_TRADE_CODE = "signTradeCode";
	/** 支付查询代码 */
	private static final String CHECK_TRADE_CODE = "checkTradeCode";
	/** 卡类型. */
	private static final String CARD_TYPE = "cardType";
	/** 证件类型. */
	private static final String CERT_TYPE = "certType";
	/** 商户号. */
	private static final String MERCHANT_ID = "merchantId";
	/** 终端号. */
	private static final String TERMINAL = "terminal";
	/** 专线host. */
	private static final String SPECIAL_HOST = "specialHost";
	/** 专线端口号. */
	private static final String SPECIAL_PORT = "specialPort";
	/** 前置机host. */
	private static final String HOST = "host";
	/** 前置机端口号. */
	private static final String PORT = "port";
	/** POS编号. */
	private static final String POS = "pos";
	/** 支付查询交易性质 type */
	private static final String TRADE_NATURE_TYPE = "type";

	/** 支付查询返回的状态：1 正在处理 */
	private static final String CHECK_STATUS_DEALING = "1";
	/** 支付查询返回的状态：3 失败 */
	private static final String CHECK_STATUS_FAIL = "3";
	/** 数量：9 */
	private static final Integer MERCHANT_SERIAL_NO_LENGTH = 9;
	/** 签约，支付查询接口报文头. */
	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	/** 支付，退款接口报文头. */
	private static final String PAYANDREFUND_XML_HEAD = "<?xml version=\"1.0\" encoding=\"GB2312\""
			+ " standalone=\"yes\" ?>";

	private String formateEnumMap(Map<? extends EnumKeys, Object> map) throws PluginException {
		return Enum2StringUtils.formateEnumBytes(map, "GBK");
	}

	/**
	 * 将字符串转化化成Map
	 * 
	 * 
	 * @param map
	 *            响应枚举类型
	 * @param resp
	 *            响应报文
	 * 
	 * @return
	 * @throws PluginException
	 *             异常
	 */
	private Map<EnumKeys, Object> parseString(Class<? extends EnumKeys> enumType, String resp) throws PluginException {
		return Enum2StringUtils.parseByBytes(enumType, resp, "GBK");
	}

	/**
	 * 重构：设置报文体长度和总长度
	 * 
	 * @param map
	 *            请求报文头
	 * @param requestBody
	 *            请求报文体
	 * @throws PluginException
	 *             异常
	 */
	private void putTotalLength(Map<JSCCBQuickReqHead, Object> map, String requestBody) throws PluginException {
		final String methodName = "putHeadAndBodyLength";
		String codeStyle = "GBK";
		// try {
		// map.put(JSCCBQuickReqHead.BodyLen,
		// requestBody.getBytes(codeStyle).length);
		// 签约请求报文头
		// String reqHead = formateEnumMap(map);
		// map.put(JSCCBQuickReqHead.TotalLen,
		// reqHead.getBytes(codeStyle).length
		// + requestBody.getBytes(codeStyle).length);
		// } catch (UnsupportedEncodingException e) {
		// LOGGER.error(methodName, "字节转换异常...");
		// throw new PluginException("字节转换异常...");
		// }
	}

	/**
	 * 发送请求
	 * 
	 * @param requsetString
	 *            请求报文
	 * @param host
	 *            请求主机地址
	 * @param port
	 *            端口号
	 * @return
	 * @throws PluginException
	 *             异常
	 */
	private String sendRequest(String requsetString, String host, String port) throws PluginException {
		final String methodName = "sendRequest";

		LOGGER.info(methodName, "请求报文：" + requsetString);
		int reqPort = Integer.parseInt(port);
		String code = "GBK";
		// 发送请求
		String resposeString = SocketConnectionUtil.sendSocketConnection(host, reqPort, requsetString, code, code);
		LOGGER.info(methodName, "响应数据： " + resposeString);

		if (null == resposeString || "".equals(resposeString)) {
			LOGGER.error(methodName, "银行响应报文为空");
			throw new PluginException(methodName + "error : 银行返回报文为空");
		}
		return resposeString;
	}

	/**
	 * 支付扣款、退款接口xml转换<br>
	 * XmlConverter工具类中的公共方法会将报文中的空格过滤掉，所以使用自定义的方法进行报文转换
	 * 
	 * @param obj
	 * @return
	 */
	private String objToXml(Object obj) {
		XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xstream.processAnnotations(obj.getClass());
		StringBuffer sb = new StringBuffer(xstream.toXML(obj));
		String reqXml = sb.toString();
		return reqXml;
	}

	/**
	 * 处理商户流水号
	 * 
	 * @param serialNo
	 *            商户号
	 * @return
	 */
	private String dealMerchantSerialNo(String serialNo) {
		int serialLength = serialNo.length();
		String serial = serialNo;
		// 大于9位的截取后9位
		if (serialLength > MERCHANT_SERIAL_NO_LENGTH) {
			serial = serialNo.substring(serialLength - MERCHANT_SERIAL_NO_LENGTH);
		}
		return serial;
	}

	/**
	 * 支付查询
	 * 
	 * @param serialNo
	 *            请求参数
	 * @return
	 * @throws PluginException
	 *             异常
	 */
	@Override
	public Integer checkOrder(QuickPayRequestData request) throws PluginException {
		Map<String, String> props = getProperties();
		// 1、组装报文体
		String requestBody = createCheckReqBody(request);
		// 2、组装报文头，利用定义的枚举类
		Map<JSCCBQuickReqHead, Object> headMap = createCheckReqString(request, requestBody);

		// 签约请求报文
		String requestCheckString = formateEnumMap(headMap) + requestBody;
		// 3、发送给银行请求
		String responseMsg = sendRequest(requestCheckString, props.get(SPECIAL_HOST), props.get(SPECIAL_PORT));

		// 4、校验银行返回的信息
		// 判断是否存在报文体
		Integer index = checkResponseBody(responseMsg);
		// 组织数据
		String respHead = responseMsg.substring(0, index);
		Map<EnumKeys, Object> map = parseString(JSCCBQuickResHead.class, respHead);
		// 响应报文体
		String respBody = responseMsg.substring(index);

		// 开始进行校验
		return verifyCheckOrder(respHead, respBody, map, request);
	}

	/**
	 * 组装支付查询的报文头
	 * 
	 * @param request
	 *            请求参数
	 * @param requestBody
	 *            请求报文体
	 * @return
	 * @throws PluginException
	 *             异常
	 */
	private Map<JSCCBQuickReqHead, Object> createCheckReqString(QuickPayRequestData request, String requestBody)
			throws PluginException {
		Map<String, String> props = getProperties();

		JSCCBQuickReqHead head = new JSCCBQuickReqHead();
		head.setTotalLen(12.0);
		head.setTradeCode("125");

		// Map<JSCCBQuickReqHead, Object> map = new HashMap<JSCCBQuickReqHead,
		// Object>();
		//
		// map.put(JSCCBQuickReqHead.TradeCode, props.get(CHECK_TRADE_CODE));
		// map.put(JSCCBQuickReqHead.TradeDate,
		// TimestampUtil.formatDays(request.getOrderTime()));
		// map.put(JSCCBQuickReqHead.TradeTime,
		// TimestampUtil.formatTimes(request.getOrderTime()));
		// map.put(JSCCBQuickReqHead.MerchantId, props.get(MERCHANT_ID));
		// map.put(JSCCBQuickReqHead.TerminalNo, props.get(TERMINAL));
		// map.put(JSCCBQuickReqHead.POS, props.get(POS));

		// 交易号
		String tradeNo = request.getTraceNo();

		// 商户流水号
		String merchantSerial = tradeNo;

		if (null == tradeNo) {
			String timeString = Long.toString(System.currentTimeMillis());
			// 商户流水号
			merchantSerial = timeString.substring(timeString.length() - MERCHANT_SERIAL_NO_LENGTH);
		}

		if (merchantSerial.length() < MERCHANT_SERIAL_NO_LENGTH) {
			for (int i = 0; i < MERCHANT_SERIAL_NO_LENGTH - merchantSerial.length(); i++) {
				merchantSerial = "0" + merchantSerial;
			}

		} else {
			merchantSerial = merchantSerial.substring(merchantSerial.length() - MERCHANT_SERIAL_NO_LENGTH);
		}

		map.put(JSCCBQuickReqHead.MerchantSerial, merchantSerial);

		String mac = sign(TimestampUtil.formatDays(request.getOrderTime()), props.get(MERCHANT_ID), merchantSerial,
				requestBody);
		map.put(JSCCBQuickReqHead.MAC, mac);
		putTotalLength(map, requestBody);
		return map;
	}

	/**
	 * 组装查询报文体
	 * 
	 * @param request
	 *            请求参数
	 * @return
	 */
	private String createCheckReqBody(QuickPayRequestData request) {
		Map<String, String> props = getProperties();

		JSCCBQuickReqBody checkReqBody = new JSCCBQuickReqBody();
		checkReqBody.setOrgTrxDate(TimestampUtil.formatDays(request.getOrderTime()));
		String orderId = request.getOrderId();
		if (orderId.length() > MERCHANT_SERIAL_NO_LENGTH) {
			checkReqBody.setOrgTraceNo(orderId.substring(orderId.length() - MERCHANT_SERIAL_NO_LENGTH));
		} else {
			checkReqBody.setOrgTraceNo(orderId);
		}
		checkReqBody.setOrgOrderNo(orderId);
		checkReqBody.setType(props.get(TRADE_NATURE_TYPE));
		checkReqBody.setCardType(props.get(CARD_TYPE));
		checkReqBody.setCustomId(request.getSignNo() == null ? "" : request.getSignNo());

		String signBody = XmlConvertUtil.objToXml(checkReqBody);
		String requestString = XML_HEAD + signBody;
		return requestString;
	}

	/**
	 * 拆分主方法，重构银行返回结果的校验
	 * 
	 * @param respHead
	 *            响应报文头
	 * @param respBody
	 *            响应报文体
	 * @param map
	 *            枚举类型Map
	 * @param request
	 *            请求参数
	 * @return
	 * @throws PluginException
	 *             异常
	 */
	private Integer verifyCheckOrder(String respHead, String respBody, Map<EnumKeys, Object> map,
			QuickPayRequestData request) throws PluginException {
		final String methodName = "verifyCheckOrder";

		JSCCBQuickRespBody checkRespBody = XmlConvertUtil.xmlToObj(respBody, JSCCBQuickRespBody.class);

		// 4.1、校验mac字段
		verifySign(map, respBody, (String) map.get(JSCCBQuickResHead.MAC));

		// 查询操作码
		String checkReqCode = (String) map.get(JSCCBQuickResHead.ReturnCode);
		String checkReqMessage = (String) map.get(JSCCBQuickResHead.ReturnMsg);

		// 交易性质
		String type = checkRespBody.getType();

		// 查询到的这笔交易的操作码
		String checkPayCode = checkRespBody.getOrgRetCode();
		String checkPayMessage = checkRespBody.getOrgMessage();

		// 请求的金额
		BigDecimal reqAmount = request.getPaymentAmount();
		// 响应的金额
		String respAmountStr = checkRespBody.getTxAmount();
		if (null == respAmountStr || respAmountStr.length() == 0) {
			respAmountStr = "0";
		}
		BigDecimal respAmount = new BigDecimal(respAmountStr);

		// 最终状态
		String status = checkRespBody.getStatus();

		Integer ret = null;

		// 4.2、判断查询操作的返回码
		if (!OPER_SUCCESS_CODE.equals(checkReqCode)) {
			setBankErrorCode(checkReqCode);
			setBankErrorMsg(checkReqMessage);
			ret = CommonConstants.CHECK_RESULT_FAILED;
		}
		// 4.3、判断交易性质：如果不是支付的则抛出异常
		else if (!getProperties().get(TRADE_NATURE_TYPE).equals(type)) {
			LOGGER.error(methodName, "响应的交易性质校验失败！");
			ret = CommonConstants.CHECK_RESULT_FAILED;
		}
		// 4.4、检查查询返回的支付操作的返回码
		else if (!OPER_SUCCESS_CODE.equals(checkPayCode)) {
			setBankErrorCode(checkPayCode);
			setBankErrorMsg(checkPayMessage);
			ret = CommonConstants.CHECK_RESULT_FAILED;
		}
		// 4.5、判断请求交易的金额
		else if (!(request.getOrderId().equals(checkRespBody.getOrgOrderNo().trim()))) {
			LOGGER.error(methodName, "请求的订单号和响应的订单号不相同！");
			ret = CommonConstants.CHECK_RESULT_FAILED;
		}
		// 4.6、判断订单号
		else if (!(reqAmount.compareTo(respAmount) == 0)) {
			LOGGER.error(methodName, "请求的金额和响应的金额不相同！");
			ret = CommonConstants.CHECK_RESULT_FAILED;
		}
		// 4.7、判断最终的交易状态
		else if (CHECK_STATUS_DEALING.equals(status) || CHECK_STATUS_FAIL.equals(status)) {
			ret = CommonConstants.CHECK_RESULT_NOPAY;
		} else {
			ret = CommonConstants.CHECK_RESULT_SUCCESS;
		}
		return ret;
	}

	/**
	 * 签名公用方法
	 * 
	 * @param orderTime
	 *            订单时间
	 * @param merchantId
	 *            商户号
	 * @param merchantSerial
	 *            商户流水号
	 * @param requestBody
	 *            报文体
	 * @return
	 * @throws PluginException
	 */
	private String sign(String orderTime, String merchantId, String merchantSerial, String requestBody)
			throws PluginException {
		final String methodName = "sign";

		StringBuffer buffer = new StringBuffer();
		String mac = null;
		try {
			// 签名原串：交易日期+商户号+商户流水号+报文体
			buffer.append(orderTime).append(merchantId).append(merchantSerial).append(requestBody);
			mac = EncryptionDecryptionUtil.encryptByMD5ToUpCase(buffer.toString(), "GBK".toUpperCase());
		} catch (PluginException e) {
			LOGGER.error(methodName, "签名时MD5加密错误...");
			throw new PluginException("签名时MD5加密错误...");
		}
		return mac;
	}

	/**
	 * 公用方法：对银行返回回来的报文进行签名，来做验签
	 * 
	 * @param map
	 *            返回的报文头
	 * @param respBody
	 *            返回的报文体
	 * @param bankMac
	 *            银行返回的MAC
	 * @return
	 * @throws PluginException
	 */
	private void verifySign(Map<EnumKeys, Object> map, String respBody, String bankMac) throws PluginException {
		final String methodName = "verifySign";

		StringBuffer buffer = new StringBuffer();
		String mac = null;
		try {
			buffer.append(map.get(JSCCBQuickResHead.TradeDate)).append(map.get(JSCCBQuickResHead.MerchantSerial))
					.append(map.get(JSCCBQuickResHead.ReturnCode)).append(respBody);
			mac = EncryptionDecryptionUtil.encryptByMD5ToUpCase(buffer.toString(), "GBK");
		} catch (PluginException e) {
			LOGGER.error(methodName, "验签时MD5加密错误...");
			throw new PluginException("验签时MD5加密错误...");
		}

		// 如果验签失败 ，抛出异常
		if (!mac.equals(bankMac)) {
			LOGGER.error(methodName, "验签失败！");
			throw new PluginException("验签失败");
		}
	}

	/**
	 * 校验返回到报文中是否存在报文体
	 * 
	 * @param responseMsg
	 *            响应报文
	 * @return
	 * @throws LogicCommunicationException
	 */
	private Integer checkResponseBody(String responseMsg) throws PluginException {
		final String methodName = "checkResponseBody";

		int index = responseMsg.indexOf(XML_HEAD);

		// 4.0、如果没有报文体，则直接抛出异常
		if (-1 == index) {
			LOGGER.error(methodName, "响应的返回没有报文体！");
			throw new PluginException("响应的返回没有报文体！");
		}
		return index;
	}

	@Override
	public Integer refundOrder(QuickPayRequestData request) throws PluginException {
		// TODO Auto-generated method stub
		return null;
	}

}
