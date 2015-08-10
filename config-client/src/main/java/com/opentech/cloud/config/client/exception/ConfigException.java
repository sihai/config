package com.opentech.cloud.config.client.exception;

/**
 * 
 * @author sihai
 *
 */
public class ConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5654300373610183081L;

	/**
	 * 
	 */
	private final String errorCode;
	
	/**
	 * 
	 */
	private final String errorMsg;
	
	/**
	 * 
	 * @param errorCode
	 * @param errorMsg
	 */
	public ConfigException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}