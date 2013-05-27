package data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class UnionPayRequestData implements Serializable {

	private static final long serialVersionUID = -3642969726159605368L;

	/** 插件名称 */
	private String pluginName;

	/** 订单号 */
	private String orderId;

	/** 币种 */
	private String curCode;

	/** 金额 */
	private BigDecimal amount;

	/** 订单时间 */
	private Timestamp orderTime;

	/** 分期付款 */
	private Integer installment;

	/** 银行代码 */
	private String bankCode;

	/** 客户IP */
	private String clientIP;

	/** The other required parameters. */
	private Map<String, ?> parameters;

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getInstallment() {
		return installment;
	}

	public void setInstallment(Integer installment) {
		this.installment = installment;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public Map<String, ?> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, ?> parameters) {
		this.parameters = parameters;
	}

}
