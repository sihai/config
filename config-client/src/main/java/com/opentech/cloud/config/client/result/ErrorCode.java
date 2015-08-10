package com.opentech.cloud.config.client.result;

/**
 * 错误码枚举
 * @author sihai
 *
 */
public enum ErrorCode {
	
	SUCCEED("succeed", "成功"),
	NETWORK_ERROR("network_error", "网络错误"),
	WRONG_PARAMETER("wrong_parameter", "参数错误: %s"),
	UNKNOWN_ERROR("unknown_error", "未知错误");
	
	/**
	 * 
	 */
	private final String code;

	/**
	 * 
	 */
	private final String msg;
	
	/**
	 * 
	 * @param code
	 * @param msg
	 */
	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}