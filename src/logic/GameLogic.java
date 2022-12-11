package logic;

import java.util.ArrayList;
import java.util.Collections;

import base.Location;
import base.SpecialLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import player.Player;

public class GameLogic {

	@FXML
	GridPane locationGridPane, diceGridPane, gridDiceL1, gridDiceL2, gridDiceL3, gridDiceL4, gridDiceL5, gridDiceL6;
	@FXML
	VBox vBoxL1, vBoxL2, vBoxL3, vBoxL4, vBoxL5, vBoxL6;
	@FXML
	StackPane stackLocation1, stackLocation2, stackLocation3, stackLocation4, stackLocation5, stackLocation6;
	@FXML
	Text roundText;
	@FXML
	Button rollButton;

	private static GameLogic instance = null;
	private ArrayList<Location> locationList;
	private ArrayList<String> locationNameList;
	private ArrayList<Player> playerList;
	private int roundCount;

	public GameLogic() {
		// TODO Auto-generated constructor stub
		//this.newGame(4);
	}

	public void rollDice() {
		playerList.get(0).rollDice();
	}

	// new game
	public void newGame(int amountOfPlayer) {
		this.setRoundCount(0);
		//->ค้างสร้าง player
		locationNameList.add("Gold Tower Casino");
		locationNameList.add("Riverside Casino");
		locationNameList.add("The Royal Casino");
		locationNameList.add("Luckey 7's Casino");
		locationNameList.add("The Edge Casino");
		locationNameList.add("Blackbird Casino");
		Collections.shuffle(locationNameList);
		for (int i = 0; i < 5; i++) {
			locationList.add(new Location(this.getLocationNameList().get(i), amountOfPlayer, i));
		}
		locationList.add(new SpecialLocation(this.getLocationNameList().get(5), amountOfPlayer, 5));
		this.playGame(playerList);
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
		this.setRoundCount(this.getRoundCount()+1);
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
