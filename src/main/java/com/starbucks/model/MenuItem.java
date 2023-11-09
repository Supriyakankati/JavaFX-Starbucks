package com.starbucks.model;

public class MenuItem {
	private String itemName;
	private double itemPrice;
	private String description;

	public MenuItem(String itemName, double itemPrice, String description) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.description = description;
	}

	public String getName() {
		return itemName;
	}

	public void setName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return itemPrice;
	}

	public void setPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MenuItem{" + "itemName='" + itemName + '\'' + ", itemPrice=" + itemPrice + ", description='"
				+ description + '\'' + '}';
	}
}
