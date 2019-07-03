package com.deloitte.ta.smartbuy.service;

import com.deloitte.ta.smartbuy.model.OrderPayload;
import com.deloitte.ta.smartbuy.model.Product;
import com.deloitte.ta.smartbuy.model.Products;
import com.deloitte.ta.smartbuy.model.PurchaseResponse;

public interface SmartBuyService {

	public Products getAllProducts();

	public Product getProductDetails(String productID);

	public PurchaseResponse placeOrder(OrderPayload payload);

	public String cancelOrder(String orderID);

}
