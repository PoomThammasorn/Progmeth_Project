package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import base.Dice;
import base.Location;
import base.SpecialLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import player.Player;

public class GameLogic {

	@FXML
	private GridPane locationGridPane, diceGridPane, gridDiceL1, gridDiceL2, gridDiceL3, gridDiceL4, gridDiceL5,
			gridDiceL6;
	@FXML
	private VBox vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6, vBoxGameStatus;
	@FXML
	private StackPane stackLocation1, stackLocation2, stackLocation3, stackLocation4, stackLocation5, stackLocation6;
	@FXML
	private ImageView diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7, locationImg1,
			locationImg2, locationImg3, locationImg4, locationImg5, locationImg6, cardImg;
	@FXML
	private Text roundText;
	@FXML
	private Button rollButton;

	private ArrayList<ImageView> diceImgList, locationImgList;
	private static GameLogic instance = null;
	private ArrayList<Location> locationList = new ArrayList<>();
	private ArrayList<String> locationNameList = new ArrayList<>();
	private ArrayList<Player> playerList = new ArrayList<>();
	private int roundCount, lastSpecialLocation = 0;
	private boolean isRoll = false, selected = false;
	private Player currentTurn = new Player("Poom", "white");
	private int curretntDiceSelect;

	public GameLogic() {
		// TODO Auto-generated constructor stub
//		diceImgList = new ArrayList<ImageView>(
//				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		// this.newGame(4);
	}

	@FXML
	public void testGame() {
		newGame(4);
	}

	public void updateGameStatus(String string) {
		if (string.length() > 32) {
			String newString = string.substring(0, 32);
			newString += "\n";
			newString += string.substring(32, string.length());
			string = newString;
		}
		Text text = new Text(string);
		text.setFont(new Font(12));
		vBoxGameStatus.getChildren().add(0, text);
	}

	public void updateDice(Player player) {
		for (int k = 0; k < player.getDiceInPlayer().size(); k++) {
			int dicePoint = player.getDiceInPlayer().get(k).getPoint();
			String diceColour = player.getDiceInPlayer().get(k).getColour();
			File file = new File("res/dice" + dicePoint + diceColour + ".png");
			diceImgList.get(k).setImage(new Image(file.toURI().toString()));
		}
	}

