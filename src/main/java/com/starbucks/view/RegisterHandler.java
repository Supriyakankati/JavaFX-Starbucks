package com.starbucks.view;

import java.io.IOException;

import com.starbucks.model.User;
import com.starbucks.utils.UserManagement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterHandler extends BaseView {

	private final Stage primaryStage;
	private final Scene customerScene;

	private final UserManagement userManagement;

	private Label validationLabel;

	// Constructor
	public RegisterHandler(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.customerScene = primaryStage.getScene();
		this.userManagement = UserManagement.getInstance();
	}

	// Creates and returns the scene for Register handler
	public Scene createRegisterScene() {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));

		Label registerLabel = new Label("Register");
		registerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		usernameField.setMaxWidth(300);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(300);

		PasswordField confirmPasswordField = new PasswordField();
		confirmPasswordField.setPromptText("Confirm Password");
		confirmPasswordField.setMaxWidth(300);

		Button registerButton = styleButton(new Button("Register"));
		registerButton.setOnAction(e -> handleRegistration(usernameField, passwordField, confirmPasswordField));

		validationLabel = new Label();
		validationLabel.setTextFill(Color.RED);

		Button backButton = styleButton(new Button("Back"));
		backButton.setOnAction(e -> primaryStage.setScene(customerScene));

		layout.getChildren().addAll(registerLabel, usernameField, passwordField, confirmPasswordField, validationLabel,
				registerButton, backButton);

		return new Scene(layout, 600, 400);
	}

	// Handles the registration process
	private void handleRegistration(TextField usernameField, PasswordField passwordField,
			PasswordField confirmPasswordField) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		try {
			// Validate input fields
			if (username.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
				validationLabel.setText("Username, password, and confirm password cannot be blank.");
				return;
			}

			if (!password.equals(confirmPassword)) {
				validationLabel.setText("Passwords do not match.");
				return;
			}

			// Attempt to create a new user
			User newUser = new User(username, password, 0.0);
			userManagement.createUser(newUser);

			// Show success message
			showSuccessAlert(username);

		} catch (IOException e) {
			showAlert("Error", "Failed to write user data to file: " + e.getMessage(), Alert.AlertType.ERROR);
		} catch (Exception e) {
			validationLabel.setText(e.getMessage());
		}
	}

	// Displays a success alert with a login button after registration
	private void showSuccessAlert(String username) {
		Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "User '" + username + "' registered successfully.",
				new ButtonType("Login", ButtonBar.ButtonData.OK_DONE));
		successAlert.showAndWait().ifPresent(response -> {
			if (response.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
				primaryStage.setScene(customerScene);
			}
		});
	}
}
