package com.deloitte.ta.smartbuy.exception;

public class OrderException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorCode;

	public OrderException(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
