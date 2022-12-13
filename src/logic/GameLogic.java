package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.io.File;

import base.Banknote;
import base.Dice;
import base.Location;
import base.SpecialLocation;
import basecard.Card;
import card.CardDeck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import player.Player;

public class GameLogic {

	@FXML
	private StackPane playerBoardPane1, playerBoardPane2, playerBoardPane3, playerBoardPane4, playerBoardPane5,
			balanceBoardPane1, balanceBoardPane2, balanceBoardPane3, balanceBoardPane4, balanceBoardPane5,
			diceBoardPane1, diceBoardPane2, diceBoardPane3, diceBoardPane4, diceBoardPane5;

	@FXML
	private GridPane locationGridPane, diceGridPane;
	@FXML
	private VBox vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6, vBoxGameStatus;

	@FXML
	private ImageView diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7, cardImg,
			locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6, diceP1L1Img,
			diceP1L2Img, diceP1L3Img, diceP1L4Img, diceP1L5Img, diceP1L6Img, diceP2L1Img, diceP2L2Img, diceP2L3Img,
			diceP2L4Img, diceP2L5Img, diceP2L6Img, diceP3L1Img, diceP3L2Img, diceP3L3Img, diceP3L4Img, diceP3L5Img,
			diceP3L6Img, diceP4L1Img, diceP4L2Img, diceP4L3Img, diceP4L4Img, diceP4L5Img, diceP4L6Img, diceP5L1Img,
			diceP5L2Img, diceP5L3Img, diceP5L4Img, diceP5L5Img, diceP5L6Img;
	@FXML
	private Text roundText, textP1L1, textP2L1, textP3L1, textP4L1, textP5L1, textP1L2, textP2L2, textP3L2, textP4L2,
			textP5L2, textP1L3, textP2L3, textP3L3, textP4L3, textP5L3, textP1L4, textP2L4, textP3L4, textP4L4,
			textP5L4, textP1L5, textP2L5, textP3L5, textP4L5, textP5L5, textP1L6, textP2L6, textP3L6, textP4L6,
			textP5L6;
	@FXML
	private Button rollButton;

	private ArrayList<VBox> vBoxLocationList;
	private ArrayList<StackPane> playerScoreBoard, balanceScoreBoard, diceScoreBoard;
	private ArrayList<ImageView> diceImgList, locationImgList;
	private ArrayList<ImageView> diceLocation1ImgList, diceLocation2ImgList, diceLocation3ImgList, diceLocation4ImgList,
			diceLocation5ImgList, diceLocation6ImgList;
	private ArrayList<ArrayList<ImageView>> diceInLocationImgList;
	private ArrayList<Text> textOfAmountInLocation1List, textOfAmountInLocation2List, textOfAmountInLocation3List,
			textOfAmountInLocation4List, textOfAmountInLocation5List, textOfAmountInLocation6List;
	private static GameLogic instance = null;
	private ArrayList<Location> locationList = new ArrayList<>();
	private ArrayList<String> locationNameList = new ArrayList<>();
	private ArrayList<Player> playerList = new ArrayList<>();
	private int roundCount, lastSpecialLocation = 0, indexPlayer = 0;
	private boolean isRoll = false, selected = false;
	private Player currentPlayer = new Player("Poom", "white");
	private int curretntDiceSelect;
	private CardDeck cardDeck;

