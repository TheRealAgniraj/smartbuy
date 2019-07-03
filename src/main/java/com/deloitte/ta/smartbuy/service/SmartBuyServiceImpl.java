package com.deloitte.ta.smartbuy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.deloitte.ta.smartbuy.exception.OrderException;
import com.deloitte.ta.smartbuy.model.OrderPayload;
import com.deloitte.ta.smartbuy.model.Orders;
import com.deloitte.ta.smartbuy.model.Product;
import com.deloitte.ta.smartbuy.model.Products;
import com.deloitte.ta.smartbuy.model.PurchaseResponse;
import com.deloitte.ta.smartbuy.model.SuccessResponse;

import static com.deloitte.ta.smartbuy.constants.SmartBuyConstants.*;

import java.util.Optional;

@Service
public class SmartBuyServiceImpl implements SmartBuyService {

	@Value("${productsprovider.url}")
	String PRODUCTS_PROVIDER_URL;

	@Value("${ordersprovider.url}")
	String ORDERS_PROVIDER_URL;

	WebClient client = WebClient.create();

	@Override
	public Products getAllProducts() {

		Products productsAvailable = client.get().uri(PRODUCTS_PROVIDER_URL).retrieve().bodyToMono(Products.class)
				.block();
		return productsAvailable;
	}

	@Override
	public Product getProductDetails(String productID) {

		// Product Object To Be Returned
		Product product = null;

		// Constructing Request & Hitting an External Service
		Products productsAvailable = client.get().uri(PRODUCTS_PROVIDER_URL).retrieve().bodyToMono(Products.class)
				.block();

		// Check If The Product Is Available Or Not
		if (null != productsAvailable) {
			Optional<Product> userRequestedProduct = productsAvailable.getProduct().stream()
					.filter(prod -> prod.getProductID().equals(productID)).findFirst();
			if (userRequestedProduct.isPresent()) {
				product = userRequestedProduct.get();
			}
		}

		return product;
	}

	@Override
	public PurchaseResponse placeOrder(OrderPayload payload) {

		SuccessResponse successResponse = new SuccessResponse();
		PurchaseResponse orderResponse = new PurchaseResponse();

		// Constructing Request & Hitting an External Service
		Products productsAvailable = client.get().uri(PRODUCTS_PROVIDER_URL).retrieve().bodyToMono(Products.class)
				.block();

		// Product -> Available && Wallet Balance > Price ==> Place Order
		Optional<Product> userRequestedProduct = productsAvailable.getProduct().stream()
				.filter(prod -> prod.getProductID().equals(payload.getProductID())).findFirst();
		if (userRequestedProduct.isPresent()) {
			Product product = userRequestedProduct.get();
			if (product.getAvailableQuantity() >= payload.getQuantity()) {
				if (payload.getWalletBalance() >= product.getPrice() * payload.getQuantity()) {
					successResponse.setOrderID(String.valueOf(Math.round(Math.random() * 10000)));
					successResponse.setProductID(product.getProductID());
					successResponse.setMessage(ORDER_PLACED);
					successResponse.setRemainingBalance(
							payload.getWalletBalance() - (product.getPrice() * payload.getQuantity()));
					orderResponse.setSuccessResponse(successResponse);
				} else {
					throw new OrderException(LOW_BALANCE_CODE);
				}
			} else {
				throw new OrderException(LOW_QUANTITY_CODE);
			}
		} else {
			throw new OrderException(PRODUCT_NOT_AVAILABLE_CODE);
		}

		return orderResponse;
	}

	@Override
	public String cancelOrder(String orderID) {

		// Constructing Request & Hitting an External Service
		Orders ordersPlaced = client.get().uri(ORDERS_PROVIDER_URL).retrieve().bodyToMono(Orders.class).block();

		boolean isOrderIDValid = ordersPlaced.getOrderIDs().stream().filter(order -> orderID.equals(order)).findAny()
				.isPresent();

		if (isOrderIDValid) {
			return ORDER_CANCELLED;
		}
		return INVALID_ORDER_ID;
	}
}
