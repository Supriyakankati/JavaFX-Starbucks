package com.starbucks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu {
	private List<MenuItem> items;

	public Menu() {
		this.items = new ArrayList<>();
	}

	public void addItem(MenuItem item) {
		Objects.requireNonNull(item, "MenuItem cannot be null");
		items.add(item);
	}

	public void updateItem(String name, MenuItem updatedItem) {
		Objects.requireNonNull(updatedItem, "Updated MenuItem cannot be null");
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(name)) {
				items.set(i, updatedItem);
				return;
			}
		}
		throw new IllegalArgumentException("Item with name " + name + " not found in menu.");
	}

	public void removeItem(String name) {
		items.removeIf(item -> item.getName().equals(name));
	}

	public MenuItem getItem(String name) {
		return items.stream()
				.filter(item -> item.getName().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Item with name " + name + " not found in menu."));
	}

	public List<MenuItem> getItems() {
		return new ArrayList<>(items);
	}
}
