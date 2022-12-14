package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenu {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private ImageView newGameImg, exitImg;

	@FXML
	public void beginNewGame(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SelectPlayerScene.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void exitGame(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.CANCEL);
		alert.setTitle("Casino Royal Game");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to exit the game?");
		alert.showAndWait();
		if (alert.getResult().equals(ButtonType.YES)) {
			Platform.exit();
			System.exit(0);
		}
	}

}
