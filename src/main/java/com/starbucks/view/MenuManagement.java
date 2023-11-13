package com.starbucks.view;

import java.util.Arrays;

import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MenuManagement {

	// Menu containing all the items
	private final Menu menu;
	
	private final Stage primaryStage;
	
	// Main scene to return to from menu management
	private final Scene mainScene;
	
	private TableView<MenuItem> tableView = new TableView<>();
	
	private Button updateButton;
	
	private Button removeButton;
	
	// Store the selected item
	private MenuItem selectedItem;

	// Constructor to initialize the menu management
	public MenuManagement(Stage primaryStage, Menu menu) {
		this.primaryStage = primaryStage;
		this.mainScene = primaryStage.getScene();
		this.menu = menu;
	}

	// Creates and returns the scene for menu management
	public Scene createMenuManagementScene() {
		BorderPane root = new BorderPane();

		// Setup the table for displaying menu items
		setupTableView();

		Label menuManagementLabel = new Label("Menu Management");
		menuManagementLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		menuManagementLabel.setPadding(new Insets(10, 0, 10, 0));

		VBox vBox = new VBox(10, menuManagementLabel, tableView);
		vBox.setAlignment(Pos.CENTER);

		root.setCenter(vBox);

		HBox buttonBox = setupButtonBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10, 10, 10, 10));
		root.setBottom(buttonBox);

		return new Scene(root, 600, 400);
	}

	// Sets up the TableView with columns for menu item name, price, and description.
	private void setupTableView() {
		TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

		TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

		TableColumn<MenuItem, String> descColumn = new TableColumn<>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		// Adding columns to the TableView
		tableView.getColumns().addAll(Arrays.asList(nameColumn, priceColumn, descColumn));
		
		// Populate the TableView with menu items
		tableView.setItems(FXCollections.observableArrayList(menu.getItems()));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setPadding(new Insets(10, 10, 10, 10));
		
		// Adding listener to enable update and remove buttons when an item is selected
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			// Update the selectedItem with the currently selected item
			selectedItem = newSelection; 
			
			// Enable the buttons
			updateButton.setDisable(newSelection == null);
			removeButton.setDisable(newSelection == null);
		});
	}

	// Sets up the buttons for menu management operations
	private HBox setupButtonBox() {
		Button addButton = styleButton(new Button("Add Item"));
		addButton.setOnAction(e -> showAddItemDialog());

		updateButton = styleButton(new Button("Update Item"));
        updateButton.setOnAction(e -> showUpdateItemDialog());

        removeButton = styleButton(new Button("Remove Item"));
        removeButton.setOnAction(e -> removeSelectedItem());

		Button backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(mainScene));

		// Initially disable update and remove buttons
		updateButton.setDisable(true);
		removeButton.setDisable(true);

		// Arrange buttons horizontally and center them
		HBox buttonBox = new HBox(10, addButton, updateButton, removeButton, backButton);
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;
	}

	private Button styleButton(Button button) {
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

	// Shows a dialog for adding a new menu item
	private void showAddItemDialog() {
		showItemDialog(false);
	}

	private void showUpdateItemDialog() {
		if (selectedItem == null) {
			showAlert("No Selection", "Please select an item to update.");
			return;
		}
		showItemDialog(true);
	}

	private void removeSelectedItem() {
		// TODO
	}
	
	// Shows a dialog for adding or updating a menu item
	private void showItemDialog(boolean isUpdate) {
		// Creating a new dialog window based on isUpdate flag
		Dialog<MenuItem> dialog = new Dialog<>();
		dialog.setTitle(isUpdate ? "Update Menu Item" : "Add New Menu Item");

		// Setting up the dialog pane with OK and Cancel buttons
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		// Pre-fill the text fields if updating, else start with empty fields
		TextField nameField = new TextField(isUpdate ? selectedItem.getItemName() : "");
		TextField priceField = new TextField(isUpdate ? String.valueOf(selectedItem.getItemPrice()) : "");
		TextField descField = new TextField(isUpdate ? selectedItem.getDescription() : "");

		GridPane grid = new GridPane();
		grid.add(new Label("Name:"), 0, 0);
		grid.add(nameField, 1, 0);
		grid.add(new Label("Price:"), 0, 1);
		grid.add(priceField, 1, 1);
		grid.add(new Label("Description:"), 0, 2);
		grid.add(descField, 1, 2);
		dialogPane.setContent(grid);

		// The behavior of the OK button
		Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
		okButton.addEventFilter(ActionEvent.ACTION, event -> {
			try {
				String name = nameField.getText().trim();
				String description = descField.getText().trim();
				String priceText = priceField.getText().trim();

				// Check if any field is empty and show an error if so
				if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
					showAlert("Validation Error", "Name, price, and description cannot be empty.");
					event.consume();
					return;
				}

				// Parse price and ensure it's a valid number
				double price = Double.parseDouble(priceText);

				// Create new MenuItem or update existing based on isUpdate flag
				MenuItem newItem = new MenuItem(name, price, description);
				if (isUpdate) {
					// Update the existing item in the menu and table view
					menu.updateItem(selectedItem.getItemName(), newItem);
					int index = tableView.getItems().indexOf(selectedItem);
					tableView.getItems().set(index, newItem);
				} else {
					// Add the new item to the menu and table view
					menu.addItem(newItem);
					tableView.getItems().add(newItem);
				}
				dialog.close();
			} catch (NumberFormatException e) {
				// Show an error if the price is not a valid number
				showAlert("Error", "Price must be a valid number.");
				event.consume();
			} catch (NullPointerException | IllegalArgumentException e) {
				// Show an error if there's a problem with the item data
				showAlert("Error", e.getMessage());
				event.consume();
			}
		});

		dialog.showAndWait();
	}
	
	// Displays an error alert dialog with a specified title and message
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
