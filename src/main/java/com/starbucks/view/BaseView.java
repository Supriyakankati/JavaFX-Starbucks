package com.starbucks.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;

public class BaseView {

	// Method for styling buttons
	protected Button styleButton(Button button) {
		// Starbucks green color
		Color starbucksGreen = Color.rgb(0, 150, 57);

		// Set background color
		BackgroundFill backgroundFill = new BackgroundFill(starbucksGreen, new CornerRadii(5), Insets.EMPTY);
		Background background = new Background(backgroundFill);
		button.setBackground(background);

		// Set text color
		button.setTextFill(Color.BLACK);

		// Set font to bold
		Font fontBold = Font.font("Arial", FontWeight.BOLD, 12);
		button.setFont(fontBold);

		// Set button size and padding
		button.setPadding(new Insets(5, 10, 5, 10));
		button.setPrefSize(120, 20);

		return button;
	}

	// Displays an error alert dialog with a specified title, message and
	// customizable alert types
	protected void showAlert(String title, String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	// Helper method to format price values
	protected String formatPrice(double price) {
		return String.format("%.2f", price);
	}
}
