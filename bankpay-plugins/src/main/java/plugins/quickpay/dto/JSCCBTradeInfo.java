package plugins.quickpay.dto;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 江苏建行报文体
 */
@XStreamAlias("TX_INFO")
public class JSCCBTradeInfo {

    /** 本方企业账户 */
    @XStreamAlias("ACC_NO1")
    private String accountNo;
    /** 代发代扣编号 */
    @XStreamAlias("BILL_CODE")
    private String billCode;
    /** 账户 */
    @XStreamAlias("ACC_NO2")
    private String cardNo;
    /** 姓名 */
    @XStreamAlias("OTHER_NAME")
    private String custName;
    /** 金额 */
    @XStreamAlias("AMOUNT")
    private BigDecimal amount;
    /** 用途编号 */
    @XStreamAlias("USEOF_CODE")
    private String useofCode;
    /** 网银审批标识 */
    @XStreamAlias("FLOW_FLAG")
    private String flowFlag;
    /** 对方账户支付系统行号 */
    @XStreamAlias("UBANK_NO")
    private String ubankNo;
    /** 备注1 */
    @XStreamAlias("REM1")
    private String rem1;
    /** 备注2 */
    @XStreamAlias("REM2")
    private String rem2;
    /** 凭证号 */
    @XStreamAlias("CREDIT_NO")
    private String creditNo;
    /** 自定义输出名称1 */
    @XStreamAlias("INDIVIDUAL_NAME1")
    private String individualName1;
    /** 自定义输出内容1 */
    @XStreamAlias("INDIVIDUAL1")
    private String individual1;
    /** 自定义输出名称2 */
    @XStreamAlias("INDIVIDUAL_NAME2")
    private String individualName2;
    /** 自定义输出内容2 */
    @XStreamAlias("INDIVIDUAL2")
    private String individual2;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUseofCode() {
        return useofCode;
    }

    public void setUseofCode(String useofCode) {
        this.useofCode = useofCode;
    }

    public String getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }

    public String getUbankNo() {
        return ubankNo;
    }

    public void setUbankNo(String ubankNo) {
        this.ubankNo = ubankNo;
    }

    public String getRem1() {
        return rem1;
    }

    public void setRem1(String rem1) {
        this.rem1 = rem1;
    }

    public String getRem2() {
        return rem2;
    }

    public void setRem2(String rem2) {
        this.rem2 = rem2;
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getIndividualName1() {
        return individualName1;
    }

    public void setIndividualName1(String individualName1) {
        this.individualName1 = individualName1;
    }

    public String getIndividual1() {
        return individual1;
    }

    public void setIndividual1(String individual1) {
        this.individual1 = individual1;
    }

    public String getIndividualName2() {
        return individualName2;
    }

    public void setIndividualName2(String individualName2) {
        this.individualName2 = individualName2;
    }

    public String getIndividual2() {
        return individual2;
    }

    public void setIndividual2(String individual2) {
        this.individual2 = individual2;
    }

}
