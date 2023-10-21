package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
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
import controller.EndScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;
import player.Player;

public class GameLogic implements Initializable {

	@FXML
	private StackPane playerBoardPane1, playerBoardPane2, playerBoardPane3, playerBoardPane4, playerBoardPane5;
	@FXML
	private StackPane balanceBoardPane1, balanceBoardPane2, balanceBoardPane3, balanceBoardPane4, balanceBoardPane5;
	@FXML
	private StackPane diceBoardPane1, diceBoardPane2, diceBoardPane3, diceBoardPane4, diceBoardPane5;

	@FXML
	private VBox vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6;
	@FXML
	private VBox vBoxGameStatus;
	@FXML
	private GridPane gridDicePaneL0, gridDicePaneL1, gridDicePaneL2, gridDicePaneL3, gridDicePaneL4, gridDicePaneL5;
	
	@FXML
	private GridPane gridDice;

	@FXML
	private ImageView cardImg;
	@FXML
	private ImageView diceImgP1, diceImgP2, diceImgP3, diceImgP4, diceImgP5;

	@FXML
	private ImageView locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6;

	@FXML
	private Text roundText;

	@FXML
	private Button rollButton, nextRoundBtn, startBtn, endBtn;
	
	private ArrayList<GridPane> gridDicePaneList;

	private ArrayList<VBox> vBoxLocationList;
	private ArrayList<StackPane> playerScoreBoard, balanceScoreBoard, diceScoreBoard;

	private ArrayList<ImageView> diceImgList, locationImgList, diceImgScoreBoard;

	private ArrayList<ArrayList<ImageView>> diceInLocationImgList;

	private ArrayList<ArrayList<Text>> textOfAmountInLocationList;

	private ArrayList<Location> locationList = new ArrayList<>();

	private ArrayList<String> locationNameList;

	private ArrayList<Player> playerList = new ArrayList<>();

	private ArrayList<Integer> oldBalanceList = new ArrayList<>();

	private boolean isRoll = false;
	private boolean selected = false;
	private boolean cardSeal = true;
	private boolean waiting = false;

	private Player currentPlayer;
	private Player playerWinSpecial = null;

	private int curretntDiceSelect;
	private int amountOfPlayer;
	private int roundCount;
	private int indexPlayer;

	private CardDeck cardDeck;

	private MediaPlayer themeSongPlayer;

	public void setVariable(ArrayList<Player> playerList, int amount) {
		this.playerList = playerList;
		this.amountOfPlayer = amount;
	}

	@FXML
	public void beginGame(MouseEvent event) {
		startBtn.setVisible(false);
		nextRoundBtn.setVisible(true);
		rollButton.setDisable(false);
		beginThemeSong();
		newGame();
	}

	public void newGame() {
		updateGameStatus("Start New Game!!", Color.web("#FF8C00"));
		this.setRoundCount(1);
		this.indexPlayer = 0;
		for (int i = 0; i < amountOfPlayer; i++) {
			oldBalanceList.add(0);
		}
		initialScoreBoard();
		resetBoard(amountOfPlayer);
		updateGameStatus("==== Round " + getRoundCount() + " Start!! ====", Color.BLACK);
		this.playGame(playerList);
	}

