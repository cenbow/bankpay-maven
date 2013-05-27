package data.dto;

import java.io.Serializable;
import java.util.Map;

public class ResultData implements Serializable {

	private static final long serialVersionUID = -752186404597830940L;

	/** 错误信息 */
	private String errorMsg;

	/** 错误码 */
	private String errorCode;

	/** 返回结果对象 */
	private Object resultObject;

	/** 执行结果是否成功 */
	private Boolean success;

	/** 返回其他的响应值 */
	private Map<String, ?> response;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Map<String, ?> getResponse() {
		return response;
	}

	public void setResponse(Map<String, ?> response) {
		this.response = response;
	}

}
