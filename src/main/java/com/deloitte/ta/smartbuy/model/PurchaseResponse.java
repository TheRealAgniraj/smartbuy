package com.deloitte.ta.smartbuy.model;

public class PurchaseResponse {

	private SuccessResponse successResponse;
	private FailureResponse failureResponse;

	public SuccessResponse getSuccessResponse() {
		return successResponse;
	}

	public void setSuccessResponse(SuccessResponse successResponse) {
		this.successResponse = successResponse;
	}

	public FailureResponse getFailureResponse() {
		return failureResponse;
	}

	public void setFailureResponse(FailureResponse failureResponse) {
		this.failureResponse = failureResponse;
	}

}
