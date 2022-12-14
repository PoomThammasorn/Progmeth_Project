package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import player.Player;

public class EndScene {

	@FXML
	private Button exitBtn, homeBtn;
	@FXML
	private Text nameText, balanceText;

	public void setEnd(ArrayList<Player> list) {
		String name = list.get(0).getName();
		String balance = list.get(0).getBalance() + "";
		if (list.size() > 1) {
			for (int i = 1; i < list.size(); i++) {
				name += ", " + list.get(i).getName();
				balance += ", " + list.get(i).getBalance();
			}
		}
		nameText.setText(name);
		balanceText.setText(balance);
	}

	@FXML
	public void beginNewGame(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainPane.fxml"));
		Parent root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void exitGame(MouseEvent event) {
		Platform.exit();
		System.exit(0);
	}

}
