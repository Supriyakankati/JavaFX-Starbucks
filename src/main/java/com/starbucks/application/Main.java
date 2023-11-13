package com.starbucks.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Starbucks Application");

		showMainView();
	}

	private void showMainView() {
		Button staffButton = new Button("STAFF");
		staffButton.setPrefSize(150, 50);

		Button customerButton = new Button("CUSTOMER");
		customerButton.setPrefSize(150, 50);

		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(staffButton, customerButton);

		Scene scene = new Scene(hBox, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}