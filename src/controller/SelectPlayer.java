package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.GameLogic;
import player.Player;

public class SelectPlayer implements Initializable {

	@FXML
	private TextField player1TextField, player2TextField, player3TextField, player4TextField, player5TextField;
	@FXML
	ImageView startImg;
	@FXML
	ChoiceBox<Integer> choiceBox;
	int amount = 2;
	private Integer[] amountOfPlayer = { 2, 3, 4, 5 };
	private ArrayList<TextField> textFieldList;
	private String[] colourList = { "white", "blue", "red", "green", "yellow" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFieldList = new ArrayList<>(Arrays.asList(player1TextField, player2TextField, player3TextField,
				player4TextField, player5TextField));

		for (int i = 2; i < textFieldList.size(); i++) {
			textFieldList.get(i).setDisable(true);
		}

		choiceBox.getItems().addAll(amountOfPlayer);
		choiceBox.setValue(2);
		choiceBox.setOnAction(this::getAmountOfPlayer);
	}

	public void getAmountOfPlayer(ActionEvent event) {
		for (int i = 2; i < textFieldList.size(); i++) {
			textFieldList.get(i).setDisable(true);
		}
		amount = choiceBox.getValue();
		for (int i = 2; i < amount; i++) {
			textFieldList.get(i).setDisable(false);
		}
	}

	@FXML
	public void nextToGameScene(MouseEvent event) {
		ArrayList<Player> playerList = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			playerList.add(new Player(textFieldList.get(i).getText(), colourList[i]));
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameScene.fxml"));
			Parent root = loader.load();
			GameLogic controller = loader.getController();
			controller.setVariable(playerList, amount);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Casino Royal Dice");
			stage.setScene(scene);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
