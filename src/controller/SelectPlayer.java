package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SelectPlayer {

	@FXML
	private TextField player1TextField;
	@FXML
	private TextField player2TextField;
	@FXML
	private TextField player3TextField;
	@FXML
	private TextField player4TextField;
	@FXML
	private TextField player5TextField;
	@FXML
	private Button select2playerButton;
	@FXML
	private Button select3playerButton;
	@FXML
	private Button select4playerButton;
	@FXML
	private Button select5playerButton;
	@FXML
	private Button backButton;

	@FXML
	public void backToMainMenu(ActionEvent event) throws IOException {
		;
	}

}
