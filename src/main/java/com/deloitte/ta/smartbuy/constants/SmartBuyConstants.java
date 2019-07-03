package com.deloitte.ta.smartbuy.constants;

public class SmartBuyConstants {

	// Server Constants
	public static final String SERVICE_URL = "http://localhost:8090/smartbuydb";

	// Text Constants
	public static final String ORDER_PLACED = "Your order has been successfully placed";
	public static final String ORDER_CANCELLED = "Your order has been cancelled";
	public static final String INVALID_ORDER_ID = "Invalid order ID";

	// Error Constants
	public static final int PRODUCT_NOT_AVAILABLE_CODE = 4100;
	public static final String PRODUCT_NOT_AVAILABLE = "Product is currently not available";
	public static final int LOW_QUANTITY_CODE = 4200;
	public static final String LOW_QUANTITY_MESSAGE = "Not enough items are available in the repository";
	public static final int LOW_BALANCE_CODE = 4300;
	public static final String LOW_BALANCE_MESSAGE = "Your wallet balance is low. Please recharge and try again";
}
