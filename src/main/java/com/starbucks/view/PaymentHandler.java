package com.starbucks.view;

import com.starbucks.model.Order;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PaymentHandler extends BaseView {

	private final Stage primaryStage;
	private final Scene orderProcessingScene;
	private Order currentOrder;
	private final OrderManagement orderManagement;

	private TextField cashInputField;
	private Label cashInputLabel;
	private Label statusLabel;
	private Button confirmPaymentButton;
	private Button backButton;
	private Button newOrderButton;

	// Constructor to initialize the payment handler
	public PaymentHandler(Stage primaryStage, Order currentOrder, OrderManagement orderManagement) {
		this.primaryStage = primaryStage;
		this.orderProcessingScene = primaryStage.getScene();
		this.currentOrder = currentOrder;
		this.orderManagement = orderManagement;
	}

	// Creates and returns the scene for payment handler
	public Scene createPaymentHandlerScene() {
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(15));

		// Displaying the total cost of the current order
		Label totalCostLabel = new Label("Total Cost: $" + formatPrice(currentOrder.calculateTotalCost()));
		totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		// Setting up the cash input field
		cashInputField = new TextField();
		cashInputField.setPromptText("Enter cash amount here");

		// Label for cash input
		cashInputLabel = new Label("Cash Provided:");
		cashInputLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

		// Label for displaying payment status messages
		statusLabel = new Label();
		statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		statusLabel.setPadding(new Insets(10, 0, 10, 0));

		// Button to confirm the payment
		confirmPaymentButton = styleButton(new Button("Confirm Payment"));
		confirmPaymentButton.setOnAction(e -> handlePayment());

		// Back button to return to the previous scene
		backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(orderProcessingScene));

		// Button to place a new order
		newOrderButton = styleButton(new Button("Place a New Order"));
		newOrderButton.setOnAction(e -> resetAndNavigateToOrderManagement());

		// Arranging buttons in a horizontal box
		HBox buttonBox = new HBox(10, backButton, confirmPaymentButton);
		buttonBox.setAlignment(Pos.CENTER);

		// Adding components to the root layout
		root.getChildren().addAll(totalCostLabel, cashInputLabel, cashInputField, statusLabel, buttonBox);

		return new Scene(root, 600, 400);
	}

	// Method to handle the payment process
	private void handlePayment() {
		try {
			double cash = Double.parseDouble(cashInputField.getText());
			double totalCost = currentOrder.calculateTotalCost();

			// Checking if the provided cash is sufficient
			if (cash < totalCost) {
				statusLabel.setText("Insufficient Payment. Please enter a valid amount.");
				statusLabel.setTextFill(Color.RED);
			} else {
				// Calculating change and updating status
				double change = cash - totalCost;
				statusLabel.setText("Payment Successful. Change: $" + formatPrice(change));
				statusLabel.setTextFill(Color.GREEN);

				// Hiding payment and back buttons after successful payment
				confirmPaymentButton.setVisible(false);
				backButton.setVisible(false);

				// Adding the new order button to the layout
				VBox root = (VBox) primaryStage.getScene().getRoot();
				newOrderButton.setPrefWidth(root.getWidth() - 40); // Adjust width to fit text
				root.getChildren().add(newOrderButton);
			}
		} catch (NumberFormatException ex) {
			statusLabel.setText("Invalid Input. Please enter a valid number.");
			statusLabel.setTextFill(Color.RED);
		}
	}

	// Method to reset order management and navigate back to the Order Management
	// scene
	private void resetAndNavigateToOrderManagement() {
		// Reset the order management
		orderManagement.resetOrderManagement();

		// Navigate back to the Order Management scene
		primaryStage.setScene(orderManagement.createOrderManagementScene());
	}
}