	public void playGame(ArrayList<Player> playerList) {
		rollButton.setDisable(false);
		indexPlayer = indexPlayer % playerList.size();
		selected = false;
		isRoll = false;
		curretntDiceSelect = -1;
		updateScoreBoard();
		if (allOutOfDice()) {
			playAudio("voice/Startturn_endturn.mp3", 0.3);
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
			nextRoundBtn.setVisible(false);
			endBtn.setVisible(true);
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
			String image_path1 = ClassLoader.getSystemResource("dice" + (i + 1) + "Visible.png").toString();
			diceImgScoreBoard.get(i).setImage(new Image(image_path1));
		}
		int index = currentPlayer.playerNumber();
		String image_path2 = ClassLoader.getSystemResource("dice" + index + currentPlayer.getPlayerColour() + ".png")
				.toString();
		diceImgScoreBoard.get(index - 1).setImage(new Image(image_path2));
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
				for (int i = 0; i < banknote.getAmount(); i++) {
					String image_path = ClassLoader.getSystemResource("bankNote" + banknote.getBanknoteValue() + ".png")
							.toString();
					ImageView img = new ImageView(new Image(image_path));
					img.setFitHeight(60);
					img.setFitWidth(140);
					vBoxLocationList.get(location.getDiceValue() - 1).getChildren().add(img);
				}
			}
		}
	}

	public void resetCard() {
		cardSeal = true;
		cardDeck = new CardDeck();
		String image_path = ClassLoader.getSystemResource("cardBackSeal.png").toString();
		cardImg.setImage(new Image(image_path));
	}

	@FXML
	public void openCard(MouseEvent event) {
		if (!cardSeal) {
			playAudio("voice/Draw_edit.mp3", 1);
			Card card = cardDeck.giveTopCardTo();
			String image_path = ClassLoader.getSystemResource(card.getName() + ".png").toString();
			cardImg.setImage(new Image(image_path));
			updateGameStatus(card.description(), Color.BLUE);
			cardEffect(card);
			cardSeal = true;
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
				s = "location" + (i + 1) + "Special.png";
			} else {
				s = "location" + (i + 1) + ".png";
			}
			String image_path = ClassLoader.getSystemResource(s).toString();
			locationImgList.get(i).setImage(new Image(image_path));
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
			String image_path = ClassLoader.getSystemResource("location" + (i + 1) + ".png").toString();
			diceImgList.get(i).setImage(new Image(image_path));
		}
	}

	public void updateDice(Player player) {
		for (int k = 0; k < player.getDiceInPlayer().size(); k++) {
			int dicePoint = player.getDiceInPlayer().get(k).getPoint();
			String diceColour = player.getDiceInPlayer().get(k).getColour();
			String image_path = ClassLoader.getSystemResource("dice" + dicePoint + diceColour + ".png").toString();
			diceImgList.get(k).setImage(new Image(image_path));
		}
		if (player.getDiceInPlayer().size() < 8) {
			for (int k = player.getDiceInPlayer().size(); k < 8; k++) {
				String image_path = ClassLoader.getSystemResource("nullDice.png").toString();
				diceImgList.get(k).setImage(new Image(image_path));
			}

		}
	}

	public void resetDiceImgInLocation() {
		for (int i = 0; i < diceInLocationImgList.size(); i++) {
			for (ImageView img : diceInLocationImgList.get(i)) {
				String image_path = ClassLoader.getSystemResource("dice" + (i + 1) + "Seal.png").toString();
				img.setImage(new Image(image_path));
			}
		}
	}

	public void resetAmountOfDiceInLocation() {
		for (Text text : textOfAmountInLocationList.get(0)) {
			text.setFill(Color.web("#3a161a"));
		}
		for (Text text : textOfAmountInLocationList.get(1)) {
			text.setFill(Color.web("#2d102e"));
		}
		for (Text text : textOfAmountInLocationList.get(2)) {
			text.setFill(Color.web("#956803"));
		}
		for (Text text : textOfAmountInLocationList.get(3)) {
			text.setFill(Color.web("#158474"));
		}
		for (Text text : textOfAmountInLocationList.get(4)) {
			text.setFill(Color.web("#33c1c2"));
		}
		for (Text text : textOfAmountInLocationList.get(5)) {
			text.setFill(Color.BLACK);
		}
	}

	public void resetPlayerDice() {
		for (Player p : playerList) {
			p.resetDice();
		}
		for (int i = 0; i < diceImgList.size(); i++) {
			String image_path = ClassLoader.getSystemResource("nullDice.png").toString();
			diceImgList.get(i).setImage(new Image(image_path));
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
	public void roll() {
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
		playAudio("voice/Rolldice_cut.mp3", 0.3);
		rollButton.setDisable(true);
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
							String image_path = ClassLoader.getSystemResource("dice" + dicePoint + diceColour + ".png")
									.toString();
							diceImgList.get(k).setImage(new Image(image_path));
						}
						Thread.sleep(30);

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

	@FXML
	public void selectLocation(MouseEvent event) {
	    ImageView locationImg = (ImageView) event.getSource();
	    if (waiting) {
	    // Exit the method early if waiting
	    } else if (selected) {
	        for (int i = 0; i < locationImgList.size(); i++) {
	            if (locationImg.equals(locationImgList.get(i)) && curretntDiceSelect == i + 1) {
	                dropDiceInLocation(i, currentPlayer, textOfAmountInLocationList.get(i));
		            break;
	            }
	        }
	        updateGameStatus("Invalid selection.", Color.RED);
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
		String image_path = ClassLoader
				.getSystemResource("dice" + curretntDiceSelect + player.getPlayerColour() + ".png").toString();
		(diceInLocationImgList.get(numberLocation)).get(playerIndex).setImage(new Image(image_path));
		updateDice(player);
		indexPlayer += 1;
		playGame(playerList);
	}

	@FXML
	public void selectDice(MouseEvent event) {
		ImageView sourceImg = (ImageView) event.getSource();

		if (waiting) {
		    // Do nothing
		} else if (isRoll) {
		    updateDice(currentPlayer);
		    int size = currentPlayer.getDiceInPlayer().size();
		    boolean found = false;
		    for (int i = 0; i < diceImgList.size(); i++) {
		        if (sourceImg.equals(diceImgList.get(i)) && size > i) {
		            editSelectDice(currentPlayer.getDiceInPlayer().get(i).getPoint(), currentPlayer);
		            found = true;
		            break;
		        }
		    }
		    if (!found) {
		        updateGameStatus("Please select the correct dice.", Color.RED);
		    }
		} else {
		    updateGameStatus("You must roll dices before selecting it.", Color.RED);
		}
	}

	public void editSelectDice(int dicePoint, Player player) {
		selected = true;
		curretntDiceSelect = dicePoint;
		for (int i = 0; i < player.getDiceInPlayer().size(); i++) {
			Dice dice = player.getDiceInPlayer().get(i);
			if (dice.getPoint() != dicePoint) {
				String image_path = ClassLoader.getSystemResource("dice" + dice.getPoint() + "Visible" + ".png")
						.toString();
				diceImgList.get(i).setImage(new Image(image_path));
			}
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

	public ArrayList<Player> findRichestPlayer() {
		ArrayList<Player> richestPlayerList = new ArrayList<>();
		int max = -1;
		for (Player p : playerList) {
			if (p.getBalance() > max) {
				max = p.getBalance();
				richestPlayerList.clear();
				richestPlayerList.add(p);
			} else if (p.getBalance() == max) {
				richestPlayerList.add(p);
			}

		}
		return richestPlayerList;
	}

	public void beginThemeSong() {
		Class currentClass = this.getClass();
		ClassLoader classLoader = currentClass.getClassLoader();
		URL url = classLoader.getResource("voice/backGroundTheme.mp3");
		Media media = new Media(url.toString());
		themeSongPlayer = new MediaPlayer(media);
		themeSongPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		themeSongPlayer.setVolume(0.2);
		themeSongPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				themeSongPlayer.play();
			}
		});
		themeSongPlayer.play();
	}

	public void playAudio(String string, double volume) {
		AudioClip sound = new AudioClip(ClassLoader.getSystemResource(string).toString());
		sound.setVolume(volume);
		sound.play();
	}

	public boolean allOutOfDice() {
		for (Player p : playerList) {
			if (p.getDiceInPlayer().size() != 0)
				return false;
		}
		return true;
	}

	@FXML
	public void endGame(MouseEvent event) {
		updateGameStatus("GAME END!!", Color.MEDIUMPURPLE);
		try {
			themeSongPlayer.stop();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/EndScene.fxml"));
			Parent root = loader.load();
			EndScene controller = loader.getController();
			controller.setEnd(findRichestPlayer());
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			Window window = ((Node) (event.getSource())).getScene().getWindow();
			window.hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		gridDicePaneList = new ArrayList<GridPane>(
				Arrays.asList(gridDicePaneL0, gridDicePaneL1, gridDicePaneL2, gridDicePaneL3, gridDicePaneL4, gridDicePaneL5));
		diceInLocationImgList = new ArrayList<ArrayList<ImageView>>();
		for (int i = 0; i < 6; i++) {
			ArrayList<ImageView> imgList = new ArrayList<ImageView>();
			for (int j = 0; j < 5; j++) {
			    ImageView img = new ImageView();
			    img.setFitWidth(45.0);
			    img.setFitHeight(45.0);
			    imgList.add(img);
			    gridDicePaneList.get(i).add(img, 0, j);
			}
			diceInLocationImgList.add(imgList);
		}
		
		textOfAmountInLocationList = new ArrayList<ArrayList<Text>>();
		for (int i = 0; i < 6; i++) {
			ArrayList<Text> textList = new ArrayList<Text>();
			for (int j = 0; j < 5; j++) {
			    Text text = new Text();
			    text.setStrokeType(StrokeType.OUTSIDE);
			    text.setStrokeWidth(0.0);
			    text.setTextAlignment(TextAlignment.CENTER);
			    Font font = Font.font("Bell MT Bold", FontWeight.NORMAL, 37.0);
			    text.setFont(font);
			    textList.add(text);
			    gridDicePaneList.get(i).add(text, 1, j);
			}
			textOfAmountInLocationList.add(textList);
		}
		diceImgScoreBoard = new ArrayList<>(Arrays.asList(diceImgP1, diceImgP2, diceImgP3, diceImgP4, diceImgP5));
		locationNameList = new ArrayList<>(Arrays.asList("Gold Tower Casino", "Riverside Casino", "The Royal Casino",
				"Lucky 7's Casino", "The Edge Casino", "Blackbird Casino"));
		vBoxLocationList = new ArrayList<>(Arrays.asList(vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6));
		diceImgList = new ArrayList<ImageView>();
		for (int i = 0; i < 8; i++) {
			ImageView img = new ImageView();
			img.setFitWidth(66.0);
			img.setFitHeight(66.0);
			img.setOnMouseClicked(this::selectDice);
			img.setOnMouseEntered(event -> {
				img.setCursor(Cursor.HAND);
			});
			gridDice.add(img,i,0);
			diceImgList.add(img);
		}
		locationImgList = new ArrayList<ImageView>(
				Arrays.asList(locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6));
		endBtn.setVisible(false);
	}

}