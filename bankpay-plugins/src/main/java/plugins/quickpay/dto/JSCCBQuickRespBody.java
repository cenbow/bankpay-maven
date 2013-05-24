package plugins.quickpay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 用户银行返回的报文体进行字段拼装的DTO数据类<br>
 * 
 * 
 * @author 13040443
 */
@XStreamAlias("Resp")
public class JSCCBQuickRespBody {

    /** 银行交易日期 */
    @XStreamAlias("TrxDate")
    private String trxDate;
    /** 银行交易时间 */
    @XStreamAlias("TrxTime")
    private String trxTime;
    /** 币种 */
    @XStreamAlias("TrxcurType")
    private String trxcurType;
    /** 交易金额 */
    @XStreamAlias("TxAmount")
    private String txAmount;
    /** 原交易流水号 */
    @XStreamAlias("OrgTraceNo")
    private String orgTraceNo;
    /** 原订单编号 */
    @XStreamAlias("OrgOrderNo")
    private String orgOrderNo;
    /** 银行卡号 */
    @XStreamAlias("CardNo")
    private String cardNo;
    /** 银行卡类型 */
    @XStreamAlias("CardType")
    private String cardType;
    /** 原交易返回代码说明 */
    @XStreamAlias("OrgMessage")
    private String orgMessage;
    /** 备注 */
    @XStreamAlias("Addword")
    private String addword;
    /** 交易性质 */
    @XStreamAlias("Type")
    private String type;
    /** 原交易处理结果返回码 */
    @XStreamAlias("OrgRetCode")
    private String orgRetCode;
    /** 交易状态 */
    @XStreamAlias("Status")
    private String status;

    public String getAddword() {
        return addword;
    }

    public void setAddword(String addword) {
        this.addword = addword;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the orgRetCode
     */
    public String getOrgRetCode() {
        return orgRetCode;
    }

    /**
     * @param orgRetCode the orgRetCode to set
     */
    public void setOrgRetCode(String orgRetCode) {
        this.orgRetCode = orgRetCode;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the trxDate
     */
    public String getTrxDate() {
        return trxDate;
    }

    /**
     * @param trxDate the trxDate to set
     */
    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    /**
     * @return the trxTime
     */
    public String getTrxTime() {
        return trxTime;
    }

    /**
     * @param trxTime the trxTime to set
     */
    public void setTrxTime(String trxTime) {
        this.trxTime = trxTime;
    }

    /**
     * @return the trxcurType
     */
    public String getTrxcurType() {
        return trxcurType;
    }

    /**
     * @param trxcurType the trxcurType to set
     */
    public void setTrxcurType(String trxcurType) {
        this.trxcurType = trxcurType;
    }

    /**
     * @return the txAmount
     */
    public String getTxAmount() {
        return txAmount;
    }

    /**
     * @param txAmount the txAmount to set
     */
    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    /**
     * @return the orgTraceNo
     */
    public String getOrgTraceNo() {
        return orgTraceNo;
    }

    /**
     * @param orgTraceNo the orgTraceNo to set
     */
    public void setOrgTraceNo(String orgTraceNo) {
        this.orgTraceNo = orgTraceNo;
    }

    /**
     * @return the orgOrderNo
     */
    public String getOrgOrderNo() {
        return orgOrderNo;
    }

    /**
     * @param orgOrderNo the orgOrderNo to set
     */
    public void setOrgOrderNo(String orgOrderNo) {
        this.orgOrderNo = orgOrderNo;
    }

    /**
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return the orgMessage
     */
    public String getOrgMessage() {
        return orgMessage;
    }

    /**
     * @param orgMessage the orgMessage to set
     */
    public void setOrgMessage(String orgMessage) {
        this.orgMessage = orgMessage;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

}
