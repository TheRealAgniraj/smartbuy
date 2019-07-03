package com.deloitte.ta.smartbuy.model;

public class Product {

	private String productID;
	private String name;
	private String category;
	private int availableQuantity;
	private double price;

	public Product() {
		super();
	}

	public Product(String productID, String name, String category, int availableQuantity, double price) {
		super();
		this.productID = productID;
		this.name = name;
		this.category = category;
		this.availableQuantity = availableQuantity;
		this.price = price;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
