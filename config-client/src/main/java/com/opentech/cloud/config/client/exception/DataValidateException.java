package com.opentech.cloud.config.client.exception;

/**
 * 
 * @author sihai
 *
 */
public class DataValidateException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8046770117316302800L;

	/**
	 * 
	 * @param errorMsg
	 */
	public DataValidateException(String errorMsg) {
		super(errorMsg);
	}
	
}