	public GameLogic() {
		// TODO Auto-generated constructor stub
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		diceLocation1ImgList = new ArrayList<>(
				Arrays.asList(diceP1L1Img, diceP2L1Img, diceP3L1Img, diceP4L1Img, diceP5L1Img));
		diceLocation2ImgList = new ArrayList<>(
				Arrays.asList(diceP1L2Img, diceP2L2Img, diceP3L2Img, diceP4L2Img, diceP5L2Img));
		diceLocation3ImgList = new ArrayList<>(
				Arrays.asList(diceP1L3Img, diceP2L3Img, diceP3L3Img, diceP4L3Img, diceP5L3Img));
		diceLocation4ImgList = new ArrayList<>(
				Arrays.asList(diceP1L4Img, diceP2L4Img, diceP3L4Img, diceP4L4Img, diceP5L4Img));
		diceLocation5ImgList = new ArrayList<>(
				Arrays.asList(diceP1L5Img, diceP2L5Img, diceP3L5Img, diceP4L5Img, diceP5L5Img));
		diceLocation6ImgList = new ArrayList<>(
				Arrays.asList(diceP1L6Img, diceP2L6Img, diceP3L6Img, diceP4L6Img, diceP5L6Img));
		diceInLocationImgList = new ArrayList<>(Arrays.asList(diceLocation1ImgList, diceLocation2ImgList,
				diceLocation3ImgList, diceLocation4ImgList, diceLocation5ImgList, diceLocation6ImgList));

		textOfAmountInLocation1List = new ArrayList<>(Arrays.asList(textP1L1, textP2L1, textP3L1, textP4L1, textP5L1));
		textOfAmountInLocation2List = new ArrayList<>(Arrays.asList(textP1L2, textP2L2, textP3L2, textP4L2, textP5L2));
		textOfAmountInLocation3List = new ArrayList<>(Arrays.asList(textP1L3, textP2L3, textP3L3, textP4L3, textP5L3));
		textOfAmountInLocation4List = new ArrayList<>(Arrays.asList(textP1L4, textP2L4, textP3L4, textP4L4, textP5L4));
		textOfAmountInLocation5List = new ArrayList<>(Arrays.asList(textP1L5, textP2L5, textP3L5, textP4L5, textP5L5));
		textOfAmountInLocation6List = new ArrayList<>(Arrays.asList(textP1L6, textP2L6, textP3L6, textP4L6, textP5L6));
		// this.newGame(4);
	}

	@FXML
	public void testGame() {
		// add dice Image in diceImgList
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		// add dice Image in each location to each List
		diceLocation1ImgList = new ArrayList<>(
				Arrays.asList(diceP1L1Img, diceP2L1Img, diceP3L1Img, diceP4L1Img, diceP5L1Img));
		diceLocation2ImgList = new ArrayList<>(
				Arrays.asList(diceP1L2Img, diceP2L2Img, diceP3L2Img, diceP4L2Img, diceP5L2Img));
		diceLocation3ImgList = new ArrayList<>(
				Arrays.asList(diceP1L3Img, diceP2L3Img, diceP3L3Img, diceP4L3Img, diceP5L3Img));
		diceLocation4ImgList = new ArrayList<>(
				Arrays.asList(diceP1L4Img, diceP2L4Img, diceP3L4Img, diceP4L4Img, diceP5L4Img));
		diceLocation5ImgList = new ArrayList<>(
				Arrays.asList(diceP1L5Img, diceP2L5Img, diceP3L5Img, diceP4L5Img, diceP5L5Img));
		diceLocation6ImgList = new ArrayList<>(
				Arrays.asList(diceP1L6Img, diceP2L6Img, diceP3L6Img, diceP4L6Img, diceP5L6Img));
		// add all diceLocationImagelist in Sumlist
		diceInLocationImgList = new ArrayList<>(Arrays.asList(diceLocation1ImgList, diceLocation2ImgList,
				diceLocation3ImgList, diceLocation4ImgList, diceLocation5ImgList, diceLocation6ImgList));

		textOfAmountInLocation1List = new ArrayList<>(Arrays.asList(textP1L1, textP2L1, textP3L1, textP4L1, textP5L1));
		textOfAmountInLocation2List = new ArrayList<>(Arrays.asList(textP1L2, textP2L2, textP3L2, textP4L2, textP5L2));
		textOfAmountInLocation3List = new ArrayList<>(Arrays.asList(textP1L3, textP2L3, textP3L3, textP4L3, textP5L3));
		textOfAmountInLocation4List = new ArrayList<>(Arrays.asList(textP1L4, textP2L4, textP3L4, textP4L4, textP5L4));
		textOfAmountInLocation5List = new ArrayList<>(Arrays.asList(textP1L5, textP2L5, textP3L5, textP4L5, textP5L5));
		textOfAmountInLocation6List = new ArrayList<>(Arrays.asList(textP1L6, textP2L6, textP3L6, textP4L6, textP5L6));
		// add location name
		locationNameList.add("Gold Tower Casino");
		locationNameList.add("Riverside Casino");
		locationNameList.add("The Royal Casino");
		locationNameList.add("Lucky 7's Casino");
		locationNameList.add("The Edge Casino");
		locationNameList.add("Blackbird Casino");
		newGame(2);
	}

