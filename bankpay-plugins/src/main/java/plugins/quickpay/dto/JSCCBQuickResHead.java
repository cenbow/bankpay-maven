package plugins.quickpay.dto;

import common.plugins.keys.EnumKeys;

/**
 * 
 * 特色接口响应报文头
 */
public enum JSCCBQuickResHead implements EnumKeys {
	/** 交易代码 */
	TradeCode(6.0, String.class),
	/** 返回代码 */
	ReturnCode(4.0, String.class),
	/** 错误信息 */
	ReturnMsg(40.0, String.class),
	/** 交易日期 */
	TradeDate(8.0, String.class),
	/** 交易时间 */
	TradeTime(6.0, String.class),
	/** 商户流水号 */
	MerchantSerial(9.0, String.class),
	/** MAC */
	MAC(32.0, String.class),
	/** 报文体长度 */
	BodyLen(4.0, Double.class);

	private Double length;
	private Class<?> type;

	JSCCBQuickResHead(Double len, Class<?> t) {
		this.length = len;
		this.type = t;
	}

	public Double getLength() {
		return length;
	}

	public Class<?> getType() {
		return type;
	}

}
