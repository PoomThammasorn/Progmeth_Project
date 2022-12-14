package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button newGameButton;

	@FXML
	private Button exitButton;

	@FXML
	public void beginNewGame(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectPlayerScene.fxml"));	
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void exitGame() {
		System.exit(0);
	}

}
