package com.starbucks.model;

import java.util.Objects;

public class OrderItem {
	private final MenuItem menuItem;
	private int quantity;

	// Constructor
	public OrderItem(MenuItem menuItem, int quantity) {
		this.menuItem = Objects.requireNonNull(menuItem, "MenuItem cannot be null");
		this.quantity = quantity;
	}

	// Get the base menu item
	public MenuItem getMenuItem() {
		return menuItem;
	}

	// Get the quantity of the order item
	public int getQuantity() {
		return quantity;
	}

	// Set the quantity of the order item
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Calculate the total price of the order item
	public double calculateItemPrice() {
		return menuItem.getItemPrice() * quantity;
	}

	// Returns a string representation of the order item
    @Override
    public String toString() {
        return "OrderItem{" + "menuItem=" + menuItem + ", quantity=" + quantity + '}';
    }
}
