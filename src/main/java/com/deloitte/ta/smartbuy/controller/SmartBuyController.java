package com.deloitte.ta.smartbuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.ta.smartbuy.model.OrderPayload;
import com.deloitte.ta.smartbuy.model.Product;
import com.deloitte.ta.smartbuy.model.Products;
import com.deloitte.ta.smartbuy.model.PurchaseResponse;
import com.deloitte.ta.smartbuy.service.SmartBuyService;

import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import static com.deloitte.ta.smartbuy.constants.SmartBuyConstants.*;

import javax.validation.Valid;

@RestController
public class SmartBuyController {

	@Autowired
	SmartBuyService smartBuyService;

	// This service is used to retrieve all products
	@GetMapping("/getallproducts")
	public Products getAllProducts() {
		return smartBuyService.getAllProducts();
	}

	// This service is used to retrieve a product requested by users
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is available", response = Product.class),
			@ApiResponse(code = 204, message = "Product is not available", response = String.class) })
	@GetMapping("/getdetails/{productID}")
	public ResponseEntity<Object> getProductDetails(@PathVariable("productID") String productID) {
		Product product = smartBuyService.getProductDetails(productID);
		if (null != product) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}

		return new ResponseEntity<>(PRODUCT_NOT_AVAILABLE, HttpStatus.NO_CONTENT);
	}

	// This service is used to place an order
	@PostMapping(value = "/placeorder", consumes = "application/json", produces = "application/json")
	public PurchaseResponse placeOrder(@RequestBody @Valid OrderPayload payload) {
		return smartBuyService.placeOrder(payload);
	}

	// This service is used to cancel the order
	@GetMapping("/cancelorder")
	public String cancelOrder(@RequestParam(name = "orderID", required = true) String orderID) {
		return smartBuyService.cancelOrder(orderID);
	}

}
