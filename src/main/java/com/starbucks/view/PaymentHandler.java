package com.starbucks.view;

import java.io.IOException;

import com.starbucks.model.Order;
import com.starbucks.model.User;
import com.starbucks.utils.UserManagement;

import javafx.event.ActionEvent;
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

	private Label cashInputLabel;
	private TextField cashInputField;
	private Label accountBalanceLabel;
	private Button cashPaymentButton;
	private Button depositButton;
	private Button payWithBalanceButton;
	private Button backButton;
	private Button newOrderButton;
	private Label statusLabel;

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

		User authenticatedUser = UserManagement.getInstance().getAuthenticatedUser();
		if (authenticatedUser != null) {
			Label welcomeLabel = new Label("Hi " + authenticatedUser.getUsername());
			welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

			accountBalanceLabel = new Label("Account Balance: $" + formatPrice(authenticatedUser.getBalance()));
			accountBalanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

			depositButton = styleButton(new Button("Deposit"));
			depositButton.setOnAction(e -> showDepositDialog(authenticatedUser));

			payWithBalanceButton = styleButton(new Button("Pay with Account Balance"));
			payWithBalanceButton.setPrefWidth(300);
			payWithBalanceButton.setDisable(authenticatedUser.getBalance() < currentOrder.calculateTotalCost());
			payWithBalanceButton.setOnAction(e -> handlePaymentWithBalance(authenticatedUser));

			root.getChildren().addAll(welcomeLabel, accountBalanceLabel, depositButton, payWithBalanceButton);
		}

		// Displaying the total cost of the current order
		Label totalCostLabel = new Label("Total Cost: $" + formatPrice(currentOrder.calculateTotalCost()));
		totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		// Setting up the cash input field
		cashInputField = new TextField();
		cashInputField.setMaxWidth(300);
		cashInputField.setPromptText("Enter cash amount here");

		// Label for cash input
		cashInputLabel = new Label("Cash Provided:");
		cashInputLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

		// Label for displaying payment status messages
		statusLabel = new Label();
		statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		statusLabel.setPadding(new Insets(10, 0, 10, 0));

		// Button to confirm the cash payment
		cashPaymentButton = styleButton(new Button("Pay with Cash"));
		cashPaymentButton.setOnAction(e -> handleCashPayment());

		// Back button to return to the previous scene
		backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(orderProcessingScene));

		// Button to place a new order
		newOrderButton = styleButton(new Button("Place a New Order"));
		newOrderButton.setOnAction(e -> resetAndNavigateToOrderManagement());

		// Arranging buttons in a horizontal box
		HBox buttonBox = new HBox(10, backButton);
		buttonBox.setAlignment(Pos.CENTER);

		// Adding components to the root layout
		root.getChildren().addAll(totalCostLabel, cashInputLabel, cashInputField, statusLabel, cashPaymentButton,
				buttonBox);

		return new Scene(root, 600, 400);
	}

	// Method to handle the cash payment process
	private void handleCashPayment() {
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
				cashPaymentButton.setVisible(false);
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
	
	// Method to handle payment using account balance
	private void handlePaymentWithBalance(User user) {
		double totalCost = currentOrder.calculateTotalCost();

		if (user.getBalance() >= totalCost) {
			try {
				// Deducting the total cost from the user's balance
				user.setBalance(user.getBalance() - totalCost);

				// Persisting the updated user data
				UserManagement.getInstance().updateUser(user);

				// Updating the UI to reflect successful payment
				statusLabel.setText("Payment successful using account balance.");
				statusLabel.setTextFill(Color.GREEN);

				// Hide unnecessary buttons after successful payment
				cashInputLabel.setVisible(false);
				cashInputField.setVisible(false);
				cashPaymentButton.setVisible(false);
				depositButton.setVisible(false);
				payWithBalanceButton.setVisible(false);
				backButton.setVisible(false);

				// Update the account balance display
				updateAccountBalanceDisplay(user);

				// Adding the option to place a new order
				VBox root = (VBox) primaryStage.getScene().getRoot();
				newOrderButton.setPrefWidth(root.getWidth() - 40);
				root.getChildren().add(newOrderButton);
			} catch (IOException e) {
				statusLabel.setText("Error updating account balance: " + e.getMessage());
				statusLabel.setTextFill(Color.RED);
			}
		} else {
			// Handle case where balance is insufficient
			statusLabel.setText("Insufficient account balance.");
			statusLabel.setTextFill(Color.RED);
		}
	}
	
	// Update account balance
	private void updateAccountBalanceDisplay(User user) {
		accountBalanceLabel.setText("Account Balance: $" + formatPrice(user.getBalance()));
		payWithBalanceButton.setDisable(user.getBalance() < currentOrder.calculateTotalCost());
	}
	
	// Displays a dialog for the user to deposit funds into their account
	private void showDepositDialog(User user) {
		Dialog<Void> dialog = new Dialog<>();
		dialog.setTitle("Deposit Funds");

		ToggleGroup group = new ToggleGroup();
		RadioButton option10 = new RadioButton("$10");
		option10.setUserData(10.0);
		option10.setToggleGroup(group);
		RadioButton option25 = new RadioButton("$25");
		option25.setUserData(25.0);
		option25.setToggleGroup(group);
		TextField customAmountField = new TextField();
		customAmountField.setPromptText("Custom Amount");

		ButtonType depositButtonType = new ButtonType("Deposit", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(depositButtonType, ButtonType.CANCEL);

		VBox content = new VBox(10, option10, option25, customAmountField);
		dialog.getDialogPane().setContent(content);

		Button depositButton = (Button) dialog.getDialogPane().lookupButton(depositButtonType);
		depositButton.addEventFilter(ActionEvent.ACTION, event -> {
			try {
				double amount;
				if (group.getSelectedToggle() != null) {
					amount = (double) group.getSelectedToggle().getUserData();
				} else {
					amount = Double.parseDouble(customAmountField.getText());
				}
				user.deposit(amount);
				updateAccountBalanceDisplay(user);
				UserManagement.getInstance().updateUser(user);
				statusLabel.setText("Deposit successful.");
				statusLabel.setTextFill(Color.GREEN);
			} catch (NumberFormatException e) {
				statusLabel.setText("Invalid amount. Please enter a valid number.");
				statusLabel.setTextFill(Color.RED);
				event.consume();
			} catch (IllegalArgumentException e) {
				statusLabel.setText(e.getMessage());
				statusLabel.setTextFill(Color.RED);
				event.consume();
			} catch (IOException e) {
				statusLabel.setText("Error updating account balance: " + e.getMessage());
                statusLabel.setTextFill(Color.RED);
			}
		});

		dialog.showAndWait();
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
