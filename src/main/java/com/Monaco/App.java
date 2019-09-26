package com.Monaco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.IOException;

public class App extends Application{


	@Override
	public void start(Stage stage){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add("styles/modena_dark.css");

		stage.getIcons().clear();
		stage.getIcons().add(new Image("images/Icon.png"));
		stage.setScene(scene);
		stage.setTitle("D'yem");
		stage.show();
	}

 }
