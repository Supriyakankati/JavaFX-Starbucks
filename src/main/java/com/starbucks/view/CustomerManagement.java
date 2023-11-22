package com.starbucks.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CustomerManagement extends BaseView {

	private final Stage primaryStage;
	private final Scene mainScene;

	public CustomerManagement(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainScene = primaryStage.getScene();
	}

	public Scene createCustomerScene() {
		BorderPane borderPane = new BorderPane();

		// Welcome Label
		Label welcomeLabel = new Label("Welcome to Starbucks!");
		welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		HBox topLayout = new HBox(welcomeLabel);
		topLayout.setAlignment(Pos.CENTER);
		borderPane.setTop(topLayout);

		// Login and Continue as Guest
		VBox loginLayout = new VBox(10);
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		usernameField.setMaxWidth(200);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(200);

		Button loginButton = styleButton(new Button("Login"));
		loginButton.setPrefSize(150, 50);

		VBox registerBox = new VBox();
		Label newUserLabel = new Label("New User?");
		newUserLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Button registerButton = styleButton(new Button("Register"));
		registerButton.setPrefSize(150, 50);
		registerButton.setOnAction(e -> showRegisterHandlerView());
		registerBox.getChildren().addAll(newUserLabel, registerButton);
		registerBox.setAlignment(Pos.CENTER_LEFT);

		loginLayout.getChildren().addAll(usernameField, passwordField, loginButton, registerBox);
		loginLayout.setAlignment(Pos.CENTER_LEFT);

		VBox guestLayout = new VBox(10);
		Button guestButton = styleButton(new Button("Continue as Guest"));
		guestButton.setPrefSize(200, 50);
		guestButton.setOnAction(e -> showOrderManagementView());
		guestLayout.getChildren().add(guestButton);
		guestLayout.setAlignment(Pos.CENTER);

		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);

		HBox mainLayout = new HBox(20, loginLayout, separator, guestLayout);
		mainLayout.setAlignment(Pos.CENTER);
		borderPane.setCenter(mainLayout);

		// Back Button
		Button backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(mainScene));
		HBox bottomLayout = new HBox(backButton);
		bottomLayout.setAlignment(Pos.CENTER);
		borderPane.setBottom(bottomLayout);

		BorderPane.setMargin(topLayout, new Insets(10));
		BorderPane.setMargin(bottomLayout, new Insets(10));

		return new Scene(borderPane, 600, 400);
	}

	// Displays the order management view of the application
	private void showOrderManagementView() {
		OrderManagement orderManagement = new OrderManagement(primaryStage);
		Scene orderManagementScene = orderManagement.createOrderManagementScene();
		primaryStage.setScene(orderManagementScene);
	}
	
	// Displays the register handler view of the application
	private void showRegisterHandlerView() {
		RegisterHandler registerHandler = new RegisterHandler(primaryStage);
		Scene registerHandlerScene = registerHandler.createRegisterScene();
		primaryStage.setScene(registerHandlerScene);
	}
}
