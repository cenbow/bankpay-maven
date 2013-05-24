package plugins.quickpay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 江苏建行交易报文类：支付，退款
 */
public class JSCCBQuickTradePacket {

	/** 请求序列号 */
	@XStreamAlias("REQUEST_SN")
	private String requestSn;
	/** 客户号 */
	@XStreamAlias("CUST_ID")
	private String custId;
	/** 操作员号 */
	@XStreamAlias("USER_ID")
	private String userId;
	/** 密码 */
	@XStreamAlias("PASSWORD")
	private String password;
	/** 交易码 */
	@XStreamAlias("TX_CODE")
	private String txCode;
	/** 语言 */
	@XStreamAlias("LANGUAGE")
	private String language;
	/** 交易信息 */
	@XStreamAlias("TX_INFO")
	private JSCCBTradeInfo tradeInfo;
	/** 签名信息 */
	@XStreamAlias("SIGN_INFO")
	private String signInfo;
	/** 签名CA信息 */
	@XStreamAlias("SIGNCERT")
	private String signCert;
	/** 响应码 */
	@XStreamAlias("RETURN_CODE")
	private String returnCode;
	/** 响应信息 */
	@XStreamAlias("RETURN_MSG")
	private String returnMsg;

	public String getRequestSn() {
		return requestSn;
	}

	public void setRequestSn(String requestSn) {
		this.requestSn = requestSn;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public JSCCBTradeInfo getTradeInfo() {
		return tradeInfo;
	}

	public void setTradeInfo(JSCCBTradeInfo tradeInfo) {
		this.tradeInfo = tradeInfo;
	}

	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	public String getSignCert() {
		return signCert;
	}

	public void setSignCert(String signCert) {
		this.signCert = signCert;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