	@FXML
	public void roll() {
		// สองอันนี้ใส่มาก่อน จริงๆควรทำใน NewGame
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		Player player = currentTurn;
		//
		if (!isRoll) {
			rollButton.setDisable(true);
			Thread thread = new Thread() {
				public void run() {
					try {
						for (int i = 0; i < 30; i++) {
							player.rollDice();
							if (i == 29) {
								player.sortDiceInPlayer();
							}
							for (int k = 0; k < player.getDiceInPlayer().size(); k++) {
								int dicePoint = player.getDiceInPlayer().get(k).getPoint();
								String diceColour = player.getDiceInPlayer().get(k).getColour();
								File file = new File("res/dice" + dicePoint + diceColour + ".png");
								diceImgList.get(k).setImage(new Image(file.toURI().toString()));
							}
							Thread.sleep(50);
						}
						isRoll = true;
						rollButton.setDisable(false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		} else {
			updateGameStatus("You already rolled the dice!!");
		}
	}

	// new game
	public void newGame(int amountOfPlayer) {
		this.setRoundCount(0);
		isRoll = false;
		curretntDiceSelect = -1;
		updateGameStatus("Start New Game!!");
		File file = new File("res/cardBack.png");
		cardImg.setImage(new Image(file.toURI().toString()));
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		locationImgList = new ArrayList<ImageView>(
				Arrays.asList(locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6));

		// ->ค้างสร้าง player
		// ลองสร้างไว้ทดสอบเฉยๆ
		playerList.add(new Player("Poom", "white"));
		playerList.add(new Player("Tungmay", "red"));
		playerList.add(new Player("Muay", "yellow"));
		playerList.add(new Player("Night", "blue"));
//		for (int k = 0; k < amountOfPlayer; k++) {
//			
//		}
		locationNameList.add("Gold Tower Casino");
		locationNameList.add("Riverside Casino");
		locationNameList.add("The Royal Casino");
		locationNameList.add("Luckey 7's Casino");
		locationNameList.add("The Edge Casino");
		locationNameList.add("Blackbird Casino");

		locationList.add(new Location("Gold Tower Casino", amountOfPlayer, 0));
		locationList.add(new Location("Riverside Casino", amountOfPlayer, 0));
		locationList.add(new Location("The Royal Casino", amountOfPlayer, 0));
		locationList.add(new Location("Luckey 7's Casino", amountOfPlayer, 0));
		locationList.add(new Location("The Edge Casino", amountOfPlayer, 0));
		locationList.add(new Location("Blackbird Casino", amountOfPlayer, 0));
		int randomNumber = (new Random()).nextInt(6);
		int locationCode = randomNumber + 1;
		File file1 = new File("res/location" + (lastSpecialLocation + 1) + ".png");
		locationImgList.get(lastSpecialLocation).setImage(new Image(file1.toURI().toString()));
		File file2 = new File("res/location" + locationCode + "Special.png");
		locationImgList.get(randomNumber).setImage(new Image(file2.toURI().toString()));
		lastSpecialLocation = randomNumber;
		updateGameStatus("Special Location is " + locationNameList.get(randomNumber));
		this.playGame(playerList, 0);
	}

	public void playGame(ArrayList<Player> playerList, int indexPlayer) {
		int i = indexPlayer % playerList.size();
		selected = false;
		curretntDiceSelect = -1;
		if (!allOutOfDice()) {
			Player p = playerList.get(i);
			updateDice(p);
			updateGameStatus(p.getName() + "'s turn!!");
			currentTurn = p;
			if (p.getDiceInPlayer().size() != 0) {

			}
			i += 1;
		} else {
			this.setRoundCount(this.getRoundCount() + 1);
			if (this.getRoundCount() != 4) {
				this.endRound();
			} else {
				roundText.setText("Round " + (getRoundCount() + 1) + " of 4");
				this.endgame();
			}
		}

	}

	// อัพเดตเงินในแต่ละสถานที่+แจกเงิน
	public void endRound() {
		updateGameStatus("End Round " + getRoundCount());
		roundText.setText("Round " + (getRoundCount() + 1) + " of 4");
		for (Location l : locationList) {
			for (int i = 0; i < l.getDiceInLocation().size(); i++) {
				if (l instanceof SpecialLocation) {
					SpecialLocation sp = (SpecialLocation) l;
					int maxelement = Collections.max(sp.getDiceInLocation());
					if (maxelement != 0 && sp.haveSameElement(maxelement)) {
						int maxelementindex = sp.getDiceInLocation().indexOf(maxelement);
						sp.sendReward(playerList.get(maxelementindex));
						l.getDiceInLocation().set(maxelementindex, 0);
						// -> เเลือกใช้การ์ด มี medthod แยกให้กรณีใช่การ์ดStealCard กับ Tax+BonusCard
					}
				} else {
					int maxelement = Collections.max(l.getDiceInLocation());
					if (maxelement != 0 && l.haveSameElement(maxelement)) {
						int maxelementindex = l.getDiceInLocation().indexOf(maxelement);
						l.sendReward(playerList.get(maxelementindex));
						l.getDiceInLocation().set(maxelementindex, 0);
					}
				}
			}
			// reset ช่องเงิน
			l.getFund().clear();
			while (l.fundValue(l.getFund()) < 50000) {
				l.updateFund();
			}
		}
		this.setRoundCount(this.getRoundCount() + 1);
	}
	
	public void selectLocation(MouseEvent event) {
		ImageView locaitonImg = (ImageView) event.getSource();
		if (selected) {
			if (locaitonImg.equals(locationImg1)) {
				
			} else if (locaitonImg.equals(locationImg2)) {
				
			} else if (locaitonImg.equals(locationImg3)) {
				
			} else if (locaitonImg.equals(locationImg4)) {
				
			} else if (locaitonImg.equals(locationImg5)) {
				
			} else if (locaitonImg.equals(locationImg6)) {
				
			} 
		}
	}
	
	public void dropDiceInLocation(int numberLocation, GridPane locationPane) {
		locationList.get(numberLocation).addDice(currentTurn, curretntDiceSelect);
		File file = new File("res/dice" + curretntDiceSelect + currentTurn.getPlayerColour() + ".png");
		ImageView img = new ImageView(new Image(file.toURI().toString()));
		img.setFitHeight(45);
		img.setFitWidth(45);
		locationPane.getChildren().add(numberLocation, img);
	}

	@FXML
	public void selectDice(MouseEvent event) {
		ImageView sourceImg = (ImageView) event.getSource();
		if (isRoll) {
			updateDice(currentTurn);
			if (sourceImg.equals(diceImg0)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(0).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg1)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(1).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg2)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(2).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg3)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(3).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg4)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(4).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg5)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(5).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg6)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(6).getPoint(), currentTurn);
			} else if (sourceImg.equals(diceImg7)) {
				editSelectDice(currentTurn.getDiceInPlayer().get(7).getPoint(), currentTurn);
			}
		} else {
			updateGameStatus("You must roll a dice before selecting it.");
		}
	}

	public void editSelectDice(int dicePoint, Player player) {
		if (isRoll) {
			selected = true;
			curretntDiceSelect = dicePoint;
			for (int i = 0; i < player.getDiceInPlayer().size(); i++) {
				Dice dice = player.getDiceInPlayer().get(i);
				if (dice.getPoint() != dicePoint) {
					File file = new File("res/dice" + dice.getPoint() + "Visible" + ".png");
					diceImgList.get(i).setImage(new Image(file.toURI().toString()));
				}
			}
		} else {
			updateGameStatus("You must roll a dice before selecting it.");
		}
	}

	public void endgame() {
		updateGameStatus("GAME END!!");
		// ->เขียนยังไงดี
	}

	public boolean allOutOfDice() {
		for (Player p : playerList) {
			if (p.getDiceInPlayer().size() != 0)
				return false;
		}
		return true;
	}

	public ArrayList<String> getLocationNameList() {
		return locationNameList;
	}

	public void setLocationNameList(ArrayList<String> locationNameList) {
		this.locationNameList = locationNameList;
	}

	public int getRoundCount() {
		return roundCount;
	}

	public void setRoundCount(int roundCount) {
		this.roundCount = roundCount;
	}

	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}

}
