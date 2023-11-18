package com.starbucks.view;

import com.starbucks.model.Order;
import com.starbucks.model.OrderItem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OrderProcessing extends BaseView {

	private final Stage primaryStage;
	
	// Store the current order
	private final Order currentOrder;
	
	private final OrderManagement orderManagement;

	// Constructor to initialize the order processing
	public OrderProcessing(Stage primaryStage, Order currentOrder, OrderManagement orderManagement) {
		this.primaryStage = primaryStage;
		this.currentOrder = currentOrder;
		this.orderManagement = orderManagement;
	}

	// Creates and returns the scene for order processing
	public Scene createOrderProcessingScene() {
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(15));

		Label summaryLabel = new Label("Order Summary");
		summaryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		// Create a scrollable area for order items
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setPadding(new Insets(10));
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// Vertical scrollbar when needed
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		// Styling the scroll pane
		scrollPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		scrollPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		GridPane orderItemsGrid = new GridPane();
		orderItemsGrid.setHgap(10);
		orderItemsGrid.setVgap(5);
		orderItemsGrid.setAlignment(Pos.CENTER);

		int row = 0;
		for (OrderItem item : currentOrder.getOrderItems()) {
			String formattedItemPrice = formatPrice(item.getMenuItem().getItemPrice());
			String formattedTotalPrice = formatPrice(item.calculateItemPrice());

			orderItemsGrid.add(new Label(item.getMenuItem().getItemName()), 0, row);
			orderItemsGrid.add(new Label("$" + formattedItemPrice), 1, row);
			orderItemsGrid.add(new Label("x" + item.getQuantity()), 2, row);
			orderItemsGrid.add(new Label("= $" + formattedTotalPrice), 3, row);
			row++;
		}

		scrollPane.setContent(orderItemsGrid);

		// Create label for total cost
		String formattedTotalCost = formatPrice(currentOrder.calculateTotalCost());
		Label totalCostLabel = new Label("Total Cost: $" + formattedTotalCost);
		totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		// Create buttons for navigation and payment
		Button backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(orderManagement.createOrderManagementScene()));

		Button paymentButton = styleButton(new Button("Pay"));
		paymentButton.setOnAction(e -> handlePayment());

		// Setup the button box
		HBox buttonBox = new HBox(10, backButton, paymentButton);
		buttonBox.setAlignment(Pos.CENTER);

		root.getChildren().addAll(summaryLabel, scrollPane, totalCostLabel, buttonBox);

		return new Scene(root, 600, 400);
	}
	
	private void handlePayment() {
		PaymentHandler paymentHandler = new PaymentHandler(primaryStage, currentOrder, orderManagement);
		Scene paymentHandlerScene = paymentHandler.createPaymentHandlerScene();
		primaryStage.setScene(paymentHandlerScene);
	}
}
