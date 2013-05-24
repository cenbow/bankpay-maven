package plugins.quickpay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 特色接口请求 报文体
 */
@XStreamAlias("Req")
public class JSCCBQuickReqBody {

	/** 银行卡号 */
	@XStreamAlias("CardNo")
	private String cardNo;
	/** 持卡人姓名 */
	@XStreamAlias("CustName")
	private String custName;
	/** 银行卡类型 */
	@XStreamAlias("CardType")
	private String cardType;
	/** 证件类型 */
	@XStreamAlias("IDType")
	private String idType;
	/** 证件号码 */
	@XStreamAlias("IDNo")
	private String idNo;
	/** 手机号码 */
	@XStreamAlias("MobilePhone")
	private String mobilePhone;
	/** 商户授权号 */
	@XStreamAlias("CustomId")
	private String customId;
	/** 原交易日期 */
	@XStreamAlias("OrgTrxDate")
	private String orgTrxDate;
	/** 原交易流水号 */
	@XStreamAlias("OrgTraceNo")
	private String orgTraceNo;
	/** 原银行订单号 */
	@XStreamAlias("OrgOrderNo")
	private String orgOrderNo;
	/** 交易性质 */
	@XStreamAlias("Type")
	private String type;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	/**
	 * @return the orgTrxDate
	 */
	public String getOrgTrxDate() {
		return orgTrxDate;
	}

	/**
	 * @param orgTrxDate
	 *            the orgTrxDate to set
	 */
	public void setOrgTrxDate(String orgTrxDate) {
		this.orgTrxDate = orgTrxDate;
	}

	/**
	 * @return the orgTraceNo
	 */
	public String getOrgTraceNo() {
		return orgTraceNo;
	}

	/**
	 * @param orgTraceNo
	 *            the orgTraceNo to set
	 */
	public void setOrgTraceNo(String orgTraceNo) {
		this.orgTraceNo = orgTraceNo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the orgOrderNo
	 */
	public String getOrgOrderNo() {
		return orgOrderNo;
	}

	/**
	 * @param orgOrderNo
	 *            the orgOrderNo to set
	 */
	public void setOrgOrderNo(String orgOrderNo) {
		this.orgOrderNo = orgOrderNo;
	}

}
