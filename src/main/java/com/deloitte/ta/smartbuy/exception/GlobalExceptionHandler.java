package com.deloitte.ta.smartbuy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deloitte.ta.smartbuy.model.FailureResponse;
import com.deloitte.ta.smartbuy.model.PurchaseResponse;

import static com.deloitte.ta.smartbuy.constants.SmartBuyConstants.*;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = OrderException.class)
	public ResponseEntity<PurchaseResponse> handleOrderException(OrderException exception) {

		FailureResponse failureResponse = new FailureResponse();
		PurchaseResponse purchaseResponse = new PurchaseResponse();

		if (PRODUCT_NOT_AVAILABLE_CODE == exception.getErrorCode()) {
			failureResponse.setErrorCode(PRODUCT_NOT_AVAILABLE_CODE);
			failureResponse.setErrorMessage(PRODUCT_NOT_AVAILABLE);
			purchaseResponse.setFailureResponse(failureResponse);
		} else if (LOW_BALANCE_CODE == exception.getErrorCode()) {
			failureResponse.setErrorCode(LOW_BALANCE_CODE);
			failureResponse.setErrorMessage(LOW_BALANCE_MESSAGE);
			purchaseResponse.setFailureResponse(failureResponse);
		} else if (LOW_QUANTITY_CODE == exception.getErrorCode()) {
			failureResponse.setErrorCode(LOW_QUANTITY_CODE);
			failureResponse.setErrorMessage(LOW_QUANTITY_MESSAGE);
			purchaseResponse.setFailureResponse(failureResponse);
		}

		return new ResponseEntity<>(purchaseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = ConnectException.class)
	public ResponseEntity<Object> handleOrderException(ConnectException exception) {
		return new ResponseEntity<>("Our server is down. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
