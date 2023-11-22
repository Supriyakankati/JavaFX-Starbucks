package com.starbucks.view;

import java.util.Arrays;

import com.starbucks.enums.Flavor;
import com.starbucks.enums.Milk;
import com.starbucks.enums.Size;
import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import com.starbucks.model.Order;
import com.starbucks.model.OrderItem;
import com.starbucks.utils.DecimalFormatCellFactory;
import com.starbucks.utils.UserManagement;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

	private Label totalCostLabel = new Label("Total Cost: $0.00");
	
	private Button addButton;
	private Button removeButton;
	private Button processOrderButton;

	// Constructor to initialize the order management
	public OrderManagement(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainScene = primaryStage.getScene();
		this.menu = Menu.getInstance();
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

		return new Scene(root, 700, 600);
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
		
		TableColumn<OrderItem, Double> customizationCostColumn = new TableColumn<>("Customization Cost");
		customizationCostColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().calculateCustomizationCost()).asObject());
		customizationCostColumn.setCellFactory(new DecimalFormatCellFactory<>(2));

		TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		TableColumn<OrderItem, Double> totalPriceColumn = new TableColumn<>("Total Price");
		totalPriceColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().calculateItemPrice()).asObject());
		totalPriceColumn.setCellFactory(new DecimalFormatCellFactory<>(2));
		
		// Adding columns to the TableView
		orderTableView.getColumns().addAll(Arrays.asList(itemNameColumn, itemPriceColumn, customizationCostColumn,
				quantityColumn, totalPriceColumn));

		// Populate the TableView with order items
		orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));

		orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		orderTableView.setPadding(new Insets(10, 10, 10, 10));
	}

	private HBox setupAddToOrderBox() {
		addButton = styleButton(new Button("Add"));
		addButton.setOnAction(e -> addToOrder());
		addButton.setDisable(true); // Initially disabled

        HBox addBox = new HBox(10, addButton);
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
		backButton.setOnAction(e -> goBack());

		HBox bottomBox = new HBox(10, processOrderButton, cancelButton, backButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(10));
		return bottomBox;
	}	

	private void addToOrder() {
		MenuItem selectedMenuItem = menuTableView.getSelectionModel().getSelectedItem();
		if (selectedMenuItem != null) {
			try {
				showCustomizationDialog(selectedMenuItem);

				// Enable the process order button
				processOrderButton.setDisable(false);
			} catch (NumberFormatException ex) {
				showAlert("Invalid Quantity", "Please enter a valid quantity.", Alert.AlertType.ERROR);
			}
		}
	}
	
	private void showCustomizationDialog(MenuItem menuItem) {
		Dialog<OrderItem> dialog = new Dialog<>();
		dialog.setTitle("Customize Order");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ToggleGroup sizeGroup = new ToggleGroup();
		HBox sizeOptions = createRadioButtonOptions(Size.values(), sizeGroup, "Size");

		ToggleGroup flavorGroup = new ToggleGroup();
		HBox flavorOptions = createRadioButtonOptions(Flavor.values(), flavorGroup, "Flavor");

		ToggleGroup milkGroup = new ToggleGroup();
		HBox milkOptions = createRadioButtonOptions(Milk.values(), milkGroup, "Milk");

		TextField quantityField = new TextField("1");
		quantityField.setPrefWidth(50);

		Label totalCostLabel = new Label("Total Cost: $" + formatPrice(menuItem.getItemPrice()));
		totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		// Add options and Labels to the grid
		grid.add(new Label("Size:"), 0, 0);
		grid.add(sizeOptions, 1, 0);
		grid.add(new Label("Flavor:"), 0, 1);
		grid.add(flavorOptions, 1, 1);
		grid.add(new Label("Milk type:"), 0, 2);
		grid.add(milkOptions, 1, 2);
		grid.add(new Label("Quantity:"), 0, 3);
		grid.add(quantityField, 1, 3);
		grid.add(totalCostLabel, 1, 4);

		// Update total cost when selections change
		sizeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateTotalCostLabel(menuItem,
				sizeGroup, flavorGroup, milkGroup, totalCostLabel));
		flavorGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateTotalCostLabel(menuItem,
				sizeGroup, flavorGroup, milkGroup, totalCostLabel));
		milkGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateTotalCostLabel(menuItem,
				sizeGroup, flavorGroup, milkGroup, totalCostLabel));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button addButton = (Button) dialogPane.lookupButton(ButtonType.OK);
		addButton.setText("Add");
		styleButton(addButton);
		styleButton((Button) dialogPane.lookupButton(ButtonType.CANCEL));
		dialogPane.setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				int quantity = Integer.parseInt(quantityField.getText());
				OrderItem orderItem = new OrderItem(menuItem, quantity);

				// Add Size customization
				Size selectedSize = (Size) sizeGroup.getSelectedToggle().getUserData();
				orderItem.addCustomization(selectedSize.getLabel(), selectedSize.getAdditionalCost());
				
				// Add Milk customization
				Milk selectedMilk = (Milk) milkGroup.getSelectedToggle().getUserData();
				orderItem.addCustomization(selectedMilk.getLabel(), selectedMilk.getAdditionalCost());

				// Add Flavor customization if it's not None
				Flavor selectedFlavor = (Flavor) flavorGroup.getSelectedToggle().getUserData();
				if (selectedFlavor != Flavor.NONE) {
					orderItem.addCustomization(selectedFlavor.getLabel(), selectedFlavor.getAdditionalCost());
				}

				return orderItem;
			}
			return null;
		});

		dialog.showAndWait().ifPresent(orderItem -> {
			currentOrder.addOrderItem(orderItem);
			orderTableView.setItems(FXCollections.observableArrayList(currentOrder.getOrderItems()));
			updateTotalCost();
		});
	}

	// Helper method to create radio button options (horizontal layout)
	private HBox createRadioButtonOptions(Enum<?>[] options, ToggleGroup group, String category) {
		HBox box = new HBox(10);
		for (Enum<?> option : options) {
			String label = option.toString();
			if (option instanceof Size && ((Size) option).getAdditionalCost() != 0) {
				label += " (" + ((Size) option).getAdditionalCost() + "$)";
			} else if (option instanceof Flavor && ((Flavor) option).getAdditionalCost() != 0) {
				label += " (" + ((Flavor) option).getAdditionalCost() + "$)";
			} else if (option instanceof Milk && ((Milk) option).getAdditionalCost() != 0) {
				label += " (" + ((Milk) option).getAdditionalCost() + "$)";
			}
			RadioButton rb = new RadioButton(label);
			rb.setUserData(option);
			rb.setToggleGroup(group);
			if (category.equals("Size") && option.equals(Size.TALL)
					|| category.equals("Flavor") && option.equals(Flavor.NONE)
					|| category.equals("Milk") && option.equals(Milk.REGULAR)) {
				rb.setSelected(true);
			}
			box.getChildren().add(rb);
		}
		return box;
	}

	private void updateTotalCostLabel(MenuItem menuItem, ToggleGroup size, ToggleGroup flavor, ToggleGroup milk,
			Label label) {
		Size selectedSize = (Size) size.getSelectedToggle().getUserData();
		Flavor selectedFlavor = (Flavor) flavor.getSelectedToggle().getUserData();
		Milk selectedMilk = (Milk) milk.getSelectedToggle().getUserData();

		double totalCost = menuItem.getItemPrice() + selectedSize.getAdditionalCost()
				+ selectedFlavor.getAdditionalCost() + selectedMilk.getAdditionalCost();
		label.setText("Total Cost: $" + formatPrice(totalCost));
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
		menuTableView = new TableView<>();
		orderTableView = new TableView<>();
		updateTotalCost(); // Update the total cost label
		processOrderButton.setDisable(true); // Disable the process order button
	}
	
	private Label createHeaderLabel(String text) {
		Label label = new Label(text);
		label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		return label;
	}
	
	// Handle back navigation to CustomerManagement
	private void goBack() {
		UserManagement.getInstance().logout();
		primaryStage.setScene(mainScene);
	}
}
