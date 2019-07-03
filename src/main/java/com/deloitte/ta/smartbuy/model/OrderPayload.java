package com.deloitte.ta.smartbuy.model;

import javax.validation.constraints.NotNull;

public class OrderPayload {

	@NotNull
	private String productID;
	@NotNull
	private int quantity;
	@NotNull
	private double walletBalance;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}
}
