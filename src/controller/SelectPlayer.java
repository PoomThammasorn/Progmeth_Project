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
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SelectPlayer implements Initializable {

	@FXML
	private TextField player1TextField, player2TextField, player3TextField, player4TextField, player5TextField;
	@FXML
	ImageView startImg;
	@FXML
	ChoiceBox<Integer> choiceBox;
	private Integer[] amountOfPlayer = { 2, 3, 4, 5 };
	private ArrayList<TextField> textFieldList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFieldList = new ArrayList<>(Arrays.asList(player1TextField, player2TextField, player3TextField,
				player4TextField, player5TextField));
		// TODO Auto-generated method stub
		player3TextField.setDisable(true);
		player4TextField.setDisable(true);
		player5TextField.setDisable(true);
		choiceBox.getItems().addAll(amountOfPlayer);
		choiceBox.setValue(2);
		choiceBox.setOnAction(this::getAmountOfPlayer);
	}

	public void getAmountOfPlayer(ActionEvent event) {
		System.out.println(textFieldList.size());
		for (int i = 2; i < 5; i++) {
			textFieldList.get(i).setDisable(true);
		}
		int amount = choiceBox.getValue();
		for (int i = 2; i < amount; i++) {
			textFieldList.get(i).setDisable(false);
		}
	}

}
