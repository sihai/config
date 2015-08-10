package com.opentech.cloud.config.client.result;


/**
 * 结果工厂
 * @author sihai
 *
 */
public class ResultFactory {

	/**
	 * 
	 * @return
	 */
	public static final Result succeed() {
		return ResultFactory.succeed(null);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static final Result succeed(Object data) {
		return ResultFactory.result(ErrorCode.SUCCEED.getCode(), ErrorCode.SUCCEED.getMsg(), data);
	}
	
	/**
	 * 
	 * @param ec
	 * @return
	 */
	public static final Result failed(ErrorCode ec) {
		return ResultFactory.failed(ec, null);
	}
	
	/**
	 * 
	 * @param ec
	 * @param args
	 * @return
	 */
	public static final Result failed(ErrorCode ec, String[] args) {
		return ResultFactory.failed(ec.getCode(), ec.getMsg(), args);
	}
	
	/**
	 * 
	 * @param code
	 * @param msgTemplate
	 * @return
	 */
	public static final Result failed(String code, String msgTemplate) {
		return ResultFactory.failed(code, msgTemplate, null);
	}
	
	/**
	 * 
	 * @param code
	 * @param msgTemplate
	 * @param args
	 * @return
	 */
	public static final Result failed(String code, String msgTemplate, Object[] args) {
		String msg = null;
		if(null == args || 0 == args.length) {
			msg = msgTemplate;
		} else {
			msg = String.format(msgTemplate, args);
		}
		return ResultFactory.result(code, msg, null);
	}
	
	/**
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static final Result result(String code, String msg, Object data) {
		return new Result(code, msg, data);
	}
}
