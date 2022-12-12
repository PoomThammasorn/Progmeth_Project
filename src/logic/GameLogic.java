package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import base.Location;
import base.SpecialLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import player.Player;

public class GameLogic {

	@FXML
	private GridPane locationGridPane, diceGridPane, gridDiceL1, gridDiceL2, gridDiceL3, gridDiceL4, gridDiceL5,
			gridDiceL6;
	@FXML
	private VBox vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6;
	@FXML
	private StackPane stackLocation1, stackLocation2, stackLocation3, stackLocation4, stackLocation5, stackLocation6;
	@FXML
	private ImageView diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7, locationImg1,
			locationImg2, locationImg3, locationImg4, locationImg5, locationImg6;
	@FXML
	private Text roundText;
	@FXML
	private Button rollButton, newGameBtn;

	private ArrayList<ImageView> diceImgList, locationImgList;
	private static GameLogic instance = null;
	private ArrayList<Location> locationList = new ArrayList<>();
	private ArrayList<String> locationNameList = new ArrayList<>();
	private ArrayList<Player> playerList = new ArrayList<>();
	private int roundCount, lastSpecialLocation = 0;

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

	@FXML
	public void roll() {
		// สองอันนี้ใส่มาก่อน จริงๆควรทำใน NewGame
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		Player player = new Player("player1", "white");
		//
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
					rollButton.setDisable(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();

	}

	// new game
	public void newGame(int amountOfPlayer) {
		this.setRoundCount(0);
		diceImgList = new ArrayList<ImageView>(
				Arrays.asList(diceImg0, diceImg1, diceImg2, diceImg3, diceImg4, diceImg5, diceImg6, diceImg7));
		locationImgList = new ArrayList<ImageView>(
				Arrays.asList(locationImg1, locationImg2, locationImg3, locationImg4, locationImg5, locationImg6));

		// ->ค้างสร้าง player
//		for (int k = 0; k < amountOfPlayer; k++) {
//			
//		}
		
		/*
		 * ตรง Location โปรแกรมมันค้างตรง locationList.add(new XXX); ตลอดเลย [บรรทัด
		 * 123,125] ผมเปลี่ยนให้มันเปลี่ยนแค่ special location แล้ว
		 * เพราะลืมไปว่าไม่ได้ทำรูปเผื่อสลับตำแหน่ง Location ลองกด RUN ละกดปุ่ม test
		 * game ดู
		 */
		
		locationNameList.add("Gold Tower Casino");
		locationNameList.add("Riverside Casino");
		locationNameList.add("The Royal Casino");
		locationNameList.add("Luckey 7's Casino");
		locationNameList.add("The Edge Casino");
		locationNameList.add("Blackbird Casino");
//		Collections.shuffle(locationNameList);
		locationList.add(new Location("Gold Tower Casino", amountOfPlayer, 0));
		locationList.add(new Location("Riverside Casino", amountOfPlayer, 0));
		locationList.add(new Location("The Royal Casino", amountOfPlayer, 0));
		locationList.add(new Location("Luckey 7's Casino", amountOfPlayer, 0));
		locationList.add(new Location("The Edge Casino", amountOfPlayer, 0));
		locationList.add(new Location("Blackbird Casino", amountOfPlayer, 0));
//		for (int i = 0; i < 5; i++) {
//			locationList.add(new Location(this.getLocationNameList().get(i), amountOfPlayer, i));
//		}
		// locationList.add(new
		// SpecialLocation(this.getLocationNameList().get(5).getValue(), amountOfPlayer,
		// 5));
		// เราสามารถ .set(index,ข้อมูลใหม่) เพื่อแทนข้อมูลใหม่ใน ArrayList ได้เลย
		// locationList.set(randomNumber, new SpecialLocation(this.getLocationNameList().get(randomNumber), amountOfPlayer, randomNumber + 1));
		int randomNumber = 	(new Random()).nextInt(6);
		int locationCode = randomNumber + 1;
		File file1 = new File("res/location" + (lastSpecialLocation + 1) + ".png");
		locationImgList.get(lastSpecialLocation).setImage(new Image(file1.toURI().toString()));
		File file = new File("res/location" + locationCode + "Special.png");
		locationImgList.get(randomNumber).setImage(new Image(file.toURI().toString()));
		lastSpecialLocation = randomNumber;
		// this.playGame(playerList);
	}

	public void playGame(ArrayList<Player> playerList) {
		int i = 0;
		while (!allOutOfDice()) {
			Player p = playerList.get(i);
			if (p.getDiceInPlayer().size() != 0) {
				p.rollDice();
				// ->ให้กดเลือกสถานที่ if(..) Location l
				// l.addDice(p,p.dropDice(หน้าลูกเต๋าประจำสถานที่));
			}
			i = (i + 1) % playerList.size();
		}
		this.setRoundCount(this.getRoundCount() + 1);
		if (this.getRoundCount() != 4) {
			this.endRound();
		} else {
			this.endgame();
		}

	}

	// อัพเดตเงินในแต่ละสถานที่+แจกเงิน
	public void endRound() {
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

	public void endgame() {
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
