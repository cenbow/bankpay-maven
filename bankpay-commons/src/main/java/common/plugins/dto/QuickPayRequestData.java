package common.plugins.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class QuickPayRequestData implements Serializable {

	private static final long serialVersionUID = -2053599516024125010L;

	/** 商户协议号 */
	private String signNo;

	/** 银行协议号 */
	private String bankSignNo;

	/** 银行标识 **/
	private String pluginName;

	/** 银行卡户名 */
	private String cardName;

	/** 银行卡卡号 */
	private String cardNo;

	/** 银行卡类型 */
	private String cardType;

	/** 证件类型 */
	private String certType;

	/** 证件号码 */
	private String certNo;

	/** 手机号码 */
	private String mobile;

	/** 分期期数 */
	private Integer installment;

	/** 客户手续费 */
	private String cifFee;

	/** 订单号 */
	private String orderId;

	/** 银行原交易流水号 */
	private String originSerialNo;

	/** 原交易日期和时间 */
	private Timestamp originTime;

	/** 退款单号 */
	private String refundOrderId;

	/** 短信序列号 */
	private String smsSessionId;

	/** 短信验证码 */
	private String smsCertCode;

	/** 交易日期和时间 */
	private Timestamp orderTime;

	/** 有效期 */
	private String expDate;

	/** CVV2 */
	private String cvv2;

	/** 支付金额 */
	private BigDecimal paymentAmount;

	/** 退款 */
	private BigDecimal refundAmount;

	/** 币种 */
	private String curCode;

	/** 商品描述 */
	private String goods;

	/** 跟踪号 */
	private String traceNo;

	/** 密钥 */
	private String privateKey;

	/** The other required parameters. */
	private Map<String, Object> parameters;

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getBankSignNo() {
		return bankSignNo;
	}

	public void setBankSignNo(String bankSignNo) {
		this.bankSignNo = bankSignNo;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getInstallment() {
		return installment;
	}

	public void setInstallment(Integer installment) {
		this.installment = installment;
	}

	public String getCifFee() {
		return cifFee;
	}

	public void setCifFee(String cifFee) {
		this.cifFee = cifFee;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOriginSerialNo() {
		return originSerialNo;
	}

	public void setOriginSerialNo(String originSerialNo) {
		this.originSerialNo = originSerialNo;
	}

	public Timestamp getOriginTime() {
		return originTime;
	}

	public void setOriginTime(Timestamp originTime) {
		this.originTime = originTime;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getSmsSessionId() {
		return smsSessionId;
	}

	public void setSmsSessionId(String smsSessionId) {
		this.smsSessionId = smsSessionId;
	}

	public String getSmsCertCode() {
		return smsCertCode;
	}

	public void setSmsCertCode(String smsCertCode) {
		this.smsCertCode = smsCertCode;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
