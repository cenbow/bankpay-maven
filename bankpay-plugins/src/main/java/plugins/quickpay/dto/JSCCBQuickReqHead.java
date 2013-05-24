package plugins.quickpay.dto;

import java.lang.reflect.Field;

import common.plugins.annotation.ConfirmLongField;

/**
 * 
 * 特色接口请求报文头
 */
public class JSCCBQuickReqHead {

	@ConfirmLongField(length = "4")
	private Double totalLen;

	@ConfirmLongField(length = "6")
	private String tradeCode;

	public Double getTotalLen() {
		return totalLen;
	}

	public void setTotalLen(Double totalLen) {
		this.totalLen = totalLen;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public static void main(String[] args) {
		Class clazz = JSCCBQuickReqHead.class;

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(ConfirmLongField.class)) {
				System.out.println(field.getName());
				ConfirmLongField annoField = field.getAnnotation(ConfirmLongField.class);
				System.out.println(annoField.length());
			}
		}

	}

	// /** 报文总长度 */
	// TotalLen(4.0, Double.class),
	// /** 交易代码 */
	// TradeCode(6.0, String.class),
	// /** 交易日期 */
	// TradeDate(8.0, String.class),
	// /** 交易时间 */
	// TradeTime(6.0, String.class),
	// /** 商户编号 */
	// MerchantId(15.0, String.class),
	// /** 终端号 */
	// TerminalNo(8.0, String.class),
	// /** POS编号 */
	// POS(4.0, String.class),
	// /** 商户流水号 */
	// MerchantSerial(9.0, String.class),
	// /** MAC */
	// MAC(32.0, String.class),
	// /** 报文体长度 */
	// BodyLen(4.0, Double.class);

	// private Double length;
	// private Class<?> type;
	//
	// JSCCBQuickReqHead(Double len, Class<?> t) {
	// this.length = len;
	// this.type = t;
	// }
	//
	// public Double getLength() {
	// return length;
	// }
	//
	// public Class<?> getType() {
	// return type;
	// }

}
