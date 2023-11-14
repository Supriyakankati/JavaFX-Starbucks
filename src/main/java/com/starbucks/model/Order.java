package com.starbucks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
	private final List<OrderItem> orderItems;
	private int orderNumber;

	// Constructor
	public Order() {
		this.orderItems = new ArrayList<>();
	}

	// Add an OrderItem to the order
	public void addOrderItem(OrderItem item) {
		Objects.requireNonNull(item, "OrderItem cannot be null");
		orderItems.add(item);
	}

	// Remove an OrderItem from the order
	public void removeOrderItem(OrderItem item) {
		orderItems.remove(item);
	}

	// Get the order number
	public int getOrderNumber() {
		return orderNumber;
	}

	// Set the order number
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	// Calculate the total cost of the order
	public double calculateTotalCost() {
		double totalCost = 0;
		for (OrderItem item : orderItems) {
			totalCost += item.calculateItemPrice();
		}
		return totalCost;
	}

	// Retrieve a copy of the list of all order items
	public List<OrderItem> getOrderItems() {
		return new ArrayList<>(orderItems);
	}

	// Returns a string representation of the order
	@Override
	public String toString() {
		return "Order{" + "orderItems=" + orderItems + ", orderNumber=" + orderNumber + '}';
	}
}
