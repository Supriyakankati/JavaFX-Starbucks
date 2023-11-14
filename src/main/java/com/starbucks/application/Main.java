package com.starbucks.application;

import com.starbucks.model.Menu;
import com.starbucks.model.MenuItem;
import com.starbucks.view.MenuManagement;
import com.starbucks.view.OrderManagement;

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
	private Menu menu = new Menu();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Starbucks Application");

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
		customerButton.setOnAction(e -> showOrderManagementView());

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
		MenuManagement menuManagement = new MenuManagement(primaryStage, menu);
		Scene menuManagementScene = menuManagement.createMenuManagementScene();
		primaryStage.setScene(menuManagementScene);
	}
	
	private void showOrderManagementView() {
		OrderManagement orderManagement = new OrderManagement(primaryStage, menu);
		Scene orderManagementScene = orderManagement.createOrderManagementScene();
		primaryStage.setScene(orderManagementScene);
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

	public static void main(String[] args) {
		launch(args);
	}
}