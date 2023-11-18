package com.starbucks.view;

import java.util.Arrays;

import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import com.starbucks.model.Order;
import com.starbucks.model.OrderItem;
import com.starbucks.utils.DecimalFormatCellFactory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OrderManagement extends BaseView {

	private final Stage primaryStage;
	
	// Main scene to return to from menu management
	private final Scene mainScene;
	
	// Menu containing all the items
	private final Menu menu;
	
	// Current order
	private Order currentOrder = new Order(); 

	private TableView<MenuItem> menuTableView = new TableView<>();
	private TableView<OrderItem> orderTableView = new TableView<>();

	private TextField quantityTextField = new TextField("1"); // Default quantity is 1
	private Label totalCostLabel = new Label("Total Cost: $0.00");
	
	private Button addButton;
	private Button removeButton;
	private Button processOrderButton;

	// Constructor to initialize the order management
	public OrderManagement(Stage primaryStage, Menu menu) {
		this.primaryStage = primaryStage;
		this.mainScene = primaryStage.getScene();
		this.menu = menu;
	}

	// Creates and returns the scene for order management
	public Scene createOrderManagementScene() {
		BorderPane root = new BorderPane();
		setupMenuTableView();
		setupOrderTableView();

		// Setup headers for tables
        Label menuLabel = createHeaderLabel("Menu");
        Label orderLabel = createHeaderLabel("Order");

        // Setup total cost label with bold font
        totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Setup remove button for order items
        removeButton = styleButton(new Button("Remove"));
        removeButton.setOnAction(e -> removeFromOrder());
        removeButton.setDisable(true); // Initially disabled

        // Listen for table selection changes
        menuTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            addButton.setDisable(newVal == null);
        });
        orderTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            removeButton.setDisable(newVal == null);
        });

        // Add headers and arrange components
        VBox centerBox = new VBox(10, menuLabel, menuTableView, setupAddToOrderBox(), orderLabel, orderTableView, setupTotalCostAndRemoveBox());
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(10));
        root.setCenter(centerBox);

		root.setBottom(setupBottomButtonBox());

		return new Scene(root, 600, 600);
	}

	// Setup the table for displaying menu items
	private void setupMenuTableView() {
		TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

		TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceColumn.setCellFactory(new DecimalFormatCellFactory<>(2));

		TableColumn<MenuItem, String> descColumn = new TableColumn<>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		// Adding columns to the TableView
		menuTableView.getColumns().addAll(Arrays.asList(nameColumn, priceColumn, descColumn));

		// Populate the TableView with menu items
		menuTableView.setItems(FXCollections.observableArrayList(menu.getItems()));

		menuTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		menuTableView.setPadding(new Insets(10, 10, 10, 10));
	}

	// Setup the table for displaying order items
	private void setupOrderTableView() {
		TableColumn<OrderItem, String> itemNameColumn = new TableColumn<>("Item Name");
		itemNameColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getMenuItem().getItemName()));

		TableColumn<OrderItem, Double> itemPriceColumn = new TableColumn<>("Item Price");
		itemPriceColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getMenuItem().getItemPrice()).asObject());
		itemPriceColumn.setCellFactory(new DecimalFormatCellFactory<>(2));
		
		TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		TableColumn<OrderItem, Double> totalPriceColumn = new TableColumn<>("Total Price");
		totalPriceColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().calculateItemPrice()).asObject());
		totalPriceColumn.setCellFactory(new DecimalFormatCellFactory<>(2));
		
		// Adding columns to the TableView
		orderTableView.getColumns().addAll(Arrays.asList(itemNameColumn, itemPriceColumn, quantityColumn, totalPriceColumn));

		// Populate the TableView with order items
		orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));

		orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		orderTableView.setPadding(new Insets(10, 10, 10, 10));
	}

	private HBox setupAddToOrderBox() {
		addButton = styleButton(new Button("Add"));
		addButton.setOnAction(e -> addToOrder());
		addButton.setDisable(true); // Initially disabled

		HBox addBox = new HBox(10, new Label("Quantity:"), quantityTextField, addButton);
		addBox.setAlignment(Pos.CENTER);
		return addBox;
	}
	
	private HBox setupTotalCostAndRemoveBox() {
		removeButton = styleButton(new Button("Remove"));
		removeButton.setOnAction(e -> removeFromOrder());
		removeButton.setDisable(true); // Initially disabled

		HBox totalCostRemoveBox = new HBox(10, totalCostLabel, removeButton);
		totalCostRemoveBox.setAlignment(Pos.CENTER);
		return totalCostRemoveBox;
	}
	
	private HBox setupBottomButtonBox() {
		processOrderButton = styleButton(new Button("Process Order"));
		processOrderButton.setOnAction(e -> processOrder());
		processOrderButton.setDisable(true); // Initially disabled

		Button cancelButton = styleButton(new Button("Cancel Order"));
		cancelButton.setOnAction(e -> cancelOrder());

		Button backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(mainScene));

		HBox bottomBox = new HBox(10, processOrderButton, cancelButton, backButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(10));
		return bottomBox;
	}	

	private void addToOrder() {
		MenuItem selectedMenuItem = menuTableView.getSelectionModel().getSelectedItem();
		if (selectedMenuItem != null) {
			try {
				int quantity = Integer.parseInt(quantityTextField.getText());
				OrderItem orderItem = new OrderItem(selectedMenuItem, quantity);
				currentOrder.addOrderItem(orderItem);
				orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));
				updateTotalCost();
				
				// Enable the process order button
                processOrderButton.setDisable(false);
			} catch (NumberFormatException ex) {
				showAlert("Invalid Quantity", "Please enter a valid quantity.", Alert.AlertType.ERROR);
			}
		}
	}
	
	private void removeFromOrder() {
		OrderItem selectedOrderItem = orderTableView.getSelectionModel().getSelectedItem();
		if (selectedOrderItem != null) {
			currentOrder.removeOrderItem(selectedOrderItem);
			orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));
			updateTotalCost();
			
			// Disable the process order button if the order is empty
            processOrderButton.setDisable(currentOrder.getOrderItems().isEmpty());
		}
	}

	private void updateTotalCost() {
		double totalCost = currentOrder.calculateTotalCost();
		totalCostLabel.setText(String.format("Total Cost: $%.2f", totalCost));
	}

	private void processOrder() {
		OrderProcessing orderProcessing = new OrderProcessing(primaryStage, currentOrder, this);
		Scene orderProcessingScene = orderProcessing.createOrderProcessingScene();
		primaryStage.setScene(orderProcessingScene);
	}

	private void cancelOrder() {
		// Confirmation dialog for canceling the order
		Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel the order?",
				ButtonType.YES, ButtonType.NO);
		confirmationDialog.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				currentOrder = new Order();
				orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));
				updateTotalCost();
				
				// Disable the process order button if the order is empty
	            processOrderButton.setDisable(currentOrder.getOrderItems().isEmpty());
			}
		});
	}
	
	// Method to reset the order and TableView
	public void resetOrderManagement() {
		this.currentOrder = new Order(); // Reset the current order
		orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems())); // Clear the TableView
		updateTotalCost(); // Update the total cost label
		processOrderButton.setDisable(true); // Disable the process order button
	}
	
	private Label createHeaderLabel(String text) {
		Label label = new Label(text);
		label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		return label;
	}
}
