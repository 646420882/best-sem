package com.perfect.autosdk.exception;

/**
 * 
 * @author Baidu API Team (apihelp@baidu.com)
 * 
 * @version 1.0.0, $Date: 2013-5-9$
 * 
 */
public class SDKParseException extends Exception {

	private static final long serialVersionUID = 638233461903665217L;

	public SDKParseException() {
		super();
	}

	public SDKParseException(String msg) {
		super(msg);
	}

	public SDKParseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public SDKParseException(Throwable cause){
		super(cause);
	}
	
	public SDKParseException(String msg, int errorCode) {
		super("ErrorCode : " + errorCode + ", info : " + msg);
	}

}
