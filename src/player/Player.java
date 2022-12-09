package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import base.Dice;
import comparator.DiceComparator;

public class Player {

	private String name;
	private String playerColour;
	private int balance;
	private ArrayList<Dice> diceInPlayer;

	public Player(String name, String colour) {
		// TODO Auto-generated constructor stub
		setName(name);
		setBalance(0);
		setPlayerColour(colour);
		diceInPlayer = new ArrayList<Dice>();
		for (int i = 0; i < 8; i++) {
			diceInPlayer.add(new Dice(colour));
		}
	}

	public void rollDice() {
		for (Dice dice : diceInPlayer) {
			dice.rolling();
		}
	}

	public void sortDiceInPlayer() {
		Collections.sort(diceInPlayer, new DiceComparator());
	}

	public int dropDice(int point) {
		int count = 0;
		Iterator<Dice> itr = diceInPlayer.iterator();
		while (itr.hasNext()) {
			Dice dice = itr.next();
			if (dice.getPoint() == point) {
				itr.remove();
				count += 1;
			}
		}
		return count;

	}

	//public void getCard()
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == "") {
			name = "player";
		}
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		if (balance < 0) {
			balance = 0;
		}
		this.balance = balance;
	}

	public ArrayList<Dice> getDiceInPlayer() {
		return diceInPlayer;
	}

	public void setDiceInPlayer(ArrayList<Dice> diceInPlayer) {
		this.diceInPlayer = diceInPlayer;
	}

	public String getPlayerColour() {
		return playerColour;
	}

	public void setPlayerColour(String playerColour) {
		this.playerColour = playerColour;
	}

}
