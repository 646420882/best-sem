package com.perfect.autosdk.exception;

/**
 * 
 * @author Baidu API Team (apihelp@baidu.com)
 * 
 * @version 1.0.0, $Date: 2013-5-9$
 * 
 */
public class ApiException extends Exception {

	private static final long serialVersionUID = 638241177122665217L;

	public ApiException() {
		super();
	}

	public ApiException(String msg) {
		super(msg);
	}

	public ApiException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ApiException(Throwable cause){
		super(cause);
	}
	
	public ApiException(String msg, int errorCode) {
		super("ErrorCode : " + errorCode + ", info : " + msg);
	}

}
