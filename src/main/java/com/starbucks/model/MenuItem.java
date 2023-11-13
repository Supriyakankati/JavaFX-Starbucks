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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
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
