package com.starbucks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu {
	// The single instance of the Menu class
	private static Menu instance; 
	
	private List<MenuItem> items;

	// Private constructor to prevent instantiation from other classes
	private Menu() {
		this.items = new ArrayList<>();
	}

	// Public method to get the instance of the Menu
	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}

	// Add a MenuItem to the menu
	public void addItem(MenuItem item) {
		// Throws NullPointerException if the provided item is null
		Objects.requireNonNull(item, "MenuItem cannot be null");
		items.add(item);
	}

	// Update an existing MenuItem in the menu by name
	public void updateItem(String name, MenuItem updatedItem) {
		// Throws NullPointerException if the updatedItem is null
		Objects.requireNonNull(updatedItem, "Updated MenuItem cannot be null");
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemName().equals(name)) {
				items.set(i, updatedItem);
				return;
			}
		}
		// Throws IllegalArgumentException if no MenuItem with the given name is found in the menu
		throw new IllegalArgumentException("Item with name " + name + " not found in menu.");
	}

	// Remove a MenuItem from the menu by name
	public void removeItem(String name) {
		items.removeIf(item -> item.getItemName().equals(name));
	}

	// Retrieve a MenuItem from the menu by name
	public MenuItem getItem(String name) {
		return items.stream()
				.filter(item -> item.getItemName().equals(name))
				.findFirst()
				// Throws IllegalArgumentException if no MenuItem with the given name is found in the menu
				.orElseThrow(() -> new IllegalArgumentException("Item with name " + name + " not found in menu."));
	}

	// Retrieve a copy of the list of all items in the menu
	public List<MenuItem> getItems() {
		return new ArrayList<>(items);
	}
}