	public void updateGameStatus(String string) {
		if (string.length() > 70) {
			String newString = string.substring(0, 35);
			newString += "\n" + string.substring(35, 70);
			newString += "\n" + string.substring(70, string.length());
			string = newString;
		} else if (string.length() > 35) {
			String newString = string.substring(0, 35);
			newString += "\n";
			newString += string.substring(35, string.length());
			string = newString;
		}
		Text text = new Text(string);
		text.setFont(new Font(12));
		vBoxGameStatus.getChildren().add(0, text);
		text = new Text("-------------------");
		text.setFont(new Font(12));
		vBoxGameStatus.getChildren().add(0, text);
	}

	public void initialScoreBoard() {
		playerScoreBoard = new ArrayList<>(Arrays.asList(playerBoardPane1, playerBoardPane2, playerBoardPane3,
				playerBoardPane4, playerBoardPane5));
		balanceScoreBoard = new ArrayList<>(Arrays.asList(balanceBoardPane1, balanceBoardPane2, balanceBoardPane3,
				balanceBoardPane4, balanceBoardPane5));
		diceScoreBoard = new ArrayList<>(
				Arrays.asList(diceBoardPane1, diceBoardPane2, diceBoardPane3, diceBoardPane4, diceBoardPane5));
		updateScoreBoard();
	}

	public void updateScoreBoard() {
		for (int i = 0; i < playerList.size(); i++) {
			playerScoreBoard.get(i).getChildren().clear();
			balanceScoreBoard.get(i).getChildren().clear();
			diceScoreBoard.get(i).getChildren().clear();
			Font font = new Font("Book Antiqua Bold", 22.0);
			Text nameText = new Text(playerList.get(i).getName());
			Text balanceText = new Text(playerList.get(i).getBalance() + "");
			Text diceText = new Text(playerList.get(i).getDiceInPlayer().size() + "");
			nameText.setFont(font);
			balanceText.setFont(font);
			diceText.setFont(font);
			playerScoreBoard.get(i).getChildren().add(nameText);
			balanceScoreBoard.get(i).getChildren().add(balanceText);
			diceScoreBoard.get(i).getChildren().add(diceText);
		}
	}

	public void updateDice(Player player) {
		for (int k = 0; k < player.getDiceInPlayer().size(); k++) {
			int dicePoint = player.getDiceInPlayer().get(k).getPoint();
			String diceColour = player.getDiceInPlayer().get(k).getColour();
			File file = new File("res/dice" + dicePoint + diceColour + ".png");
			diceImgList.get(k).setImage(new Image(file.toURI().toString()));
		}
		if (player.getDiceInPlayer().size() < 8) {
			for (int k = player.getDiceInPlayer().size(); k < 8; k++) {
				File file = new File("res/nullDice.png");
				diceImgList.get(k).setImage(new Image(file.toURI().toString()));
			}
		}
	}

	@FXML
	public void roll() {
		Player player = currentPlayer;
		if (!isRoll && player.getDiceInPlayer().size() > 0) {
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
							Thread.sleep(40);
						}
						isRoll = true;
						rollButton.setDisable(false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		} else if (player.getDiceInPlayer().size() > 0) {
			updateGameStatus("You already rolled the dice!!");
		} else if (player.getDiceInPlayer().size() <= 0) {
			updateGameStatus("ํou don't have dice!!");
		}
	}

	// new game
	public void newGame(int amountOfPlayer) {
		updateGameStatus("Start New Game!!");
		this.setRoundCount(0);
		// ->ค้างสร้าง player
		// ลองสร้างไว้ทดสอบเฉยๆ
		playerList.add(new Player("Poom", "white"));
		playerList.add(new Player("Tungmay", "blue"));
		// playerList.add(new Player("Muay", "red"));
		// playerList.add(new Player("Night", "green"));
		// playerList.add(new Player("Toe", "yellow"));
		locationList.add(new Location("Gold Tower Casino", amountOfPlayer, 1));
		locationList.add(new Location("Riverside Casino", amountOfPlayer, 2));
		locationList.add(new Location("The Royal Casino", amountOfPlayer, 3));
		locationList.add(new Location("Luckey 7's Casino", amountOfPlayer, 4));
		locationList.add(new Location("The Edge Casino", amountOfPlayer, 5));
		locationList.add(new Location("Blackbird Casino", amountOfPlayer, 6));
		initialScoreBoard();
		resetBoard(amountOfPlayer);
		this.playGame(playerList);
	}

