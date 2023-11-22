package com.starbucks.application;

import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import com.starbucks.view.CustomerManagement;
import com.starbucks.view.MenuManagement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	
	// Menu containing all the items
	private Menu menu;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Starbucks Application");

		initializeMenuWithData();
		showMainView();
	}

	// Displays the main view of the application
	private void showMainView() {
		// Setting up and styling buttons for staff and customer
		Button staffButton = styleButton(new Button("STAFF"));
		staffButton.setPrefSize(150, 50);
		staffButton.setOnAction(e -> showMenuManagementView());

		Button customerButton = styleButton(new Button("CUSTOMER"));
		customerButton.setPrefSize(150, 50);
		customerButton.setOnAction(e -> showCustomerManagementView());

		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(staffButton, customerButton);

		// Setting up the scene and displaying it on the primary stage
		Scene scene = new Scene(hBox, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Displays the menu management view of the application
	private void showMenuManagementView() {
		MenuManagement menuManagement = new MenuManagement(primaryStage);
		Scene menuManagementScene = menuManagement.createMenuManagementScene();
		primaryStage.setScene(menuManagementScene);
	}
	
	// Displays the customer management view of the application
	private void showCustomerManagementView() {
		CustomerManagement customerManagement = new CustomerManagement(primaryStage);
		Scene customerManagementScene = customerManagement.createCustomerScene();
		primaryStage.setScene(customerManagementScene);
	}

	// Sets the background color, text color, font, and size of the button
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

		return button;
	}
	
	private void initializeMenuWithData() {
		this.menu = Menu.getInstance();
        menu.addItem(new MenuItem("Caffè Americano", 2.95, "Espresso shots topped with hot water for a rich, creamy flavor."));
        menu.addItem(new MenuItem("Caffè Mocha", 3.45, "Espresso with bittersweet mocha sauce and steamed milk."));
        menu.addItem(new MenuItem("Cappuccino", 2.95, "Dark, rich espresso under a smoothed and stretched layer of thick milk foam."));
        menu.addItem(new MenuItem("Espresso", 1.95, "Rich and caramelly espresso in its purest form."));
        menu.addItem(new MenuItem("Flat White", 3.75, "Smooth ristretto shots of espresso and whole milk."));
        menu.addItem(new MenuItem("Latte Macchiato", 3.65, "Layered espresso with steamed whole milk, lightly topped with foam."));
        menu.addItem(new MenuItem("Caramel Macchiato", 3.95, "Freshly steamed milk with vanilla syrup, espresso and caramel drizzle."));
        menu.addItem(new MenuItem("White Chocolate Mocha", 3.95, "Espresso with white chocolate sauce and steamed milk."));
        menu.addItem(new MenuItem("Pike Place Roast", 2.45, "Smooth, balanced and rich flavor from our signature blend of coffee."));
        menu.addItem(new MenuItem("Nitro Cold Brew", 3.95, "Velvety-smooth cold brew with a creamy head of foam."));
    }

	public static void main(String[] args) {
		launch(args);
	}
}