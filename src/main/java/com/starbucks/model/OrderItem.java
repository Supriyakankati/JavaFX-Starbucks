package com.starbucks.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderItem {
	private final MenuItem menuItem;
	private int quantity;
	
	private Map<String, Double> customizations;

	// Constructor
	public OrderItem(MenuItem menuItem, int quantity) {
		this.menuItem = Objects.requireNonNull(menuItem, "MenuItem cannot be null");
		this.quantity = quantity;
		this.customizations = new HashMap<>();
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

	// Add a customization to the order item
	public void addCustomization(String customization, Double additionalCost) {
		customizations.put(customization, additionalCost);
	}

	// Get the customizations associated with the order item
	public Map<String, Double> getCustomizations() {
		return customizations;
	}
	
	// Calculate the total customization cost
	public double calculateCustomizationCost() {
		double cost = 0.0;
		for (Double additionalCost : customizations.values()) {
			cost += additionalCost;
		}
		return cost;
	}

	// Calculate the total price of the order item
	public double calculateItemPrice() {
		return (menuItem.getItemPrice() + calculateCustomizationCost()) * quantity;
	}

	// Returns a string representation of the order item
    @Override
    public String toString() {
        return "OrderItem{" + "menuItem=" + menuItem + ", quantity=" + quantity + '}';
    }
}