	public void playGame(ArrayList<Player> playerList) {
		indexPlayer = indexPlayer % playerList.size();
		selected = false;
		isRoll = false;
		curretntDiceSelect = -1;
		if (!allOutOfDice()) {
			Player p = playerList.get(indexPlayer);
			currentPlayer = p;
			updateScoreBoard();
			if (p.getDiceInPlayer().size() > 0) {
				updateDice(p);
				updateGameStatus(p.getName() + "'s turn!!");
			} else {
				indexPlayer += 1;
				playGame(playerList);
			}
		} else {
			this.setRoundCount(this.getRoundCount() + 1);
			if (this.getRoundCount() != 4) {
				this.endRound();
			} else {
				roundText.setText("Round " + getRoundCount() + " of 4");
				this.endgame();
			}
		}
	}

	// อัพเดตเงินในแต่ละสถานที่+แจกเงิน
	public void endRound() {
		updateGameStatus("End Round " + getRoundCount());
		roundText.setText("Round " + getRoundCount() + " of 4");
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
//			l.getFund().clear();
//			while (l.fundValue(l.getFund()) < 50000) {
//				l.updateFund();
//			}
		}
		this.setRoundCount(this.getRoundCount() + 1);
		resetBoard(playerList.size());
		playGame(playerList);
	}

	public void resetBoard(int amountOfPlayer) {
		isRoll = false;
		selected = false;
		indexPlayer = 0;
		curretntDiceSelect = -1;
		vBoxLocationList = new ArrayList<>(Arrays.asList(vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6));
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		locationImgList = new ArrayList<ImageView>(
				Arrays.asList(locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6));
		resetCard();
		resetPlayerDice();
		resetTextInLocation();
		clearBankNoteInLocation();
		addBankNoteToLocation();
		updateScoreBoard();
		setNewSpecialLocation();
	}

	public void addBankNoteToLocation() {
		for (Location location : locationList) {
			while (location.fundValue(location.getFund()) < 50000) {
				location.updateFund();
			}
			for (Banknote banknote : location.getFund()) {
				File file = new File("res/bankNote" + banknote.getBanknoteValue() + ".png");
				ImageView img = new ImageView(file.toURI().toString());
				img.setFitHeight(60);
				img.setFitWidth(140);
				vBoxLocationList.get(location.getDiceValue() - 1).getChildren().add(img);
			}
		}
	}

	public void resetCard() {
		cardDeck = new CardDeck();
		cardImg.setImage(new Image(new File("res/cardBackSeal.png").toURI().toString()));
	}

	@FXML
	public void openCard(MouseEvent event) {
		Card card = cardDeck.giveTopCardTo();
		File file = new File("res/" + card.getName() + ".png");
		cardImg.setImage(new Image(file.toURI().toString()));
		updateGameStatus(card.description());
	}

	public void setNewSpecialLocation() {
		int randomNumber = (new Random()).nextInt(6);
		int locationCode = randomNumber + 1;
		File fileLastSL = new File("res/location" + (lastSpecialLocation + 1) + ".png");
		locationImgList.get(lastSpecialLocation).setImage(new Image(fileLastSL.toURI().toString()));
		File fileSL = new File("res/location" + locationCode + "Special.png");
		locationImgList.get(randomNumber).setImage(new Image(fileSL.toURI().toString()));
		lastSpecialLocation = randomNumber;
		updateGameStatus("Special Location is " + locationNameList.get(randomNumber));
	}

	public void resetLocation() {
		for (Location location : locationList) {
			location.resetDiceInLocation();
		}
		for (int i = 0; i < locationImgList.size(); i++) {
			File file2 = new File("res/location" + (i + 1) + ".png");
			diceImgList.get(i).setImage(new Image(file2.toURI().toString()));
		}
	}

	public void resetTextInLocation() {
		for (Text text : textOfAmountInLocation1List) {
			text.setFill(Color.web("#3a161a"));
		}
		for (Text text : textOfAmountInLocation2List) {
			text.setFill(Color.web("#2d102e"));
		}
		for (Text text : textOfAmountInLocation3List) {
			text.setFill(Color.web("#956803"));
		}
		for (Text text : textOfAmountInLocation4List) {
			text.setFill(Color.web("#158474"));
		}
		for (Text text : textOfAmountInLocation5List) {
			text.setFill(Color.web("#33c1c2"));
		}
		for (Text text : textOfAmountInLocation6List) {
			text.setFill(Color.BLACK);
		}
	}

	public void resetPlayerDice() {
		for (Player p : playerList) {
			p.resetDice();
		}
		for (int i = 0; i < diceImgList.size(); i++) {
			File file1 = new File("res/nullDice.png");
			diceImgList.get(i).setImage(new Image(file1.toURI().toString()));
		}
	}

	public void clearBankNoteInLocation() {
		for (Location location : locationList) {
			location.getFund().clear();
		}
		for (VBox vBox : vBoxLocationList) {
			vBox.getChildren().clear();
		}
	}

	@FXML
	public void selectLocation(MouseEvent event) {
		ImageView locaitonImg = (ImageView) event.getSource();
		if (selected) {
			if (locaitonImg.equals(locationImg1) && curretntDiceSelect == 1) {
				dropDiceInLocation(0, currentPlayer, textOfAmountInLocation1List);
			} else if (locaitonImg.equals(locationImg2) && curretntDiceSelect == 2) {
				dropDiceInLocation(1, currentPlayer, textOfAmountInLocation2List);
			} else if (locaitonImg.equals(locationImg3) && curretntDiceSelect == 3) {
				dropDiceInLocation(2, currentPlayer, textOfAmountInLocation3List);
			} else if (locaitonImg.equals(locationImg4) && curretntDiceSelect == 4) {
				dropDiceInLocation(3, currentPlayer, textOfAmountInLocation4List);
			} else if (locaitonImg.equals(locationImg5) && curretntDiceSelect == 5) {
				dropDiceInLocation(4, currentPlayer, textOfAmountInLocation5List);
			} else if (locaitonImg.equals(locationImg6) && curretntDiceSelect == 6) {
				dropDiceInLocation(5, currentPlayer, textOfAmountInLocation6List);
			}
		} else {
			updateGameStatus("You must choose a dice first!!");
		}
	}

	public void dropDiceInLocation(int numberLocation, Player player, ArrayList<Text> textList) {
		int amount = player.dropDice(curretntDiceSelect);
		String s;
		if (amount == 1) {
			s = player.getName() + " drop a dice in " + locationNameList.get(numberLocation);
		} else {
			s = player.getName() + " drop " + amount + " dices in " + locationNameList.get(numberLocation);
		}
		updateGameStatus(s);
		amount = locationList.get(numberLocation).addDice(player, amount);
		System.out.println(numberLocation);
		System.out.println(locationList.get(numberLocation).getDiceInLocation());
		int playerIndex = playerList.indexOf(player);
		textList.get(playerIndex).setText(amount + "");
		textList.get(playerIndex).setFill(Color.WHEAT);
		File file = new File("res/dice" + curretntDiceSelect + player.getPlayerColour() + ".png");
		(diceInLocationImgList.get(numberLocation)).get(playerIndex).setImage(new Image(file.toURI().toString()));
		updateDice(player);
		indexPlayer += 1;
		playGame(playerList);
	}

	@FXML
	public void selectDice(MouseEvent event) {
		ImageView sourceImg = (ImageView) event.getSource();
		if (isRoll) {
			updateDice(currentPlayer);
			int size = currentPlayer.getDiceInPlayer().size();
			if (sourceImg.equals(diceImg0) && size > 0) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(0).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg1) && size > 1) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(1).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg2) && size > 2) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(2).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg3) && size > 3) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(3).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg4) && size > 4) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(4).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg5) && size > 5) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(5).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg6) && size > 6) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(6).getPoint(), currentPlayer);
			} else if (sourceImg.equals(diceImg7) && size > 7) {
				editSelectDice(currentPlayer.getDiceInPlayer().get(7).getPoint(), currentPlayer);
			} else {
				updateGameStatus("Please select the correct dice.");
			}
		} else {
			updateGameStatus("You must roll dices before selecting it.");
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
			updateGameStatus("You must roll dices before selecting it.");
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
