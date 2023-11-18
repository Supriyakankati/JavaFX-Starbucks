package com.starbucks.utils;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

// A cell factory for table columns that formats decimal values. This class
// allows specifying the number of decimal places for displaying double values
// in a table cell.
public class DecimalFormatCellFactory<T> implements Callback<TableColumn<T, Double>, TableCell<T, Double>> {
	private final int decimalPlaces;

	// Constructor with the specified number of decimal places
	public DecimalFormatCellFactory(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public TableCell<T, Double> call(TableColumn<T, Double> param) {
		return new TableCell<>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					// Format the double value to the specified number of decimal places
					setText(String.format("%." + decimalPlaces + "f", item));
				}
			}
		};
	}
}
