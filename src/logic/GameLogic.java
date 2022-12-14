package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.File;
import java.net.URL;

import base.Banknote;
import base.Dice;
import base.Location;
import base.SpecialLocation;
import basecard.Card;
import card.BonusCard;
import card.CardDeck;
import card.StealCard;
import card.TaxCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class GameLogic implements Initializable {

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
			diceP5L2Img, diceP5L3Img, diceP5L4Img, diceP5L5Img, diceP5L6Img, diceImgP1, diceImgP2, diceImgP3, diceImgP4,
			diceImgP5;
	@FXML
	private Text roundText, textP1L1, textP2L1, textP3L1, textP4L1, textP5L1, textP1L2, textP2L2, textP3L2, textP4L2,
			textP5L2, textP1L3, textP2L3, textP3L3, textP4L3, textP5L3, textP1L4, textP2L4, textP3L4, textP4L4,
			textP5L4, textP1L5, textP2L5, textP3L5, textP4L5, textP5L5, textP1L6, textP2L6, textP3L6, textP4L6,
			textP5L6;
	@FXML
	private Button rollButton, nextRoundBtn, startBtn;

	private ArrayList<VBox> vBoxLocationList;
	private ArrayList<StackPane> playerScoreBoard, balanceScoreBoard, diceScoreBoard;
	private ArrayList<ImageView> diceImgList, locationImgList, diceImgScoreBoard;
	private ArrayList<ImageView> diceLocation1ImgList, diceLocation2ImgList, diceLocation3ImgList, diceLocation4ImgList,
			diceLocation5ImgList, diceLocation6ImgList;
	private ArrayList<ArrayList<ImageView>> diceInLocationImgList;
	private ArrayList<Text> textOfAmountInLocation1List, textOfAmountInLocation2List, textOfAmountInLocation3List,
			textOfAmountInLocation4List, textOfAmountInLocation5List, textOfAmountInLocation6List;
	private ArrayList<Location> locationList = new ArrayList<>();
	private ArrayList<String> locationNameList;
	private ArrayList<Player> playerList = new ArrayList<>();
	private int roundCount, indexPlayer = 0;
	private boolean isRoll = false, selected = false, cardSeal = true, waiting = false;
	private Player currentPlayer, playerWinSpecial = null;
	private int curretntDiceSelect, amountOfPlayer;
	private CardDeck cardDeck;
	private ArrayList<Integer> oldBalanceList = new ArrayList<>();

	public void setVariable(ArrayList<Player> playerList, int amount) {
		this.playerList = playerList;
		this.amountOfPlayer = amount;

		// newGame();
	}

	@FXML
	public void beginGame(MouseEvent event) {
		startBtn.setVisible(false);
		nextRoundBtn.setVisible(true);
		rollButton.setDisable(false);
		newGame();
	}

	// new game
	public void newGame() {
		updateGameStatus("Start New Game!!", Color.web("#FF8C00"));
		this.setRoundCount(1);
		for (int i = 0; i < amountOfPlayer; i++) {
			oldBalanceList.add(0);
		}
		initialScoreBoard();
		resetBoard(amountOfPlayer);
		updateGameStatus("==== Round " + getRoundCount() + " Start!! ====", Color.BLACK);
		this.playGame(playerList);
	}

	public void updateGameStatus(String string, Color color) {
		if (string.length() > 72) {
			string = string.substring(0, 36) + "\n" + string.substring(36, 72) + "\n"
					+ string.substring(72, string.length());
		} else if (string.length() > 36) {
			string = string.substring(0, 36) + "\n" + string.substring(36, string.length());
		}
		Text text1 = new Text("-------------------");
		text1.setFont(new Font(12));
		vBoxGameStatus.getChildren().add(0, text1);
		Text text2 = new Text(string);
		text2.setFont(new Font(12));
		text2.setFill(color);
		vBoxGameStatus.getChildren().add(0, text2);
	}

	public void initialScoreBoard() {
		playerScoreBoard = new ArrayList<>(Arrays.asList(playerBoardPane1, playerBoardPane2, playerBoardPane3,
				playerBoardPane4, playerBoardPane5));
		balanceScoreBoard = new ArrayList<>(Arrays.asList(balanceBoardPane1, balanceBoardPane2, balanceBoardPane3,
				balanceBoardPane4, balanceBoardPane5));
		diceScoreBoard = new ArrayList<>(
				Arrays.asList(diceBoardPane1, diceBoardPane2, diceBoardPane3, diceBoardPane4, diceBoardPane5));

		for (int i = amountOfPlayer; i < diceImgScoreBoard.size(); i++) {
			diceImgScoreBoard.get(i).setVisible(false);
		}

		updateScoreBoard();
	}

	public void updateScoreBoard() {
		for (int i = 0; i < playerList.size(); i++) {
			Font font = new Font("Book Antiqua Bold", 22.0);

			playerScoreBoard.get(i).getChildren().clear();
			Text nameText = new Text(playerList.get(i).getName());
			nameText.setFont(font);

			balanceScoreBoard.get(i).getChildren().clear();
			Text balanceText = new Text(playerList.get(i).getBalance() + "");
			balanceText.setFont(font);

			diceScoreBoard.get(i).getChildren().clear();
			Text diceText = new Text(playerList.get(i).getDiceInPlayer().size() + "");
			diceText.setFont(font);

			playerScoreBoard.get(i).getChildren().add(nameText);
			balanceScoreBoard.get(i).getChildren().add(balanceText);
			diceScoreBoard.get(i).getChildren().add(diceText);
		}
	}

	public void updateTurnSB() {
		for (int i = 0; i < amountOfPlayer; i++) {
			File file1 = new File("res/dice" + (i + 1) + "Visible.png");
			diceImgScoreBoard.get(i).setImage(new Image(file1.toURI().toString()));
		}
		int index = currentPlayer.playerNumber();
		File file2 = new File("res/dice" + index + currentPlayer.getPlayerColour() + ".png");
		diceImgScoreBoard.get(index - 1).setImage(new Image(file2.toURI().toString()));
	}

	public void updateBalanceStatus() {
		String pref = "+";
		for (int i = 0; i < amountOfPlayer; i++) {
			Color color = Color.web("#00A300"); // green;
			int oldBalance = oldBalanceList.get(i);
			if (oldBalance > playerList.get(i).getBalance()) {
				color = Color.web("#D2042D"); // red
				pref = "";
			}
			String s = playerList.get(i).getName() + "'s balace : " + playerList.get(i).getBalance();
			s += " (" + pref + (playerList.get(i).getBalance() - oldBalance) + ")";
			updateGameStatus(s, color);
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
		rollButton.setDisable(true);
		Player player = currentPlayer;
		if (isRoll) {
			updateGameStatus("You already rolled the dice!!", Color.RED);
			rollButton.setDisable(false);
			return;
		} else if (player.getDiceInPlayer().size() <= 0) {
			updateGameStatus("You don't have dice!!", Color.RED);
			rollButton.setDisable(false);
			return;
		}
		Thread thread = new Thread() {
			public void run() {
				try {
					for (int i = 0; i < 30; i++) {
						waiting = true;
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
					waiting = false;
					rollButton.setDisable(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	public void playGame(ArrayList<Player> playerList) {
		rollButton.setDisable(false);
		indexPlayer = indexPlayer % playerList.size();
		selected = false;
		isRoll = false;
		curretntDiceSelect = -1;
		updateScoreBoard();

		if (allOutOfDice()) {
			updateGameStatus("==== End Round " + getRoundCount() + " ====", Color.BLACK);
			setRoundCount(getRoundCount() + 1);
			this.endRound();
			return;
		}

		Player p = playerList.get(indexPlayer);
		currentPlayer = p;
		updateTurnSB();

		if (p.getDiceInPlayer().size() == 0) {
			indexPlayer += 1;
			playGame(playerList);
			return;
		}

		updateDice(p);
		updateGameStatus(p.getName() + "'s turn!!", Color.web("#4D34A0"));
	}

	// อัพเดตเงินในแต่ละสถานที่+แจกเงิน
	public void endRound() {
		for (Location l : locationList) {
			for (int i = 0; i < l.getDiceInLocation().size(); i++) {
				if (l instanceof SpecialLocation) {
					SpecialLocation sp = (SpecialLocation) l;
					int maxelement = Collections.max(sp.getDiceInLocation());
					int maxelementindex = sp.getDiceInLocation().indexOf(maxelement);
					if (maxelement != 0 && sp.notHaveSameElement(maxelement, maxelementindex)) {
						sp.sendReward(playerList.get(maxelementindex));
						l.getDiceInLocation().set(maxelementindex, 0);
						if (playerWinSpecial == null) {
							playerWinSpecial = playerList.get(maxelementindex);
						}
					}
				} else {
					int maxelement = Collections.max(l.getDiceInLocation());
					int maxelementindex = l.getDiceInLocation().indexOf(maxelement);
					if (maxelement != 0 && l.notHaveSameElement(maxelement, maxelementindex)) {
						l.sendReward(playerList.get(maxelementindex));
						l.getDiceInLocation().set(maxelementindex, 0);
					}
				}
			}
		}
		updateScoreBoard();
		rollButton.setDisable(true);
		if (playerWinSpecial == null) {
			checkGameEnd();
		} else {
			cardImg.setImage(new Image(new File("res/cardBack.png").toURI().toString()));
			cardSeal = false;
			updateGameStatus(playerWinSpecial.getName() + " win the special location!", Color.web("#4B0082"));
			updateGameStatus(playerWinSpecial.getName() + " must draw a card!", Color.web("#4B0082"));
		}
	}

	public void checkGameEnd() {
		updateBalanceStatus();
		updateScoreBoard();
		if (roundCount <= 4) {
			nextRoundBtn.setDisable(false);
		} else {
			endgame();
		}
	}

	@FXML
	public void clickNextRound(MouseEvent event) {
		for (int i = 0; i < amountOfPlayer; i++) {
			oldBalanceList.set(i, playerList.get(i).getBalance());
		}
		roundText.setText("Round " + getRoundCount() + " of 4");
		resetBoard(playerList.size());
		updateGameStatus("==== Round " + getRoundCount() + " Start!! ====", Color.BLACK);
		playGame(playerList);
	}

	public void resetBoard(int amountOfPlayer) {
		nextRoundBtn.setDisable(true);
		playerWinSpecial = null;
		setNewSpecialLocation();
		updateScoreBoard();
		isRoll = false;
		selected = false;
		indexPlayer = 0;
		curretntDiceSelect = -1;
		resetCard();
		resetPlayerDice();
		resetAmountOfDiceInLocation();
		resetDiceImgInLocation();
		clearBankNoteInLocation();
		addBankNoteToLocation();
	}

	public void addBankNoteToLocation() {
		for (Location location : locationList) {
			while (location.fundValue(location.getFund()) < 50000) {
				location.updateFund();
			}
			for (Banknote banknote : location.getFund()) {
				String filePath = "res/bankNote" + banknote.getBanknoteValue() + ".png";
				File file = new File(filePath);
				ImageView img = new ImageView(new Image(file.toURI().toString()));
				img.setFitHeight(60);
				img.setFitWidth(140);
				vBoxLocationList.get(location.getDiceValue() - 1).getChildren().add(img);
			}
		}
	}

	public void resetCard() {
		cardSeal = true;
		cardDeck = new CardDeck();
		cardImg.setImage(new Image(new File("res/cardBackSeal.png").toURI().toString()));
	}

	@FXML
	public void openCard(MouseEvent event) {
		if (!cardSeal) {
			Card card = cardDeck.giveTopCardTo();
			File file = new File("res/" + card.getName() + ".png");
			cardImg.setImage(new Image(file.toURI().toString()));
			updateGameStatus(card.description(), Color.BLUE);
			cardEffect(card);
		} else {
			updateGameStatus("You can't open card at this time.", Color.RED);
		}
	}

	public void cardEffect(Card card) {
		String name = playerWinSpecial.getName();
		if (card instanceof StealCard) {
			Player richPlayer = findRichestPlayerWithOut(playerWinSpecial);
			StealCard stealCard = (StealCard) card;
			playerWinSpecial.useCardWithObj(stealCard, richPlayer);
			int stole = stealCard.getStolenMoney();
			updateGameStatus("Richest player(except " + name + ") is " + richPlayer.getName() + ".",
					Color.web("#FF6347"));
			updateGameStatus(name + " receive " + stole + "$ from " + richPlayer.getName() + ".", Color.web("#FF6347"));
		} else if (card instanceof BonusCard || card instanceof TaxCard) {
			playerWinSpecial.useCardWithOutObj(card);
			updateGameStatus("Now " + name + "'balance is " + playerWinSpecial.getBalance() + "$.",
					Color.web("#FF6347"));
		}
		checkGameEnd();
	}

	public void setNewSpecialLocation() {
		int randomNumber = (new Random()).nextInt(6);
		int locationCode = randomNumber + 1;
		setNewLocationList(locationCode);
		for (int i = 0; i < locationNameList.size(); i++) {
			String s;
			if (i == randomNumber) {
				s = "res/location" + (i + 1) + "Special.png";
			} else {
				s = "res/location" + (i + 1) + ".png";
			}
			locationImgList.get(i).setImage(new Image((new File(s)).toURI().toString()));
		}
		updateGameStatus("Special Location is " + locationNameList.get(randomNumber), Color.web("#2879C0"));
	}

	public void initialLocationList(int amount) {
		locationList = new ArrayList<>();
		locationList.add(new Location("Gold Tower Casino", amount, 1));
		locationList.add(new Location("Riverside Casino", amount, 2));
		locationList.add(new Location("The Royal Casino", amount, 3));
		locationList.add(new Location("Luckey 7's Casino", amount, 4));
		locationList.add(new Location("The Edge Casino", amount, 5));
		locationList.add(new Location("Blackbird Casino", amount, 6));
	}

	public void setNewLocationList(int locationCode) {
		initialLocationList(amountOfPlayer);
		int index = locationCode - 1;
		String name = locationNameList.get(index);
		locationList.set(index, new SpecialLocation(name, amountOfPlayer, locationCode));
	}

	public void resetLocation() {
		for (Location location : locationList) {
			location.resetDiceInLocation();
		}
		for (int i = 0; i < locationImgList.size(); i++) {
			File file = new File("res/location" + (i + 1) + ".png");
			diceImgList.get(i).setImage(new Image(file.toURI().toString()));
		}
	}

	public void resetDiceImgInLocation() {
		for (int i = 0; i < diceInLocationImgList.size(); i++) {
			for (ImageView img : diceInLocationImgList.get(i)) {
				File file = new File("res/dice" + (i + 1) + "Seal.png");
				img.setImage(new Image(file.toURI().toString()));
			}
		}
	}

	public void resetAmountOfDiceInLocation() {
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
		if (waiting) {
			;
		} else if (selected) {
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
			updateGameStatus("You must choose a dice first!!", Color.RED);
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
		updateGameStatus(s, Color.GREEN);
		amount = locationList.get(numberLocation).addDice(player, amount);
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
		if (waiting) {
			;
		} else if (isRoll) {
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
				updateGameStatus("Please select the correct dice.", Color.RED);
			}
		} else {
			updateGameStatus("You must roll dices before selecting it.", Color.RED);
		}
	}

	public Player findRichestPlayerWithOut(Player player) {
		Player richestPlayer = null;
		int max = -1;
		for (Player p : playerList) {
			if (p.getPlayerColour() != player.getPlayerColour()) {
				if (p.getBalance() > max) {
					max = p.getBalance();
					richestPlayer = p;
				} else if (p.getBalance() == max && p.playerNumber() < richestPlayer.playerNumber()) {
					max = p.getBalance();
					richestPlayer = p;
				}
			}
		}
		return richestPlayer;
	}

	public void editSelectDice(int dicePoint, Player player) {
		selected = true;
		curretntDiceSelect = dicePoint;
		for (int i = 0; i < player.getDiceInPlayer().size(); i++) {
			Dice dice = player.getDiceInPlayer().get(i);
			if (dice.getPoint() != dicePoint) {
				File file = new File("res/dice" + dice.getPoint() + "Visible" + ".png");
				diceImgList.get(i).setImage(new Image(file.toURI().toString()));
			}
		}
	}

	public void endgame() {
		updateGameStatus("GAME END!!", Color.MEDIUMPURPLE);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
		diceImgScoreBoard = new ArrayList<>(Arrays.asList(diceImgP1, diceImgP2, diceImgP3, diceImgP4, diceImgP5));
		locationNameList = new ArrayList<>(Arrays.asList("Gold Tower Casino", "Riverside Casino", "The Royal Casino",
				"Lucky 7's Casino", "The Edge Casino", "Blackbird Casino"));
		vBoxLocationList = new ArrayList<>(Arrays.asList(vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6));
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		locationImgList = new ArrayList<ImageView>(
				Arrays.asList(locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6));
	}

}
