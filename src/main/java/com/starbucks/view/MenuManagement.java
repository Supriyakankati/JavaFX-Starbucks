package com.starbucks.view;

import java.util.Arrays;

import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
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
	}

	// Sets up the buttons for menu management operations
	private HBox setupButtonBox() {
		Button addButton = styleButton(new Button("Add Item"));
		addButton.setOnAction(e -> showAddItemDialog());

		Button updateButton = styleButton(new Button("Update Item"));
		updateButton.setOnAction(e -> showUpdateItemDialog());

		Button removeButton = styleButton(new Button("Remove Item"));
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

	private void showAddItemDialog() {
		// TODO
	}

	private void showUpdateItemDialog() {
		// TODO
	}

	private void removeSelectedItem() {
		// TODO
	}
}
